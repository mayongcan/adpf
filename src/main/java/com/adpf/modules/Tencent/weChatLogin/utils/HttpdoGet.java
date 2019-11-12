package com.adpf.modules.Tencent.weChatLogin.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class HttpdoGet {
	
	public JSONObject doGet(String url) {
		JSONObject json = new JSONObject();
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
	        HttpGet httpGet = new HttpGet(url);
	        HttpResponse httpResponse;
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			json = JSONObject.parseObject(EntityUtils.toString(httpEntity, "utf-8"));
	        System.out.println(url);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
