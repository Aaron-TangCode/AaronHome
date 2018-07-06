package com.example.reverseproxy.controller.des;

import com.example.reverseproxy.util.DesEncryptUtil;
import com.trs.idm.spnego.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class test1 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        //明文
        String str = "Aaron is very goog";
        byte[] b = new test1().getMD5Digest(str);
        String s1 = b.toString();
        System.out.println("明文摘要："+s1);

        /**
         * 用DES加密
         */
        //先用base64编码
        String base_64 = new String(Base64.encode(str.getBytes("utf-8")));
        //用DES加密
        String dataAfterDES = DesEncryptUtil.encryptToHex(base_64.getBytes("utf-8"), "12345678");
        System.out.println("str经过base64编译和DES加密后的密文："+dataAfterDES);

        //生成密文摘要
        byte[] b2 = new test1().getMD5Digest(dataAfterDES);
        String s2 = b.toString();
        System.out.println("密文摘要："+s2);


    }
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
