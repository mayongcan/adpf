package com.adpf.modules.gdtm.service.dpiinf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.adpf.modules.gdtm.util.StringUtils;
import com.google.common.collect.Maps;

/**
 * http 工具类
 * 
 * @author zzj
 * @createTime 2017年4月17日下午6:38:36
 * @version
 */
public class HttpCLientUtil {
	Logger logger = Logger.getLogger(HttpCLientUtil.class);

	private static String isLog = "0";

	/**
	 * 发送get请求
	 *
	 * @param url
	 *            请求地址
	 * @return K V status 1 成功 0 失败 result 返回的xml字符串
	 */
	public static String get(String url) {
		if (StringUtils.isBlank(isLog)) {
			isLog = "0";
		}
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

	/**
	 * 发送Post请求
	 *
	 * @param url
	 *            请求地址
	 * @param params
	 *            post参数
	 * @return K V status 1 成功 0 失败 result 返回的xml字符串
	 *
	 */
	@SuppressWarnings("unused")
	public static Map<String, Object> post(String url, Map<String, String> params) {
		Map<String, Object> resultMap = Maps.newHashMap();
		boolean pretty = true;
		StringBuffer response = new StringBuffer();
		try {
			HttpClient httpClient = new HttpClient();
			PostMethod postMethod = new PostMethod(url);
			postMethod.getParams().setContentCharset("utf8");
			NameValuePair[] data = new NameValuePair[params.size()];
			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				nameValuePairList.add(new NameValuePair(key, params.get(key)));
			}
			for (int i = 0; i < nameValuePairList.size(); i++) {
				data[i] = nameValuePairList.get(i);
			}
			postMethod.setRequestBody(data);
			int statusCode = httpClient.executeMethod(postMethod);
			if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
				// 读取为 InputStream，在网页内容数据量大时候推荐使用
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(postMethod.getResponseBodyAsStream(), "utf8"));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty)
						response.append(line).append(System.getProperty("line.separator"));
					else
						response.append(line);
				}
				reader.close();
				postMethod.releaseConnection();
			}
			resultMap.put("status", 1);
			resultMap.put("result", response);
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		} catch (IOException e) {
			e.printStackTrace();
			resultMap.put("status", 0);
			resultMap.put("result", null);
		}
		return resultMap;
	}

	/**
	 * 发送Post请求
	 *
	 * @param url
	 *            请求地址
	 * @param xml
	 *            post参数
	 * @return K V status 1 成功 0 失败 result 返回的xml字符串
	 *
	 */
	public static Map<String, Object> postXml(String url, String xml) {
		Map<String, Object> resultMap = Maps.newHashMap();
		boolean pretty = true;
		StringBuffer response = new StringBuffer();
		try {
			HttpClient httpClient = new HttpClient();
			PostMethod postMethod = new PostMethod(url);
			// FIXME
			// httpClient.getHostConfiguration().setHost("118.144.129.94");
			postMethod.getParams().setContentCharset("utf8");
			// TODO
			// http://wangking717.iteye.com/blog/889722
			// NameValuePair[] data = new NameValuePair[params.size()];
			// List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			//
			// for (String key : params.keySet()) {
			// nameValuePairList.add(new NameValuePair(key, params.get(key)));
			// }
			// for (int i = 0; i < nameValuePairList.size(); i++) {
			// data[i] = nameValuePairList.get(i);
			// }
			// NameValuePair[] data = (NameValuePair[]) nameValuePairList.toArray();
			// postMethod.setRequetBody(xml);
			// postMethod.setRequestBody(xml);
			// postMethod.setRequestHeader(new Header());
			// StringEntity myEntity = new StringEntity(xml, "UTF-8");
			postMethod.setRequestEntity(new StringRequestEntity(xml, "text/xml", "utf-8"));
			// postMethod.setRequestEntity(myEntity);
			postMethod.setRequestHeader("Content-type", "text/xml; charset=UTF-8");
			int statusCode = httpClient.executeMethod(postMethod);
			if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
				String trt = postMethod.getResponseBodyAsString();
				// 读取为 InputStream，在网页内容数据量大时候推荐使用
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(postMethod.getResponseBodyAsStream(), "utf8"));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty)
						response.append(line).append(System.getProperty("line.separator"));
					else
						response.append(line);
				}
				reader.close();
				postMethod.releaseConnection();
			}
			resultMap.put("status", 1);
			resultMap.put("result", response);
		} catch (IOException e) {
			e.printStackTrace();
			resultMap.put("status", 0);
			resultMap.put("result", null);
		}
		return resultMap;
	}

	/**
	 * java http 发送JSON数据
	 * 
	 * @param strURL
	 * @param params
	 * @return
	 */
	public static String postJSON(String strURL, String params) {
		try {
			URL url = new URL(strURL);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
			out.append(params);
			out.flush();
			out.close();
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			InputStream is = connection.getInputStream();
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				String result = new String(data, "UTF-8"); // utf-8编码
				System.out.println(result);
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "error"; // 自定义错误信息
	}

	public static String getIsLog() {
		return isLog;
	}

	public static void setIsLog(String isLog) {
		HttpCLientUtil.isLog = isLog;
	}


}