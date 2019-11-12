/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.restful;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.service.UserInfoService;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.adpf.qtn.entity.QtnWindowInfo;
import com.adpf.qtn.service.QtnWindowInfoService;

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/adpf/qtn/window")
public class QtnWindowInfoRestful {

	protected static final Logger logger = LogManager.getLogger(QtnWindowInfoRestful.class);

	@Autowired
	private QtnWindowInfoService qtnWindowInfoService;
	
	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 用于记录打开日志
	 * @param request
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public JSONObject index(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getList",method=RequestMethod.GET)
	public JSONObject getList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				params.put("organizerId",userInfo.getOrganizerId());
				params.put("userId", userInfo.getUserId());
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
				QtnWindowInfo qtnWindowInfo = (QtnWindowInfo)BeanUtils.mapToBean(params, QtnWindowInfo.class);				
				json = qtnWindowInfoService.getList(pageable, qtnWindowInfo, params);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}


	/**
	 * 新增信息
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public JSONObject add(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = qtnWindowInfoService.add(params, userInfo);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51002","新增信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 编辑信息
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public JSONObject edit(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = qtnWindowInfoService.edit(params, userInfo);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51003","编辑信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 删除信息
	 * @param request
	 * @param idsList
	 * @return
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public JSONObject del(HttpServletRequest request,@RequestBody String idsList){
		JSONObject json = new JSONObject();
		try {
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = qtnWindowInfoService.del(idsList, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 管理员用户关联窗口
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/relevanceUser",method = RequestMethod.POST)
	public JSONObject relevanceUser(HttpServletRequest request,@RequestBody Map<String,Object>params) {
		JSONObject json =new JSONObject();
		try {
			UserInfo userInfo = SessionUtils.getUserInfo();
//			if(userInfo.getUserId())
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = qtnWindowInfoService.editUser(params, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51005","关联管理员失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 顺呼
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/sequenceCall",method=RequestMethod.POST)
	public JSONObject sequenceCall(HttpServletRequest request,@RequestBody Map<String,Object> params) {
		JSONObject json =new JSONObject();
		UserInfo userInfo =SessionUtils.getUserInfo();
		try {
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = qtnWindowInfoService.call(params,userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51005","顺呼失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 回呼
	 * @param request
	 * @param params
	 * @return
	 */

	@RequestMapping(value="/returnCall",method=RequestMethod.POST)
	public JSONObject returnCall(HttpServletRequest request,@RequestBody Map<String,Object> params) {
		JSONObject json =new JSONObject();
		UserInfo userInfo =SessionUtils.getUserInfo();
		try {
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51005","回呼失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 结束办理
	 * @param request
	 * @param params
	 * @return
	 */

	@RequestMapping(value="/end",method=RequestMethod.POST)
	public JSONObject end(HttpServletRequest request,@RequestBody Map<String,Object> params) {
		JSONObject json =new JSONObject();
		UserInfo userInfo =SessionUtils.getUserInfo();
		try {
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = qtnWindowInfoService.editNumberAndStatus(params, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51005","结束办理失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 全屏页，显示所有窗口信息
	 */
	@RequestMapping(value="/getAllWindowInfo",method=RequestMethod.GET)
	public JSONObject getAllWindowInfo(HttpServletRequest request,@RequestParam Map<String,Object> params) {
		JSONObject json =new JSONObject();
		try {
			UserInfo user = (UserInfo)BeanUtils.mapToBean(params, UserInfo.class);
			if(user == null) RestfulRetUtils.getErrorNoUser();
			else {
				UserInfo userInfo = userInfoService.getByUserId(user.getUserId());
				if(userInfo == null)RestfulRetUtils.getErrorNoUser();
				else {
					Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
					params.remove("userId");
					params.put("organizerId", userInfo.getOrganizerId());
					json = qtnWindowInfoService.getList(pageable, new QtnWindowInfo(), params);
				}
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51005","获取信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
}
