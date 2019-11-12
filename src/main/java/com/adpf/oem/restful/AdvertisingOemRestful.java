/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.oem.restful;

import com.adpf.oem.entity.AdvertisingOem;
import com.adpf.oem.service.AdvertisingOemService;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/adpf/oem")
public class AdvertisingOemRestful {

	protected static final Logger logger = LogManager.getLogger(AdvertisingOemRestful.class);
    
    @Autowired
    private AdvertisingOemService advertisingOemService;
    
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
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));
				params.put("userInfo",userInfo);
				AdvertisingOem advertisingOem = (AdvertisingOem)BeanUtils.mapToBean(params, AdvertisingOem.class);				
				json = advertisingOemService.getList(pageable, advertisingOem, params);
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
				json = advertisingOemService.add(params, userInfo);
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
				json = advertisingOemService.edit(params, userInfo);
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
				json = advertisingOemService.del(idsList, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 获取角色id信息
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/getRoleId",method=RequestMethod.POST)
	public JSONObject getRole(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try {
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = advertisingOemService.getRoleIdByRoleName(params);

			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","获取角色信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 获取角色名称信息
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/getRoleName",method=RequestMethod.POST)
	public JSONObject getRoleName(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try {
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = advertisingOemService.getRoleNameByRoleid(params);

			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","获取角色信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
}
