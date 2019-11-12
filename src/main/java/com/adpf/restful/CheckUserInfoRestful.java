/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.restful;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.service.UserInfoService;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;


/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController 
@RequestMapping("/api/adpf/userInfo")
public class CheckUserInfoRestful {

	protected static final Logger logger = LogManager.getLogger(CheckUserInfoRestful.class);

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 用于记录打开日志
	 * @param request
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public JSONObject index(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}


	@RequestMapping(value="/checkOpenId", method=RequestMethod.POST)
	public JSONObject checkOpenId(HttpServletRequest request,@RequestBody Map<String,Object>params){
		JSONObject json = new JSONObject();
		try {
			UserInfo  userInfo = userInfoService.findByOpenId((String)params.get("openId"));
			if(userInfo != null) {
				return  RestfulRetUtils.getRetSuccess(userInfo);
			}else {
				return RestfulRetUtils.getErrorMsg("000001","不存在openId");
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("00002", "解锁账号信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}

	@RequestMapping(value="/saveUserInfo" ,method=RequestMethod.POST)
	public JSONObject saveUserInfo (HttpServletRequest request,@RequestBody Map<String,Object>params) {
		JSONObject json = new JSONObject();
		try {
			UserInfo  userInfo = (UserInfo)BeanUtils.mapToBean(params, UserInfo.class);
			UserInfo newUserInfo = userInfoService.getByUserCode(userInfo.getUserCode());
			newUserInfo.setOpenId(userInfo.getOpenId() != null ? userInfo.getOpenId() : "");
			if( newUserInfo != null) {
				json = userInfoService.updateOpenIdByUserId(newUserInfo);
			}else {
				json =  RestfulRetUtils.getErrorMsg("00004", "不存在该用户");
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("00003", "保存用户信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;

	}

}
