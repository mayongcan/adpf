package com.adpf.modules.openam.service;

import java.util.HashMap;
import java.util.Map;

import com.adpf.modules.openam.util.HttpUtil;
import com.adpf.modules.openam.util.RequestUtil;
import com.alibaba.fastjson.JSONObject;

public class Testc {

	private static final String CLIENT_ID = "8013417803"; // 开发平台分发的应用 ID
	private static final String APP_SECRET = "9CZ6BIyADWeiwZ4foVRDPuYfPRU98nMc"; // 开发平台分发的密钥

	public static void main(String args[]) {
		String url = "https://open.e.189.cn/api/logbox/oauth2/getPreMobileUrl.do";

		Map<String, String> requestData = new HashMap<String, String>();
		requestData.put("appKey", CLIENT_ID); // 公共参数，应用id
		requestData.put("clientType", "20100"); // 公共参数
		requestData.put("format", "json"); // 公共参数，时间戳
		requestData.put("preUrlCBN", "json"); // 公共参数，时间戳

		requestData.put("version", "v1.1"); // 公共参数，时间戳

		String paramsString = RequestUtil.getParamString(requestData, APP_SECRET);
		System.out.println(paramsString);
		String result = HttpUtil.doHttpPost(url, paramsString);
		System.out.println(result);

	}

}
