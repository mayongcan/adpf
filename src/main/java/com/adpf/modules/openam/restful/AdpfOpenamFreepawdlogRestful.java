/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.openam.restful;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import com.gimplatform.core.utils.UserAgentUtils;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.adpf.modules.openam.config.LoginboxConfig;
import com.adpf.modules.openam.entity.AdpfOpenamFreepawdlog;
import com.adpf.modules.openam.service.AdpfOpenamFreepawdlogService;
import com.adpf.modules.openam.util.FormatUtil;
import com.adpf.modules.openam.util.HMACSHA1;
import com.adpf.modules.openam.util.HttpUtil;
import com.adpf.modules.openam.util.RequestUtil;
import com.adpf.modules.openam.util.StringUtil;
import com.adpf.modules.openam.util.URLParser;
import com.adpf.modules.openam.util.XXTea;

/**
 * Restful接口
 * 
 * @version 1.0
 * @author
 */
@RestController
@RequestMapping("/api/adpf/openam")
public class AdpfOpenamFreepawdlogRestful {

	protected static final Logger logger = LogManager.getLogger(AdpfOpenamFreepawdlogRestful.class);

	@Autowired
	private AdpfOpenamFreepawdlogService adpfOpenamFreepawdlogService;

	private static final String CLIENT_ID = "8013417803"; // 开发平台分发的应用 ID
	private static final String APP_SECRET = "9CZ6BIyADWeiwZ4foVRDPuYfPRU98nMc"; // 开发平台分发的密钥

	/**
	 * 用于记录打开日志
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public JSONObject index(HttpServletRequest request) {
		return RestfulRetUtils.getRetSuccess();
	}

	@RequestMapping(value = "/reportIndex", method = RequestMethod.GET)
	public JSONObject reportIndex(HttpServletRequest request) {
		return RestfulRetUtils.getRetSuccess();
	}

	/**
	 * 获取列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public JSONObject getList(HttpServletRequest request, @RequestParam Map<String, Object> params) {
		JSONObject json = new JSONObject();
		try {
			UserInfo userInfo = SessionUtils.getUserInfo();
			if (userInfo == null)
				json = RestfulRetUtils.getErrorNoUser();
			else {
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request),
						SessionUtils.getPageSize(request));
				AdpfOpenamFreepawdlog adpfOpenamFreepawdlog = (AdpfOpenamFreepawdlog) BeanUtils.mapToBean(params,
						AdpfOpenamFreepawdlog.class);
				adpfOpenamFreepawdlog.setOrganizerId(userInfo.getOrganizerId());
				json = adpfOpenamFreepawdlogService.getList(pageable, adpfOpenamFreepawdlog, params);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51001", "获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}

//	/**
//	 * 新增信息
//	 * @param request
//	 * @param params
//	 * @return
//	 */
//	@RequestMapping(value="/add",method=RequestMethod.POST)
//	public JSONObject add(HttpServletRequest request, @RequestBody Map<String, Object> params){
//		JSONObject json = new JSONObject();
//		try{
//			UserInfo userInfo = SessionUtils.getUserInfo();
//			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
//			else {
//				json = adpfOpenamFreepawdlogService.add(params, userInfo);
//			}
//		}catch(Exception e){
//			json = RestfulRetUtils.getErrorMsg("51002","新增信息失败");
//			logger.error(e.getMessage(), e);
//		}
//		return json;
//	}

	/**
	 * 编辑信息
	 * 
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JSONObject edit(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		JSONObject json = new JSONObject();
		try {
			UserInfo userInfo = SessionUtils.getUserInfo();
			if (userInfo == null)
				json = RestfulRetUtils.getErrorNoUser();
			else {
				json = adpfOpenamFreepawdlogService.edit(params, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51003", "编辑信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 删除信息
	 * 
	 * @param request
	 * @param idsList
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JSONObject del(HttpServletRequest request, @RequestBody String idsList) {
		JSONObject json = new JSONObject();
		try {
			UserInfo userInfo = SessionUtils.getUserInfo();
			if (userInfo == null)
				json = RestfulRetUtils.getErrorNoUser();
			else {
				json = adpfOpenamFreepawdlogService.del(idsList, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004", "删除信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取报表信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getReportList", method = RequestMethod.POST)
	public JSONObject getReportList(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		JSONObject json = new JSONObject();
		try {
			UserInfo userInfo = SessionUtils.getUserInfo();
			if (userInfo == null)
				json = RestfulRetUtils.getErrorNoUser();
			else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (StringUtils.isNotBlank((String) params.get("startTime"))
						&& StringUtils.isNotBlank((String) params.get("endTime"))) {
					String startTime = (String) params.get("startTime");
					String endTime = (String) params.get("endTime");
					startTime += " 00:00:00";
					endTime += " 23:59:59";
					params.put("startTime", sdf.parse(startTime));
					params.put("endTime", sdf.parse(endTime));
				} else {
					// 初始页面 结束时间为当前日期，则开始时间是七天前
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(new Date());
					String endTime = sdf.format(calendar.getTime());
					calendar.add(Calendar.DATE, -7);
					String startTime = sdf.format(calendar.getTime());
					params.put("startTime", sdf.parse(startTime));
					params.put("endTime", sdf.parse(endTime));
				}
				AdpfOpenamFreepawdlog adpfOpenamFreepawdlog = (AdpfOpenamFreepawdlog) BeanUtils.mapToBean(params,
						AdpfOpenamFreepawdlog.class);
				json = adpfOpenamFreepawdlogService.getReportList(adpfOpenamFreepawdlog, params);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51001", "获取列表失败");
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
			json.put("retUrl", autoLoginUrlStrBuffer.toString());

		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("71002", "获取手机失败");
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
	@RequestMapping(value = "/getPhoneInfo", method = RequestMethod.POST)
	public JSONObject getPhoneInfo(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		JSONObject json = new JSONObject();
		logger.info(params);
		try {
			UserInfo userInfo = SessionUtils.getUserInfo();
//			if (userInfo == null) {
//				logger.info("111111111");
//				json = RestfulRetUtils.getErrorNoUser();
//			} else {
			JSONObject jobect = new JSONObject();
			String code = MapUtils.getString(params, "code", "");
			String retParas = "";
			Map<String, String> map = new HashMap();
			try {
				retParas = XXTea.decrypt(code, LoginboxConfig.CHARSET,
						StringUtil.toHex(LoginboxConfig.APP_SECRET, LoginboxConfig.CHARSET));
				map = URLParser.URLRequest("test?" + retParas);
				params.put("code", map.get("code"));
				AdpfOpenamFreepawdlog freelog = adpfOpenamFreepawdlogService.getByCode(map.get("code"));
				if (freelog != null) {
					if (StringUtils.isNotBlank(freelog.getMobile())) {
						json = RestfulRetUtils.getRetSuccess();
						json.put("mobileName", freelog.getMobile());
						return json;
					}

				}
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
			if (!"10000".equals(josn.getString("result"))) {
				String ipAddr = UserAgentUtils.getIpAddress(request);
				params.put("clientIp", ipAddr);
				adpfOpenamFreepawdlogService.add(params, josn, userInfo);
				json = RestfulRetUtils.getErrorMsg(josn.getString("result"), josn.getString("msg"));
				json.put("mobileName", "");
				return json;

			}

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

			String ipAddr = UserAgentUtils.getIpAddress(request);
			params.put("clientIp", ipAddr);
			adpfOpenamFreepawdlogService.add(params, jobect, userInfo);

			if (!"0".equals(jobect.getString("result"))) {
				json = RestfulRetUtils.getErrorMsg(josn.getString("result"), josn.getString("msg"));
				json.put("mobileName", "");
				return json;
			} else {
				json = RestfulRetUtils.getRetSuccess();
				json.put("mobileName", jobect.get("mobileName"));
			}

//			}

		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("71002", "获取手机失败");
			logger.error(e.getMessage(), e);
		}

		return json;
	}
}
