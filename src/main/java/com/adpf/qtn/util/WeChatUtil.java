package com.adpf.qtn.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;

public class WeChatUtil {

	//测试号
//	private final static String APPID= "wxcc9947573d8a88a1";
//	private final static String APPSECRET="fd8585942ade53f34c871aac877daca6";
	
	
	
	//获取登陆access_token ，根据code
	private final static String GETTOKENBYCODE = " https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	//获取用户信息
	private final static String GETUSERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	//获取code请求snsapi_userinfo
	private final static String getCodePath ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
	
	//检验access_token是否有效
	private final static String  checkToken= "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
	
	
	
	/**
	 * 获取登陆用的access_token,有效期两个小时，可用来获取用户信息
	 *@param code  
	 */
	public JSONObject getTokenByCode(String code,String appId, String appSecret) {
		String path = GETTOKENBYCODE.replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", code);
		System.out.println("------------登陆用access_token："+path);
		JSONObject json = HttpRequest(path);
		return json;
		
	}
	
	/**
	 * 获取用户信息
	 * @param token 普通token
	 * @param openId 微信用户标识
	 * @return
	 */
	public JSONObject getUserInfo(String token,String openId) {
		String path = GETUSERINFO.replace("ACCESS_TOKEN", token).replace("OPENID", openId);
		System.out.println("------------获取用户信息url："+path);
		JSONObject json = HttpRequest(path);
		return json;
	}
	
	/**
	 * 检验access_token是否有效
	 * @param requestUrl
	 * @return
	 */
	public JSONObject checkToken(String token,String openId) {
		String path = checkToken.replace("ACCESS_TOKEN", token).replace("OPENID", openId);
		System.out.println("------------检查access_token是否有效url："+path);
		JSONObject json = HttpRequest(path);
		return json;
	}
	
	
	
	
	private  JSONObject HttpRequest(String requestUrl) {
		JSONObject jsonObject=null;
		StringBuffer buffer=new StringBuffer();
		InputStream inputStream=null;
		try {
			URL url=new URL(requestUrl); 	
			HttpsURLConnection httpsURLConnection=(HttpsURLConnection) url.openConnection();
			httpsURLConnection.setDoOutput(true);
			httpsURLConnection.setDoInput(true);
			httpsURLConnection.setUseCaches(false);
			//设置请求方式
			httpsURLConnection.setRequestMethod("GET");
			httpsURLConnection.connect();
			inputStream=httpsURLConnection.getInputStream();
			InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"utf-8");
			BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

			String str=null;
			while((str=bufferedReader.readLine())!=null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			//释放资源
			inputStream.close();
			inputStream=null;
			httpsURLConnection.disconnect();
			jsonObject=JSONObject.parseObject(buffer.toString());
			System.out.println(buffer.toString());
		}catch(ConnectException ce) {
			ce.printStackTrace();
			System.out.println("WeChat server connection timed out");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("http request error:{}");
		}finally {
			try {
				if(inputStream!=null) {
					inputStream.close();
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}
}
