package com.adpf.modules.openam.service;

import com.adpf.modules.openam.config.LoginboxConfig;
import com.adpf.modules.openam.util.FormatUtil;
import com.adpf.modules.openam.util.HMACSHA1;
import com.adpf.modules.openam.util.StringUtil;
import com.adpf.modules.openam.util.XXTea;
import com.alibaba.fastjson.JSONObject;

public class WapLoginbox {
	public static void main(String[] args) {
		System.out.println(getAutoLoginUrl());
	}

	/**
	 * 获取WAP登录框接口地址
	 * 
	 * @return WAP登录框接口地址
	 */
	public static String getAutoLoginUrl() {
		JSONObject parasParamJsonObj = new JSONObject();
		parasParamJsonObj.put("timeStamp", System.currentTimeMillis());
		parasParamJsonObj.put("returnURL", LoginboxConfig.RETURN_URL);
		if (!StringUtil.isEmpty(LoginboxConfig.LOGIN_TYPE))
			parasParamJsonObj.put("loginType", LoginboxConfig.LOGIN_TYPE);
		if (!StringUtil.isEmpty(LoginboxConfig.QA_URL))
			parasParamJsonObj.put("qaUrl", LoginboxConfig.QA_URL);
		if (!StringUtil.isEmpty(LoginboxConfig.OTHER_LOGIN_URL))
			parasParamJsonObj.put("otherLoginUrl", LoginboxConfig.OTHER_LOGIN_URL);
		// TODO 更多可选参数，请咨询相关人员

		String paras = "";
		try {
			paras = XXTea.encrypt(FormatUtil.json2UrlParam(parasParamJsonObj.toString(), false, null),
					LoginboxConfig.CHARSET, StringUtil.toHex(LoginboxConfig.APP_SECRET, LoginboxConfig.CHARSET));
		} catch (Exception e) {
		}

		String sign = "";
		try {
			sign = HMACSHA1.getSignature(LoginboxConfig.APP_ID + LoginboxConfig.CLIENT_TYPE_WAP + LoginboxConfig.FORMAT
					+ LoginboxConfig.VERSION_WAP + paras, LoginboxConfig.APP_SECRET).toUpperCase();
		} catch (Exception e) {
		}

		JSONObject reqJsonObj = new JSONObject();
		reqJsonObj.put("appId", LoginboxConfig.APP_ID);
		reqJsonObj.put("clientType", LoginboxConfig.CLIENT_TYPE_WAP);
		reqJsonObj.put("format", LoginboxConfig.FORMAT);
		reqJsonObj.put("version", LoginboxConfig.VERSION_WAP);
		reqJsonObj.put("paras", paras);
		reqJsonObj.put("sign", sign);

		StringBuffer autoLoginUrlStrBuffer = new StringBuffer();
		autoLoginUrlStrBuffer.append(LoginboxConfig.AUTO_LOGIN_URL);
		autoLoginUrlStrBuffer.append("?");
		autoLoginUrlStrBuffer.append(FormatUtil.json2UrlParam(reqJsonObj.toString(), false, null));
		return autoLoginUrlStrBuffer.toString();
	}
}