package com.adpf.modules.dataservice.doGet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.PrintWriter;
import java.net.URLConnection;

@Service
public class Example {
	
	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	public JSONObject postRequestFromUrl(String url, String body) throws IOException, JSONException {
		URL realUrl = new URL(url);
		URLConnection conn = realUrl.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		PrintWriter out = new PrintWriter(conn.getOutputStream());
		out.print(body);
		out.flush();
	
		InputStream instream = conn.getInputStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject();
			return json;
		} finally {
			instream.close();
		}
	}
	
	public JSONObject getRequestFromUrl(String url) throws IOException{
		URL realUrl = new URL(url);
		URLConnection conn = realUrl.openConnection();
		InputStream instream = conn.getInputStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject();
			json = JSONObject.parseObject(jsonText);
			return json;
		} finally {
			instream.close();
		}
	}
	public static void main(String[] args) throws IOException, JSONException {
	
		// 请求示例 url 默认请求参数已经做URL编码
		String urlTest = "http://";
		String url = "http://localhost:8050/api/adpf/dataServer/getList?orders=1";
//		JSONObject json = getRequestFromUrl(url);;
//		System.out.println(json.toString());
	}
}
