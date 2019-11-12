/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.tagLibrary.restful;

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
import com.gimplatform.core.utils.RedisUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.adpf.modules.gdtm.service.hbase.util.ReadExcelUtli;
import com.adpf.modules.gdtm.tagLibrary.entity.AdpfTagLibrary;
import com.adpf.modules.gdtm.tagLibrary.repository.AdpfTagLibraryRepository;
import com.adpf.modules.gdtm.tagLibrary.service.AdpfTagLibraryService;
import com.adpf.modules.gdtm.tagLibrary.utils.ReadExcel;

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/adpf/tag")
public class AdpfTagLibraryRestful {

	protected static final Logger logger = LogManager.getLogger(AdpfTagLibraryRestful.class);
    
    @Autowired
    private AdpfTagLibraryService adpfTagLibraryService;
    
    @Autowired
    private ReadExcel readExcel;
    
    @Autowired
    private AdpfTagLibraryRepository adpfTagLibraryRepository;
    
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
				AdpfTagLibrary adpfTagLibrary = (AdpfTagLibrary)BeanUtils.mapToBean(params, AdpfTagLibrary.class);				
				json = adpfTagLibraryService.getList(pageable, adpfTagLibrary, params);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 获取未绑定列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getUnboundList",method=RequestMethod.GET)
	public JSONObject getUnboundList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
				AdpfTagLibrary adpfTagLibrary = (AdpfTagLibrary)BeanUtils.mapToBean(params, AdpfTagLibrary.class);				
				json = adpfTagLibraryService.getUnboundList(pageable, adpfTagLibrary, params);
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
				json = adpfTagLibraryService.add(params, userInfo);
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
				json = adpfTagLibraryService.edit(params, userInfo);
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
				json = adpfTagLibraryService.del(idsList, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 文本添加标签
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/readExcel",method=RequestMethod.GET)
	public JSONObject readExcel(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				String filePath = (String) params.get("filePath");
				System.err.println(filePath);
				readExcel.readExcelWithTitle(filePath);
//				readExcelUtli.readExcelWithTitle("E:\\imei\\data.xlsx");
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51005","添加失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 文本添加标签
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/getTagUrl",method=RequestMethod.GET)
	public JSONObject getTagUrl(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
				AdpfTagLibrary adpfTagLibrary = (AdpfTagLibrary)BeanUtils.mapToBean(params, AdpfTagLibrary.class);				
				json = adpfTagLibraryService.getTagUrl(pageable,adpfTagLibrary, params);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51005","查询失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	

	@RequestMapping(value="/getTagUrl1",method=RequestMethod.GET)
	public JSONObject test11(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				RedisUtils ru = new RedisUtils();
				List<AdpfTagLibrary> list= adpfTagLibraryRepository.findAll();
				for(int i = 0;i<list.size();i++) {
//		        	  System.out.println(list.get(i).getTagId());
//		        	  System.out.println(list.get(i).getLabel());
//		        	  ru.set(list.get(i).getTagId(), list.get(i).getLabel(), 0);
		        	  System.out.println(ru.get(list.get(i).getTagId()));
		        }
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51005","查询失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	
}
