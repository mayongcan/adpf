/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.restful;

import java.io.File;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import com.mysql.fabric.xmlrpc.base.Params;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.adpf.modules.gdtm.util.StringUtils;
import com.adpf.qtn.entity.QtnTakeNumberInfo;
import com.adpf.qtn.service.QtnTakeNumberInfoService;
import com.adpf.qtn.service.QtnTaskHandingService;
import com.adpf.qtn.service.QtnWechatInfoService;
import com.adpf.qtn.util.CallUtil;
import com.adpf.qtn.util.RedisUtilsExtend;
import com.adpf.qtn.util.WeChatUtil;

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/adpf/wechat")
public class WeChatInfoRestful {

	protected static final Logger logger = LogManager.getLogger(WeChatInfoRestful.class);

	@Value("${resourceServer.appId}")
	private String appId;
	
	@Value("${resourceServer.appSecret}")
	private String appSecret;
	
	@Autowired
	private QtnWechatInfoService qtnWechatInfoService;

	@Autowired
	private QtnTakeNumberInfoService qtnTakeNumberInfoService;

	/**
	 * 用于记录打开日志
	 * @param request
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public JSONObject index(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}


	/**
	 * snsapi_userinfo方式
	 * 根据code获取access_token
	 * @param request
	 * @param params
	 * @return
	 */
//	@RequestMapping(value="/getTokenByCode",method=RequestMethod.GET)
//	public JSONObject getTokenByCode(HttpServletRequest request,@RequestParam Map<String,Object>params) {
//		JSONObject json =new JSONObject();
//		try {
//			WeChatUtil util =new WeChatUtil();
//			RedisUtilsExtend redis =new RedisUtilsExtend();
//
//			//根据code获取access_token和openId
//			if(params.get("code") != null) {
//				JSONObject codeJson = util.getTokenByCode((String)params.get("code"));
//				String access_token = codeJson.getString("access_token");
//				String openid = codeJson.getString("openid");
//				Map<String,Object> map =new HashMap<String,Object>();
//				map.put("openId",openid);
//				JSONObject wechatJson = qtnWechatInfoService.findOneByOpenId(params);
//				if(wechatJson.getLong("total") == 0) {//数据库不存在改openId再存储
//					JSONObject userInfoJson =util.getUserInfo(access_token, openid);
//					if(userInfoJson != null) {
//						map.put("name", userInfoJson.getString("nickname"));
//						map.put("sex", userInfoJson.getString("sex"));
//						map.put("province", userInfoJson.getString("province"));
//						map.put("city", userInfoJson.getString("city"));
//						map.put("country", userInfoJson.getString("country"));
//						qtnWechatInfoService.add(map,  new UserInfo());
//					}
//				}
//				redis.hset("wechat", openid, access_token, 2592000);//失效时间30天
//				json = RestfulRetUtils.getRetSuccess(openid);
//			}
//		}catch(Exception e){
//			logger.error(e.getMessage(),e);
//			json = RestfulRetUtils.getErrorMsg("51002","获取access_token失败");
//		}
//		return json;
//	}

	
	/**
	 * snsapi_base方式
	 * 根据code获取access_token
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/getTokenByCode",method=RequestMethod.GET)
	public JSONObject getTokenByCode(HttpServletRequest request,@RequestParam Map<String,Object>params) {
		logger.error("----------------------------------------------------------进入了getTokenByCode");
		JSONObject json =new JSONObject();
		try {
			WeChatUtil util =new WeChatUtil();
			RedisUtilsExtend redis =new RedisUtilsExtend();
			System.out.println("进入了方法------------------------------------------");
			//根据code获取access_token和openId
			if(params.get("code") != null) {
				JSONObject codeJson = util.getTokenByCode((String)params.get("code"),appId,appSecret);
				String access_token = codeJson.getString("access_token");
				String openid = codeJson.getString("openid");
				System.out.println("----------------------------------------------"+openid);
				if(StringUtils.isBlank(access_token)||StringUtils.isBlank(openid) ){
					json = RestfulRetUtils.getErrorMsg("51002","获取access_token/openid失败");
					return json;
				}
				Map<String,Object> map =new HashMap<String,Object>();
				map.put("openId",openid);
				JSONObject wechatJson = qtnWechatInfoService.findOneByOpenId(params);
				if(wechatJson.getLong("total") == 0) {//数据库不存在改openId再存储
					qtnWechatInfoService.add(map,  new UserInfo());
				}
				redis.hset("wechat", openid, access_token, 2592000);//失效时间30天
				json = RestfulRetUtils.getRetSuccess(openid);
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			json = RestfulRetUtils.getErrorMsg("51002","获取access_token失败");
		}
		return json;
	}
	
	@RequestMapping(value="/takeNumber",method=RequestMethod.POST)
	public JSONObject takeNumber (HttpServletRequest request, @RequestBody Map<String,Object> params) {
		logger.error("----------------------------------------------------------进入了takeNumber");

		JSONObject json =new JSONObject();
		try {
			if(StringUtils.isBlank(params.get("openId").toString()) || StringUtils.isBlank(params.get("id").toString())) {
				json = RestfulRetUtils.getErrorMsg("51003","取号失败");
			}
			RedisUtilsExtend redisUtils =new RedisUtilsExtend();
			
			if(StringUtils.isBlank(redisUtils.hget("wechat", params.get("openId").toString()))) {
				json = RestfulRetUtils.getErrorMsg("51003","取号失败");
			}else {
				json = qtnTakeNumberInfoService.takeNumber(params, new UserInfo());
			}
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
			json =RestfulRetUtils.getErrorMsg("51003","取号失败");
		}
		return json ;
	}
}


