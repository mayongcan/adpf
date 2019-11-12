/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.advertiser.restful;

import com.adpf.advertiser.entity.Advertisers;
import com.adpf.advertiser.service.AdvertisersService;
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
@RequestMapping("/api/adpf/advertisers")
public class AdvertisersRestful {

	protected static final Logger logger = LogManager.getLogger(AdvertisersRestful.class);
    
    @Autowired
    private AdvertisersService advertisersService;

	/**
	 * 用于记录打开日志
	 * @param request
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public JSONObject index(HttpServletRequest request){
		return RestfulRetUtils.getRetSuccess();}

	/**
	 * 用于记录打开日志
	 * @param request
	 */
	@RequestMapping(value="/index2", method=RequestMethod.GET)
	public JSONObject index2(HttpServletRequest request){
		return RestfulRetUtils.getRetSuccess();}

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
				Advertisers advertisers = (Advertisers)BeanUtils.mapToBean(params, Advertisers.class);	
				//userinfo把userinfo传过来
				json = advertisersService.getList(pageable, advertisers, params,userInfo);
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
				json = advertisersService.add(params, userInfo);
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
				json = advertisersService.edit(params, userInfo);
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
				json = advertisersService.del(idsList, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 充值
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/topUp",method=RequestMethod.POST)
	public JSONObject topUp(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = advertisersService.topUp(params, userInfo);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51003","充值失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 开通坐席
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/dredge",method=RequestMethod.POST)
	public JSONObject dredge(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = advertisersService.dredge(params, userInfo);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51004","开通失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 上发广告
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/issue",method=RequestMethod.POST)
	public JSONObject issue(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = advertisersService.issue(params, userInfo);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51005","上发失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}



	/**
	 * 获取角色id
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
				json = advertisersService.getRoleIdByRoleName(params);

			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","获取角色信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}


	/*
	* 获取企业消耗
	* */
	@RequestMapping(value="/getStatisticsList",method=RequestMethod.GET)
	public JSONObject getStatisticsList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));
				params.put("userInfo",userInfo);
				params.put("tagentId",null);
				Advertisers advertisers = (Advertisers)BeanUtils.mapToBean(params, Advertisers.class);
				//userinfo把userinfo传过来
				json = advertisersService.getStatisticsList(params,pageable);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	/*
	* 获取企业消耗
	* */
	@RequestMapping(value="/getStatisticsListByTagentId",method=RequestMethod.GET)
	public JSONObject getStatisticsListByTagentId(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));
				params.put("userInfo",userInfo);
				Advertisers advertisers = (Advertisers)BeanUtils.mapToBean(params, Advertisers.class);
				//userinfo把userinfo传过来
				json = advertisersService.getStatisticsList(params,pageable);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}

}
