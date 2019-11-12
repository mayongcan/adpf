package com.adpf.modules.openam.service;

import java.util.HashMap;
import java.util.Map;

import com.adpf.modules.openam.config.LoginboxConfig;
import com.adpf.modules.openam.util.HMACSHA1;
import com.adpf.modules.openam.util.HttpUtil;
import com.adpf.modules.openam.util.RequestUtil;
import com.adpf.modules.openam.util.StringUtil;
import com.adpf.modules.openam.util.URLParser;
import com.adpf.modules.openam.util.XXTea;
import com.alibaba.fastjson.JSONObject;

public class Client {

	private static final String CLIENT_ID = "8013417803"; // 开发平台分发的应用 ID
	private static final String APP_SECRET = "9CZ6BIyADWeiwZ4foVRDPuYfPRU98nMc"; // 开发平台分发的密钥

	public static void main(String[] args) {

		Map<String, String> map1 = new HashMap();

		String paras = "http://www.test.com/?appId=8013417803&paras=26593E1E5BD1BB87CBF45021681495F7D2A06FF9FFA742743B78D1F276E865E1C5BF85F5B03BDD0F99D8F8F704A7366DCF23D7269A0115B45A43FF502A440CCC6479EF69092284ADCC15F4A2586F13D70B4CCA5F2F16E5EC36FED57B&sign=C2E8BECBC3A180D34AC264805126299D383B69CE";
		map1 = URLParser.URLRequest(paras);

		Map<String, String> map = new HashMap();
		try {
			String retParas = XXTea.decrypt(map1.get("paras"), LoginboxConfig.CHARSET,
					StringUtil.toHex(LoginboxConfig.APP_SECRET, LoginboxConfig.CHARSET));
			System.out.println("retParas：" + retParas);
			map = URLParser.URLRequest("test?" + retParas);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String url = "https://open.e.189.cn/api/logbox/oauth2/accessToken.do";

		Map<String, String> requestData = new HashMap<String, String>();
		requestData.put("appId", LoginboxConfig.APP_ID); // 公共参数，应用id
		requestData.put("grantType", "authorization_login"); // 公共参数
		requestData.put("code", map.get("code")); // 公共参数
		requestData.put("format", "json"); // 公共参数，时间戳

		String paramsString = RequestUtil.getParamString(requestData, APP_SECRET);
		System.out.println(paramsString);
		String result = HttpUtil.doHttpPost(url, paramsString);

		JSONObject josn = JSONObject.parseObject(result);
		System.out.println("result=" + result);

		String clientIp = "127.0.0.1";
		String clientType = "1";
		String timeStamp = String.valueOf(System.currentTimeMillis());
		String version = "v1.5";
		url = "https://open.e.189.cn/api/oauth2/account/userInfo.do";

		Map<String, String> requestDataUser = new HashMap<String, String>();
		requestDataUser.put("clientId", CLIENT_ID); // 公共参数，应用id
		requestDataUser.put("timeStamp", timeStamp); // 公共参数，时间戳
		requestDataUser.put("accessToken", josn.getString("accessToken")); // 公共参数
		requestDataUser.put("version", version); // 公共参数
		requestDataUser.put("clientIp", clientIp); // 公共参数
		requestDataUser.put("clientType", clientType); // 公共参数

		requestDataUser.put("securityId", map.get("code")); // 私有参数
		requestDataUser.put("authSecurityId", "0"); // 私有参数

		paramsString = RequestUtil.getParamString(requestDataUser, APP_SECRET);
		System.out.println(paramsString);
		result = HttpUtil.doHttpPost(url, paramsString);
		System.out.println("result=" + result);
	}
}
