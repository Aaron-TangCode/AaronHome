package com.aaron.springbootcrud.IDSTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.google.gson.Gson;


public class NetUtil {
	public static HttpClient client = new HttpClient();
	
	public static String doGet(String uri) {
		GetMethod get = new GetMethod(uri);
		String res = null;
		try {
			client.executeMethod(get);
			res = byteToString(get.getResponseBody());
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(res);
		return res;
	}
	
	public static String doGetWithParams(String uri, Pair...pair) {
		boolean hasParam = true;
		try {
			hasParam = (new URL(uri).getQuery() != null);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(Pair p: pair) {
			if(hasParam) {
				uri += ("&" + p.getKey() + "=" + p.getValue());
			}else {
				uri += ("?" + p.getKey() + "=" + p.getValue());
				hasParam = true;
			}
		}
		GetMethod get = new GetMethod(uri);
		String res = null;
		try {
			client.executeMethod(get);
			res = byteToString(get.getResponseBody());
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(res);
		return res;
	}
	
	public static String doPost(String uri, HashMap<String, Object> map) {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(uri);
		post.addRequestHeader("Content-Type", "application/json");
		try {
			RequestEntity entity = new StringRequestEntity(new Gson().toJson(map), "application/json","UTF-8");
			post.setRequestEntity(entity);
			client.executeMethod(post);
			String res = byteToString(post.getResponseBody());
			System.out.println(res);
			return res;
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String byteToString(byte[] bytes) {
		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
