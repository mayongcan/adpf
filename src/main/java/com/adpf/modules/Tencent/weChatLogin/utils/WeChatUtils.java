package com.adpf.modules.Tencent.weChatLogin.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
public class WeChatUtils {
	private static final String appId = "wx2aa1aa6d129512aa";
	private static final String secret = "3d34957ef6abd011c066fe931b0fd822";
	private static String TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	private static String USERINFO_URL1 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
	private static String USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	public JSONObject token(String code) {
		HttpdoGet doGet = new HttpdoGet();
		JSONObject json = new JSONObject();
		String token_url = TOKEN_URL.replace("APPID", appId).replace("SECRET", secret).replace("CODE", code);
		json = doGet.doGet(token_url);
		return json;
	}
	
	public JSONObject userInfo(String token,String openId) {
		HttpdoGet doGet = new HttpdoGet();
		JSONObject json = new JSONObject();
		String userInfo = USERINFO_URL.replace("ACCESS_TOKEN", token).replace("OPENID", openId);
		json = doGet.doGet(userInfo);
		return json;	
	}
}
