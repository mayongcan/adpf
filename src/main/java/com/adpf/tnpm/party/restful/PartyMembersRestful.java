/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.restful;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.adpf.tnpm.party.entity.PartyMembers;
import com.adpf.tnpm.party.service.PartyMembersService;

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/adpf/tnpm/partyMember")
public class PartyMembersRestful {

	protected static final Logger logger = LogManager.getLogger(PartyMembersRestful.class);
    
    @Autowired
    private PartyMembersService tnpmJoinPartyPeopleInfoService;
    
//    @Value("${resourceServer.uploadFilePath}")
//    private String uploadFilePath;
    
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
				PartyMembers tnpmJoinPartyPeopleInfo = (PartyMembers)BeanUtils.mapToBean(params, PartyMembers.class);				
				json = tnpmJoinPartyPeopleInfoService.getList(pageable, tnpmJoinPartyPeopleInfo, params);
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
				json = tnpmJoinPartyPeopleInfoService.add(params, userInfo);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51002","新增信息失败");
			logger.error(e.getMessage(), e);
		}
		//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!json"+json);
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
				json = tnpmJoinPartyPeopleInfoService.edit(params, userInfo);
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
				json = tnpmJoinPartyPeopleInfoService.del(idsList, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}

	
	/**
	 * 编辑图片
	 * @param request
	 * @param params
	 * @return
	 */
//	@RequestMapping(value="/updateImg",method=RequestMethod.POST)
//	public JSONObject updateImg(HttpServletRequest request, @RequestBody Map<String, Object> params){
//		JSONObject json = new JSONObject();
//		try{
//			UserInfo userInfo = SessionUtils.getUserInfo();
//			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
//			else {
//				params.put("uploadFilePath", uploadFilePath);
//				json = tnpmJoinPartyPeopleInfoService.updateImg(params, userInfo);
//			}
//		}catch(Exception e){
//			json = RestfulRetUtils.getErrorMsg("51003","编辑信息失败");
//			logger.error(e.getMessage(), e);
//		}
//		return json;
//	}
	
	
	/*
	 * 根据id获取相关信息
	 */
	@RequestMapping(value="/getDetails",method=RequestMethod.POST)
	public JSONObject getDetails(HttpServletRequest request ,@RequestBody Map<String,Object>params) {
		JSONObject json =new JSONObject();
		try {
			UserInfo userInfo =SessionUtils.getUserInfo();
			if(userInfo == null) json =RestfulRetUtils.getErrorNoUser();
			else {
				json = tnpmJoinPartyPeopleInfoService.getDetails(params, userInfo);
			}
		}catch(Exception e) {
			json = RestfulRetUtils.getErrorMsg("51003","编辑信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
}
