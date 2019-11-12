package com.adpf.restful;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adpf.modules.gdtm.service.hbase.test.HBaseCrudServiceTest;
import com.adpf.modules.openam.config.LoginboxConfig;
import com.adpf.modules.openam.service.AdpfOpenamFreepawdlogService;
import com.adpf.modules.openam.util.FormatUtil;
import com.adpf.modules.openam.util.HMACSHA1;
import com.adpf.modules.openam.util.HttpUtil;
import com.adpf.modules.openam.util.RequestUtil;
import com.adpf.modules.openam.util.StringUtil;
import com.adpf.modules.openam.util.URLParser;
import com.adpf.modules.openam.util.XXTea;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.UserAgentUtils;

/**
 * Restful接口
 * 
 * @version 1.0
 * @author
 */
@RestController
@RequestMapping("/api/adpf/inf")
public class GdtmInfRestful {

	private static final String CLIENT_ID = "8013417803"; // 开发平台分发的应用 ID
	private static final String APP_SECRET = "9CZ6BIyADWeiwZ4foVRDPuYfPRU98nMc"; // 开发平台分发的密钥

	@Autowired
	private HBaseCrudServiceTest hbaseCrudService;

	@Autowired
	private AdpfOpenamFreepawdlogService adpfOpenamFreepawdlogService;

	protected static final Logger logger = LogManager.getLogger(GdtmInfRestful.class);

	/**
	 * 用于记录打开日志
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public JSONObject index(HttpServletRequest request) {
		return RestfulRetUtils.getRetSuccess();
	}

	/**
	 * 查询表描述和根据tableName获取表中的数据和根据row key获取表中的该行数据
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/queryTable", method = RequestMethod.GET)
	public JSONObject queryList(HttpServletRequest request, @RequestParam Map<String, Object> params) {
		JSONObject json = new JSONObject();
		String rowKey = (String) params.get("rowKey");
		String tableName = (String) params.get("tableName");
		try {
			if ((rowKey == null || rowKey == "") && (tableName == null || tableName == "")) {
				// 查询表描述
				json = hbaseCrudService.getTableList();
			} else if (rowKey == null || rowKey == "") {
				// 根据tableName获取表中的数据
				json = hbaseCrudService.scanTable(tableName);
				logger.info("table");
			} else {
				// 根据row key获取表中的该行数据
				json = hbaseCrudService.get(tableName, rowKey);
				logger.info("rowKey");
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("71000", "查询失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 表详情
	 * 
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public JSONObject details(HttpServletRequest request, @RequestParam String tableName) {
		JSONObject json = new JSONObject();
		try {
			json = hbaseCrudService.getTableDetails(tableName);
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("71004", "错误，可能没有该表");
			logger.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 单条插入和批量插入
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public JSONObject batch(HttpServletRequest request, @RequestParam Map<String, Object> params) {
		JSONObject json = new JSONObject();
		logger.info(params);
		String tableName = (String) params.get("tableName");
		String rowKey = (String) params.get("rowKey");
		String columnFamily = (String) params.get("columnFamily");
		String column = (String) params.get("column");
		String column2 = (String) params.get("column2");
		String data = (String) params.get("data");
		String filePath = (String) params.get("filePath");
		try {
			if (column2 == null || column2 == "") {
				// 单条插入
				hbaseCrudService.put(tableName, rowKey, columnFamily, column, data);
			} else {
				// 批量插入
				hbaseCrudService.putTextList(filePath, tableName, columnFamily, column, column2);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("71002", "插入失败");
			logger.error(e.getMessage(), e);
		}
		json = RestfulRetUtils.getRetSuccess();
		return json;
	}

	/**
	 * 创建表
	 * 
	 * @param tableName
	 * @param columnFamilies
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public JSONObject create(HttpServletRequest request, @RequestParam String tableName,
			@RequestParam String... columnFamilies) {
		JSONObject json = new JSONObject();
		try {
			hbaseCrudService.createTable(tableName, columnFamilies);
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("71003", "创建失败");
			logger.error(e.getMessage(), e);
		}
		json = RestfulRetUtils.getRetSuccess();
		return json;
	}

	/**
	 * 删除表
	 * 
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JSONObject delete(HttpServletRequest request, @RequestParam String tableName) {
		JSONObject json = new JSONObject();
		try {
			hbaseCrudService.deleteTable(tableName);
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("71004", "错误，可能没有该表");
			logger.error(e.getMessage(), e);
		}
		json = RestfulRetUtils.getRetSuccess();
		return json;
	}

	/**
	 * 获取url地址
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/getPreMobileUrl", method = RequestMethod.POST)
	public JSONObject getPreMobileUrl(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		JSONObject json = new JSONObject();
		logger.info(params);

		String url = "https://open.e.189.cn/api/logbox/oauth2/getPreMobileUrl.do";

		Map<String, String> requestData = new HashMap<String, String>();
		requestData.put("appKey", CLIENT_ID); // 公共参数，应用id
		requestData.put("clientType", "20100"); // 公共参数
		requestData.put("format", "json"); // 公共参数，时间戳
		requestData.put("preUrlCBN", "json"); // 公共参数，时间戳

		requestData.put("version", "v1.1"); // 公共参数，时间戳

		String paramsString = RequestUtil.getParamString(requestData, APP_SECRET);
		logger.info(paramsString);
		String result = HttpUtil.doHttpPost(url, paramsString);
		JSONObject jobect = JSONObject.parseObject(result);
		logger.info(result);
		try {
			adpfOpenamFreepawdlogService.add(params, jobect, null);

			json = RestfulRetUtils.getRetSuccess();
			json.put("preUrl", jobect.get("preUrl"));
			json.put("aesCacheKey", jobect.get("aesCacheKey"));
			json.put("clientType", "20100");
			json.put("returnUrl", "http://www.test.com");
			json.put("appKey", CLIENT_ID);

			json.put("version", "v1.1");
			json.put("isOauth2", "true");
			json.put("state", "");
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("71002", "插入失败");
			logger.error(e.getMessage(), e);
		}

		return json;
	}

	/**
	 * 获取url地址
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/getMobile", method = RequestMethod.POST)
	public JSONObject getMobile(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		JSONObject json = new JSONObject();
		logger.info(params);

		String status = MapUtils.getString(params, "status", "");
		JSONObject jobect = new JSONObject();

		Map<String, String> map = new HashMap();
		if ("1".equals(status)) {
			String code = MapUtils.getString(params, "code", "");
			String retParas = "";

			try {
				retParas = XXTea.decrypt(code, LoginboxConfig.CHARSET,
						StringUtil.toHex(LoginboxConfig.APP_SECRET, LoginboxConfig.CHARSET));
				map = URLParser.URLRequest("test?" + retParas);
				params.put("code", map.get("code"));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (status.equals("2")) {
			String code = MapUtils.getString(params, "code", "");
			String retParas = "";

			try {
				retParas = XXTea.decrypt(code, LoginboxConfig.CHARSET,
						StringUtil.toHex(LoginboxConfig.APP_SECRET, LoginboxConfig.CHARSET));
				map = URLParser.URLRequest("test?" + retParas);
				params.put("code", map.get("code"));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info("retParas：" + retParas);
			String url = "https://open.e.189.cn/api/logbox/oauth2/accessToken.do";

			Map<String, String> requestData = new HashMap<String, String>();
			requestData.put("appId", LoginboxConfig.APP_ID); // 公共参数，应用id
			requestData.put("grantType", "authorization_login"); // 公共参数
			requestData.put("code", map.get("code")); // 公共参数
			requestData.put("format", "json"); // 公共参数，时间戳
			String paramsString = RequestUtil.getParamString(requestData, APP_SECRET);
			logger.info(paramsString);
			String result = HttpUtil.doHttpPost(url, paramsString);

			JSONObject josn = JSONObject.parseObject(result);
			logger.info("result=" + result);

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

			requestDataUser.put("securityId", code); // 私有参数
			requestDataUser.put("authSecurityId", "0"); // 私有参数

			paramsString = RequestUtil.getParamString(requestDataUser, APP_SECRET);
			logger.info(paramsString);
			result = HttpUtil.doHttpPost(url, paramsString);
			logger.info("result=" + result);
			jobect = JSONObject.parseObject(result);
			logger.info(result);

		}
		try {
			String ipAddr = UserAgentUtils.getIpAddress(request);
			params.put("clientIp", ipAddr);
			adpfOpenamFreepawdlogService.addIm(params, jobect, null);

			json = RestfulRetUtils.getRetSuccess();
			json.put("mobileName", jobect.get("mobileName"));

		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("71002", "插入失败");
			logger.error(e.getMessage(), e);
		}

		return json;
	}

	/**
	 * 获取url地址
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/getUrl", method = RequestMethod.POST)
	public JSONObject getUrl(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		JSONObject json = new JSONObject();
		logger.info(params);
		try {
			String url = MapUtils.getString(params, "url", "");
			logger.info("url=" + url);
			JSONObject parasParamJsonObj = new JSONObject();
			parasParamJsonObj.put("timeStamp", System.currentTimeMillis());
			parasParamJsonObj.put("returnURL", url);
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
				sign = HMACSHA1.getSignature(LoginboxConfig.APP_ID + LoginboxConfig.CLIENT_TYPE_WAP
						+ LoginboxConfig.FORMAT + LoginboxConfig.VERSION_WAP + paras, LoginboxConfig.APP_SECRET)
						.toUpperCase();
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
			logger.info(" autoLoginUrlStrBuffer.toString()=" + autoLoginUrlStrBuffer.toString());
			json.put("retUrl", autoLoginUrlStrBuffer.toString());

		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("71002", "获取手机失败");
			logger.error(e.getMessage(), e);
		}

		return json;
	}

}
