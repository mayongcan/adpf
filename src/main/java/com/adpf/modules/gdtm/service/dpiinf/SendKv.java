package com.adpf.modules.gdtm.service.dpiinf;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.adpf.modules.gdtm.util.HttpCLientUtil;
import com.adpf.modules.gdtm.util.MD5Util;
import com.adpf.modules.gdtm.util.StringUtils;
import com.alibaba.fastjson.JSONObject;

public class SendKv {

	/**
	 * 标识符
	 */
	static final String FLAG_01 = "\t";
	static final String FLAG_02 = "^";
	static final String FLAG_03 = "\\s*";

	private static final Logger logger = Logger.getLogger(SendKv.class);
	// 统计写入电话号码总数
	private static String mDntoken = "";

	private static String authCode = "";

	public static String sendMdnKvH(String setMdnUrl, String param, Business business) {

		String setReqUrl = setMdnUrl + mDntoken + param;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (int i = 0; i < 3; i++) {

			resultMap = HttpCLientUtil.get(setReqUrl);
			JSONObject itemJSONObjToken = JSONObject.parseObject((String) resultMap.get("result"));
			Integer code = (Integer) itemJSONObjToken.get("code");
			if (code == 200) {
				Object dataObj = itemJSONObjToken.get("data");
				logger.info(resultMap.toString());
				return (String) dataObj.toString();
			}
			if (code == 401 || code == 402 || code == 406 || code == 410 || code == 411 || code == 431) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				mDntoken = getToken(business);
				setReqUrl = setMdnUrl + mDntoken + param;
				continue;

			} else {
				// logger.info("结果异常编码：" + code + " " + message);
				return resultMap.toString();
			}

		}
		return resultMap.toString();

	}

	public static String getToken(Business business) {

		if (StringUtils.isNotBlank(mDntoken) && (!mDntoken.equals("null"))) {
			return "300";
		}

		String apiKey = business.getCompanyApikey();
		String password = business.getCompanySecretkey();
		String author = business.getKvmdnurl();

		if (StringUtils.isBlank(authCode)) {
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String publicKeyRequestAddress = String.format("http://%s/restful/system/publicKey.json?apiKey=%s", author,
					apiKey);
			Map<String, Object> map = HttpCLientUtil.get(publicKeyRequestAddress.replaceAll(FLAG_03, ""));

			JSONObject itemJSONObjToken = JSONObject.parseObject((String) map.get("result"));
			Integer code = (Integer) itemJSONObjToken.get("code");
			if (code == 200) {
				authCode = (String) itemJSONObjToken.get("data");
				// logger.info("获取授权码结束：" + authCode);
			} else {
				return map.toString();
			}
		}

		String sign = MD5Util.MD5(apiKey + password + authCode);

		String tokenRequestAddress = String.format("http://%s/restful/system/token.json?apiKey=%s&authCode=%s&sign=%s",
				author, apiKey, authCode, sign);
		Map<String, Object> tokenRequestMap = HttpCLientUtil.get(tokenRequestAddress.replaceAll(FLAG_03, ""));
		JSONObject itemJSONObjToken = JSONObject.parseObject((String) tokenRequestMap.get("result"));
		Integer code = (Integer) itemJSONObjToken.get("code");
		if (code == 200) {
			JSONObject tokenData = (JSONObject) itemJSONObjToken.get("data");
			mDntoken = (String) tokenData.get("token");
			return "200";
			// 放入缓存
		}
		return tokenRequestAddress;
	}

	/**
	 * 不够位数的在前面补0，保留num的长度位数字
	 * 
	 * @param code
	 * @return
	 */
	public static String autoGenericCode(int code, int num) {
		String result = "";
		// 保留num的位数
		// 0 代表前面补充0
		// num 代表长度为4
		// d 代表参数为正数型
		result = String.format("%0" + num + "d", code);

		return result;
	}

	/**
	 * 32位MD5加密的大写字符串
	 *
	 * @param s
	 * @return
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
