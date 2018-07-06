package com.example.reverseproxy;

import com.example.reverseproxy.util.DesEncryptUtil;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReverseproxyApplicationTests {

    @Test
    public void contextLoads() {
        //测试MD5

        //未加密的信息
        String str = "tomcat is so good";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            //用utf-8编译
            md.update(str.getBytes("utf-8"));
            //生成摘要
            byte[] digest = md.digest();
            //输出摘要
            System.out.println("摘要："+digest);

            //把str信息用DES加密,秘钥是“12345678”
            String base64Encoded = new String(Base64.encode(str.getBytes("utf-8")));
            System.out.println("base64:"+base64Encoded);
            String dataAfterDES = DesEncryptUtil.encryptToHex(base64Encoded.getBytes("utf-8"), "12345678");
            System.out.println("str经过base64编译和DES加密后的密文："+dataAfterDES);

            //先DES解密，再BASE64解码
            //解密
            String afterDESResult = DesEncryptUtil.decrypt(dataAfterDES, "12345678");
            //用base64解码
            String afterBASE64 = new String(Base64.decode(afterDESResult.getBytes("utf-8")), "utf-8");
            System.out.println("----------------------------");
            System.out.println("response data: ");
            //输出明文
            System.out.println("明文："+afterBASE64);
            //输出摘要
            String digestOfAgent = DesEncryptUtil.toString(getMD5Digest(afterBASE64));



        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
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
