package com.adpf.modules.openam.service;

import com.adpf.modules.openam.config.LoginboxConfig;
import com.adpf.modules.openam.util.FormatUtil;
import com.adpf.modules.openam.util.HMACSHA1;
import com.adpf.modules.openam.util.StringUtil;
import com.adpf.modules.openam.util.XXTea;
import com.alibaba.fastjson.JSONObject;

public class WebLoginbox {
	public static void main(String[] args) {
		System.out.println(getUnifyAccountLoginUrl());
	}

	/**
	 * 获取Web登录框接口地址
	 * 
	 * @return Web登录框接口地址
	 */
	public static String getUnifyAccountLoginUrl() {
		JSONObject parasParamJsonObj = new JSONObject();
		parasParamJsonObj.put("timeStamp", System.currentTimeMillis());
		parasParamJsonObj.put("returnURL", LoginboxConfig.RETURN_URL);

		String paras = "";
		try {
			paras = XXTea.encrypt(FormatUtil.json2UrlParam(
					parasParamJsonObj.toString(), false, null),
					LoginboxConfig.CHARSET, StringUtil.toHex(
							LoginboxConfig.APP_SECRET, LoginboxConfig.CHARSET));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sign = "";
		try {
			sign = HMACSHA1.getSignature(
					LoginboxConfig.APP_ID + LoginboxConfig.CLIENT_TYPE_WEB
							+ LoginboxConfig.FORMAT
							+ LoginboxConfig.VERSION_WEB + paras,
					LoginboxConfig.APP_SECRET).toUpperCase();
		} catch (Exception e) {
		}

		JSONObject reqJsonObj = new JSONObject();
		reqJsonObj.put("appId", LoginboxConfig.APP_ID);
		reqJsonObj.put("clientType", LoginboxConfig.CLIENT_TYPE_WEB);
		reqJsonObj.put("format", LoginboxConfig.FORMAT);
		reqJsonObj.put("version", LoginboxConfig.VERSION_WEB);
		reqJsonObj.put("paras", paras);
		reqJsonObj.put("sign", sign);

		StringBuffer unifyAccountLoginUrlStrBuffer = new StringBuffer();
		unifyAccountLoginUrlStrBuffer
				.append(LoginboxConfig.UNIFY_ACCOUNT_LOGIN_URL);
		unifyAccountLoginUrlStrBuffer.append("?");
		unifyAccountLoginUrlStrBuffer.append(FormatUtil.json2UrlParam(
				reqJsonObj.toString(), false, null));
		return unifyAccountLoginUrlStrBuffer.toString();
	}
}