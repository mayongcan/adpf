package com.adpf.modules.gdtm.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.adpf.modules.gdtm.service.dpiinf.Business;

/**
 * 获取token
 * 
 * @author zzj
 * @createTime 2017年4月18日上午11:22:06
 * @version
 */
public class BdcscUtil {

	private static final Logger logger = Logger.getLogger(BdcscUtil.class);

	private static String authCode;

	private static String mDntoken;

	static final String FLAG_03 = "\\s*";

	public static Object getValue(String param) {
		Map map = new HashMap();
		String str = "";
		String key = "";
		Object value = "";
		char[] charList = param.toCharArray();
		boolean valueBegin = false;
		for (int i = 0; i < charList.length; i++) {
			char c = charList[i];
			if (c == '{') {
				if (valueBegin == true) {
					value = getValue(param.substring(i, param.length()));
					i = param.indexOf('}', i) + 1;
					key = key.replaceAll("\"", "");
					value = value.toString().replaceAll("\"", "");
					map.put(key, value);
				}
			} else if (c == ':') {
				valueBegin = true;
				key = str;
				str = "";
			} else if (c == '=') {
				valueBegin = true;
				key = str;
				str = "";
			} else if (c == ',') {
				valueBegin = false;
				value = str;
				str = "";
				key = key.replaceAll("\"", "");
				value = value.toString().replaceAll("\"", "");
				map.put(key, value);
			} else if (c == '}') {
				if (str != "") {
					value = str;
				}
				key = key.replaceAll("\"", "");
				value = value.toString().replaceAll("\"", "");
				map.put(key, value);
				return map;
			} else if (c != ' ') {
				str += c;
			}
		}
		return map;
	}

	public static String getAuthCode(Business business) {
		String apiKey = business.getCompanyApikey();
		String author = business.getKvmdnurl();
		String publicKeyRequestAddress = String.format("http://%s/restful/system/publicKey.json?apiKey=%s", author,
				apiKey);
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < 5; i++) {
			map = HttpCLientUtil.get(publicKeyRequestAddress.replaceAll(FLAG_03, ""));

			if (map.get("status").toString().equals("1")) {
				Object object = map.get("result");
				Map<String, Object> mapresutl1 = (Map<String, Object>) getValue(object.toString());
				if (null != mapresutl1 && mapresutl1.get("code").equals("200")) {
					authCode = (String) mapresutl1.get("data");
					return "1";
					// logger.info("获取授权码结束：" + authCode);
				} else {

				}
			}
		}
		return map.toString();
	}

	/**
	 * 用户写入setMdn接口
	 * 
	 * @param business
	 * @return
	 */
	public static String getToken2(Business business) {
		String apiKey = business.getCompanyApikey();
		String password = business.getCompanySecretkey();
		String author = business.getKvmdnurl();

		if (StringUtils.isBlank(authCode)) {
			getAuthCode(business);
		}

		String sign = MD5Util.MD5(apiKey + password + authCode);

		String tokenRequestAddress = String.format("http://%s/restful/system/token.json?apiKey=%s&authCode=%s&sign=%s",
				author, apiKey, authCode, sign);
		Map<String, Object> tokenRequestMap = HttpCLientUtil.get(tokenRequestAddress.replaceAll(FLAG_03, ""));
		if (tokenRequestMap.get("status").toString().equals("1")) {
			Object resultObj = tokenRequestMap.get("result");
			Map<String, Object> mapresutl1 = (Map<String, Object>) getValue(resultObj.toString());
			if (null != mapresutl1 && mapresutl1.get("code").equals("200")) {
				String tokenObj = (String) mapresutl1.get("data");
				Map<String, Object> tokenObj1 = (Map<String, Object>) getValue(tokenObj);

				mDntoken = (String) tokenObj1.get("token");
				return "1";
				// 放入缓存
			} else {
				return mapresutl1.get("code").toString();
			}
		}
		return "-2";
	}

	// 400：请求参数（mdn，province，type，month等）错误时
	// 401：用户token信息过期
	// 402：用户tokenid过期
	// 403：没有访问接口的权限
	// 404：path不匹配（工程名称，module，method，ref，tokenid）时返回404
	// 405：用户时间片到期（账号过期）
	// 406：没有传递tokenID
	// 407：调用缓存产品服务时，如果sessionID不存在或者对应的数据已经取出
	// 409：测试用的测试量已经用完
	// 410：当前使用的authocode已经生成token（请重新获取一个）
	// 411：当前authocode已经过期（请重新获取一个）
	// 412：该次请求的前置条件错误，如：指定的API-KEY不存在等
	// 413：apikey和authcode不匹配
	// 414：apikey和tokenID不匹配
	// 415：apikey不存在
	// 416：apikey已经被停用
	// 417：当前访问IP不在提供的白名单内
	// 418：获取令牌失败，认证错误。如：使用了错误的认证参数等。
	// 419：当前IP没有访问该接口的权限
	// 421：超过全局默认的标准限速：账号
	// 422：超过全局默认的标准限速：服务
	// 423：超过具体配置的限速：账号
	// 424：超过具体配置的限速：服务
	// 425：超过具体配置的产品限速：账号
	// 426：超过全局配置的产品限速：服务
	// 431：用户token信息没有传递
	// 500：运行时返回异常
	// 503：服务器处于超负载或正在维护停机
	// 511：调用外部服务异常
	public static String sendMdnKvH(String setMdnUrl) {

		Map<String, Object> resultMap = HttpCLientUtil.get(setMdnUrl);
		// logger.info(projectName + "setMdnUrl：" + setMdnUrl);
		if (null != resultMap && resultMap.get("status").toString().equals("1")) {
			Object resultObj = resultMap.get("result");
			Map<String, Object> mapresutl1 = (Map<String, Object>) BdcscUtil.getValue(resultObj.toString());
			if (null != mapresutl1) {

				if (mapresutl1.get("code").equals("200") || mapresutl1.get("code").equals("401")
						|| mapresutl1.get("code").equals("402")) {
					return (String) mapresutl1.get("code");
				}
			}
		} else {
			return resultMap.get("msg").toString();
		}
		return resultMap.toString();

	}

	/**
	 * 将三网明文手机号码放入表中
	 * 
	 * @param mdns
	 *            手机号码
	 * @return 调用接口状态
	 */
	public static String addMdnToKV(Business business, String key) {
		String status = "-920";
		Set<String> ms = business.getMdnSet();
		String apiKey = business.getCompanyApikey();

		if (StringUtils.isBlank(mDntoken)) {
			BdcscUtil.getToken2(business);
		}

		String setMdnUrl = "http://" + business.getKvmdnurl() + "/restful/pm-label/info/receiveData/" + apiKey + "/"
				+ mDntoken + ".json?key=" + key + "&value=" + buildMdnBunch(ms);
		for (int i = 0; i < 5; i++) {
			String ret = sendMdnKvH(setMdnUrl);
			if (ret.equals("200")) {
				status = "0";
				return status;
			}
			if (ret.equals("401") || ret.equals("402") || ret.equals("406") || ret.equals("410") || ret.equals("411")
					|| ret.equals("431")) {
				BdcscUtil.getToken2(business);
				setMdnUrl = "http://" + business.getKvmdnurl() + "/restful/pm-label/info/receiveData/" + apiKey + "/"
						+ mDntoken + ".json?key=" + key + "&value=" + buildMdnBunch(ms);
				// 获取token后，继续循环
				continue;
			}
			status = ret;
			return status;
		}
		return status;

	}

	/**
	 * 构建mdn串
	 * 
	 * @param business
	 *            厂商信息
	 * @return
	 */
	private static String buildMdnBunch(Set<String> mdnSet) {
		// logger.info(projectName+"mdn长度：" + mdnSet.size());
		if (null != mdnSet && mdnSet.size() > 0) {
			StringBuffer mdnBunch = new StringBuffer();
			for (String mdn : mdnSet) {
				mdnBunch.append(mdn).append(",");
			}
			String mdns = mdnBunch.toString().substring(0, mdnBunch.toString().lastIndexOf(","));
			// logger.info(projectName+"明文mdn串：" + mdns);
			return mdns;
		}
		return null;
	}

}
