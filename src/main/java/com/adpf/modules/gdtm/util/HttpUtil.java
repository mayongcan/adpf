package com.adpf.modules.gdtm.util;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

public class HttpUtil {

	public static String getStr(String url) {
		String result = "";
		try {
			Content content = Request.Get(url).connectTimeout(1000).socketTimeout(1000).execute().returnContent();
			result = content.asString();
			result = URLDecoder.decode(HttpUtil.replacer(result), "UTF-8");
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("异常url地址：" + url);
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(getStr("https://www.baidu.com/"));

	}

	public static String replacer(String data) {
		try {
			data = data.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
			data = data.replaceAll("\\+", "%2B");
			data = URLDecoder.decode(data, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
