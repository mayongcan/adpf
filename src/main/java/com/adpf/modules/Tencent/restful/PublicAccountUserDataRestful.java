/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.Tencent.restful;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.adpf.modules.Tencent.entity.PublicAccountUserData;
import com.adpf.modules.Tencent.service.PublicAccountUserDataService;
import com.adpf.modules.orient.entity.DirectionalInformation;
import com.adpf.modules.orient.entity.District;

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/adpf/release/ThePublic")
public class PublicAccountUserDataRestful {

	protected static final Logger logger = LogManager.getLogger(PublicAccountUserDataRestful.class);
    
    @Autowired
    private PublicAccountUserDataService publicAccountUserDataService;
    
	/**
	 * 用于记录打开日志
	 * @param request
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public JSONObject index(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}
	//返回地域信息
	@RequestMapping(value="/getTerritory",method=RequestMethod.POST)
	public JSONObject getTerritory(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		JSONObject json = new JSONObject();
		try{
				District district = (District)BeanUtils.mapToBean(params, District.class);				
				json = publicAccountUserDataService.getTerritory(district, params);
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		/*System.out.println("DDDDDDDDDDDDDDDDDDDDDD"+json);
		System.out.println(params);*/
		return json;
	}

//返回资料
@RequestMapping(value="/getListDatum",method=RequestMethod.POST)
public JSONObject getListDatum(HttpServletRequest request, @RequestBody Map<String, Object> params) {
	JSONObject json = new JSONObject();
	try{
		
			DirectionalInformation directionalInformation = (DirectionalInformation)BeanUtils.mapToBean(params, DirectionalInformation.class);				
			json = publicAccountUserDataService.getListDatum(directionalInformation, params);

	}catch(Exception e){
		json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
		logger.error(e.getMessage(), e);
	}
	/*System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHH"+json);
	System.out.println(params);*/
	return json;
}
	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getList",method=RequestMethod.GET)
	public JSONObject getList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
//			UserInfo userInfo = SessionUtils.getUserInfo();
//			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
//			else {
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
				PublicAccountUserData publicAccountUserData = (PublicAccountUserData)BeanUtils.mapToBean(params, PublicAccountUserData.class);				
				json = publicAccountUserDataService.getList(pageable, publicAccountUserData, params);
//			}
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
			json = publicAccountUserDataService.add(params);
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
				json = publicAccountUserDataService.edit(params, userInfo);
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
				json = publicAccountUserDataService.del(idsList, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	
	@RequestMapping(value="/findone",method=RequestMethod.GET)
	public JSONObject findone(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try {
			/*UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();*/
			/*else {*/
				json =publicAccountUserDataService.findone(params);
			
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	@RequestMapping(value="/findByID",method=RequestMethod.GET)
	public JSONObject findByID(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json=new JSONObject();
		try {
			PublicAccountUserData publicAccountUserData = (PublicAccountUserData)BeanUtils.mapToBean(params, PublicAccountUserData.class);				
			json = publicAccountUserDataService.findByID( publicAccountUserData, params);
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		
		return json;
		
	}
	
	
	
	
	
	
}
