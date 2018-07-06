package com.example.reverseproxy.controller;

import com.example.reverseproxy.util.DesEncryptUtil;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
public class ReverseProxy {
    //DES算法的密钥
    final String DES_KEY = "12345678";

    //协作应用名
    final String CO_APP_NAME = "reverseProxy";
    /*
     * 接受token，并获取用户信息
     */
    @PostMapping("reverseLogin")
    public void reverseLogin(String token, HttpServletRequest request) {
        String ssoSessionId = token.split("_")[0];
        System.out.println("ssoSessionId: " + ssoSessionId);
        JSONObject obj = JSONObject.fromObject(getUserInfo(ssoSessionId, request.getSession().getId(), CO_APP_NAME));


    }

    @GetMapping("reverseLogout")
    public void reverseLogout() {
        System.out.println("reverseLogout...");
    }

    /*
     * 调用IDS接口，获取用户信息
     */
    public String getUserInfo(String ssoSessionId, String coSessionId, String coAppName) {
        //idsServiceType、serviceName、coAppName、type 不需要加密
        String url = "http://localhost:8001/ids/service?idsServiceType=httpssoservice&serviceName=findUserBySSOID&type=json&coAppName=" + coAppName ;
        StringBuilder sb = new StringBuilder();
        HashMap<String, String> map = new HashMap();
        map.put("ssoSessionId", ssoSessionId);
        map.put("coSessionId", coSessionId);
        Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
        boolean first = true;
        while(iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            if(first) {
                sb.append(entry.getKey() + "=" + entry.getValue());
                first = false;
            }else {
                sb.append("&" + entry.getKey() + "=" + entry.getValue());
            }
        }
        //得到参数拼接成的字符串
        String parameters = sb.toString();
        System.out.println("parameters: " + parameters);

        //DES加密
        try {
            //获取消息摘要digest
            String digest = DesEncryptUtil.toString(getMD5Digest(parameters));
//			String digest = getMD5Digest(parameters).toString();
            System.out.println("digest: " + digest);

            //先对parameters BASE64编码，再DES加密，然后与消息摘要拼接(摘要在前)
            String base64Encoded = new String(Base64.encode(parameters.getBytes("utf-8")));
            String dataAfterDES = DesEncryptUtil.encryptToHex(base64Encoded.getBytes("utf-8"), DES_KEY);
            String finalData = digest + "&" + dataAfterDES;
//			url += ("&data=" + finalData);
            System.out.println("finalUrl: " + url);

            //封装GET请求
//			GetMethod get = new GetMethod(url);
            PostMethod post = new PostMethod(url);
            post.addParameter("data", finalData);
            HttpClient client = new HttpClient();
            client.executeMethod(post);
            //返回状态码
            System.out.println("idscode" + post.getResponseHeader("IDSCode"));
            //获取返回的加密数据
            String res = new String(post.getResponseBody(), "utf-8");
            System.out.println("res:" + res);

            String[] digestAndResult = res.split("&");
            String digestOfServer = digestAndResult[0];
            System.out.println("摘要："+digestOfServer);
            String result = digestAndResult[1];

            //先DES解密，再BASE64解码
            String afterDESResult = DesEncryptUtil.decrypt(result,  DES_KEY);
            String afterBASE64 = new String(Base64.decode(afterDESResult.getBytes("utf-8")), "utf-8");
            System.out.println("----------------------------");
            System.out.println("response data: ");
            System.out.println(afterBASE64);

            String digestOfAgent = DesEncryptUtil.toString(getMD5Digest(afterBASE64));
            System.out.println("摘要："+digestOfAgent);

            //对响应结果生成的摘要，如果与返回的摘要相等，说明响应结果没有被篡改
            if(digestOfServer.equals(digestOfAgent))
                return afterBASE64;
            else
                return null;
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /*
     * MD5算法生成消息摘要
     */
    public byte[] getMD5Digest(String str) {
        try {
            MessageDigest md = null;
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes("utf-8"));
            byte[] digestByte = md.digest();
            return digestByte;

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
