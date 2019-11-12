package com.adpf.modules.openam.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpUtil {

	private static final String CHARSET = "utf-8";// "UTF-8"; // 字符编码集

	public static String doHttpPost(String url, String req) {
		PrintWriter outPrintWriter = null;
		BufferedReader inBufferedReader = null;
		try {
			URLConnection urlConnection = new URL(url).openConnection(); // 打开和URL之间的连接
			// 设置通用的请求属性
			urlConnection.setRequestProperty("accept", "*/*");
			urlConnection.setRequestProperty("connection", "Keep-Alive");
			urlConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 必须设置如下两行
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setConnectTimeout(300000);
			urlConnection.setReadTimeout(300000);

			urlConnection.connect(); // 建立实际的连接

			outPrintWriter = new PrintWriter(urlConnection.getOutputStream()); // 获取URLConnection对象对应的输出流
			outPrintWriter.print(req); // 发送请求参数
			outPrintWriter.flush(); // flush输出流的缓冲
			inBufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), CHARSET)); // 定义BufferedReader输入流来读取URL的响应
			String line = "";
			String response = "";
			while ((line = inBufferedReader.readLine()) != null)
				response += line;
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			try {
				if (outPrintWriter != null)
					outPrintWriter.close();
				if (inBufferedReader != null)
					inBufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发送get请求
	 *
	 * @param url 请求地址
	 * @return K V status 1 成功 0 失败 result 返回的xml字符串
	 */
	public static String get(String url) {

		url = url.trim();
		String result = "";
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		int statusCode = 0;
		try {
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				byte[] responseBody = getMethod.getResponseBody();
				result = new String(responseBody);

			} else {
				byte[] responseBody = getMethod.getResponseBody();
				result = new String(responseBody);

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
		try {
			System.out.println(url + " , " + result + "," + statusCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return result;
	}
}