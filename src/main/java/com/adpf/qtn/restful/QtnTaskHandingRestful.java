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

import com.adpf.qtn.entity.QtnTaskHanding;
import com.adpf.qtn.service.QtnTaskHandingService;

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/adpf/qtn/task")
public class QtnTaskHandingRestful {

	protected static final Logger logger = LogManager.getLogger(QtnTaskHandingRestful.class);
    
    @Autowired
    private QtnTaskHandingService qtnTaskHandingService;
    
    @Autowired
    private UserInfoService userInfoService;


	/**
	 * 用于记录打开日志
	 * @param request
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public JSONObject index(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}
	
	/**
	 * 用于记录打开日志
	 * @param request
	 */
	@RequestMapping(value="/waitIndex", method=RequestMethod.GET)
	public JSONObject waitIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

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
				params.put("organizerId", userInfo.getOrganizerId());
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
				QtnTaskHanding qtnTaskHanding = (QtnTaskHanding)BeanUtils.mapToBean(params, QtnTaskHanding.class);				
				json = qtnTaskHandingService.getList(userInfo,pageable, qtnTaskHanding, params);
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
				json = qtnTaskHandingService.add(params, userInfo);
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
				json = qtnTaskHandingService.edit(params, userInfo);
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
				json = qtnTaskHandingService.del(idsList, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 获取所有列表
	 */
	
	@RequestMapping(value="/getWaitList",method=RequestMethod.GET)
	public JSONObject getWaitList(HttpServletRequest request,@RequestParam Map<String,Object>params) {
		JSONObject json =new JSONObject();
		try {
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) RestfulRetUtils.getErrorNoUser();
			else {
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
				json = qtnTaskHandingService.getWaitList(pageable,params,userInfo);
			}
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
			json = RestfulRetUtils.getErrorMsg("51008", "查询失败");
		}
		return json ;
	}

	/**
	 * 获取所有等待的列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getAllWaitList",method=RequestMethod.GET)
	public JSONObject getAllWaitList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				//不同机构不同机构id
				params.put("organizerId", userInfo.getOrganizerId());
				//查找所有在等待的号码
				params.put("status","0");
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
				QtnTaskHanding qtnTaskHanding = (QtnTaskHanding)BeanUtils.mapToBean(params, QtnTaskHanding.class);				
				json = qtnTaskHandingService.getList(userInfo,pageable, qtnTaskHanding, params);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 不需要登陆，根据userId获取等待列表
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/getWaitListByUserId",method=RequestMethod.GET)
	public JSONObject getWaitListByUserId(HttpServletRequest request,@RequestParam Map<String,Object>params) {
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
					json = qtnTaskHandingService.getWaitList(pageable,params,userInfo);
				}
			}
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
			json = RestfulRetUtils.getErrorMsg("51008", "查询失败");
		}
		return json ;
	}



}
