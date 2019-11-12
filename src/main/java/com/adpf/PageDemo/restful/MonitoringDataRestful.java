/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.PageDemo.restful;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adpf.PageDemo.entity.monitoringData;
import com.adpf.PageDemo.entity.monitoringStatistics;
import com.adpf.PageDemo.service.MonitoringDataService;
import com.adpf.modules.orient.entity.District;
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

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/adpf/monitoring")
public class MonitoringDataRestful {

	protected static final Logger logger = LogManager.getLogger(MonitoringDataRestful.class);
    
    @Autowired
    private MonitoringDataService monitoringDataService;
    
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
				monitoringData monitoringData = (monitoringData)BeanUtils.mapToBean(params, monitoringData.class);				
				json = monitoringDataService.getList(pageable, monitoringData, params);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 获取查询条件
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/selevtTv",method=RequestMethod.POST)
	public JSONObject getSelevtTv(HttpServletRequest request, @RequestBody Map<String, String> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				
				json = monitoringDataService.getSelevtTv(params.get("mediaName"),params.get("videoName"),params.get("limit"));
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 获取查询条件
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getQueryCondition",method=RequestMethod.POST)
	public JSONObject getQueryCondition(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = monitoringDataService.getQueryCondition(params.get("mediaName")+"");
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 获取查询条件
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getQueryMediaName",method=RequestMethod.POST)
	public JSONObject getQueryMediaName(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = monitoringDataService.getQueryMediaName();
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 返回查询数据  饼图省分类
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/statisticalPiePro",method=RequestMethod.POST)
	public JSONObject statisticalPiePro(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				//根据开始时间算出结束时间
				Date date=new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String da=params.get("dataDate")+"";
				int day=Integer.parseInt(da.substring( 2, da.length()-1));
				Calendar c = Calendar.getInstance();           
				c.add(Calendar.DATE, - day);           
				Date time = c.getTime();         
				String preDay = sdf.format(time);
				String newday = sdf.format(date);
				monitoringStatistics monitoringStatistics = (monitoringStatistics)BeanUtils.mapToBean(params, monitoringStatistics.class);		
				monitoringStatistics.setDataDate("'"+preDay+"' AND '"+newday+"'");
				json = monitoringDataService.getStatisticalPiePro(monitoringStatistics);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 返回查询数据  饼图
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/statisticalPie",method=RequestMethod.POST)
	public JSONObject statisticalPie(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				//根据开始时间算出结束时间
				Date date=new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String da=params.get("dataDate")+"";
				int day=Integer.parseInt(da.substring( 2, da.length()-1));
				Calendar c = Calendar.getInstance();           
				c.add(Calendar.DATE, - day);           
				Date time = c.getTime();         
				String preDay = sdf.format(time);
				String newday = sdf.format(date);
				monitoringStatistics monitoringStatistics = (monitoringStatistics)BeanUtils.mapToBean(params, monitoringStatistics.class);		
				monitoringStatistics.setDataDate("'"+preDay+"' AND '"+newday+"'");
				json = monitoringDataService.getStatisticalPie(monitoringStatistics);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 返回查询数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/statisticalData",method=RequestMethod.POST)
	public JSONObject statisticalData(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				//根据开始时间算出结束时间
				Date date=new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String da=params.get("dataDate")+"";
				int day=Integer.parseInt(da.substring( 2, da.length()-1));
				Calendar c = Calendar.getInstance();           
				c.add(Calendar.DATE, - day);           
				Date time = c.getTime();         
				String preDay = sdf.format(time);
				String newday = sdf.format(date);
				monitoringStatistics monitoringStatistics = (monitoringStatistics)BeanUtils.mapToBean(params, monitoringStatistics.class);		
				monitoringStatistics.setDataDate("'"+preDay+"' AND '"+newday+"'");
				json = monitoringDataService.getStatisticalData(monitoringStatistics);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 返回查询数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/statisticalRanKingPV",method=RequestMethod.POST)
	public JSONObject statisticalRanKing(HttpServletRequest request, @RequestBody Map<String, String> params){

		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				
				monitoringStatistics monitoringStatistics = (monitoringStatistics)BeanUtils.mapToBean(params, monitoringStatistics.class);		
				String mediaName=monitoringStatistics.getMediaName();
				if(mediaName.equals("请选择")){
					monitoringStatistics.setMediaName("");
				}
				json = monitoringDataService.statisticalRanKing(monitoringStatistics,params.get("limite"));
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}

		return json;
	}
	/**
	 * 返回查询数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/statisticalRanKingUV",method=RequestMethod.POST)
	public JSONObject statisticalRanKingUV(HttpServletRequest request, @RequestBody Map<String, String> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				monitoringStatistics monitoringStatistics = (monitoringStatistics)BeanUtils.mapToBean(params, monitoringStatistics.class);		
				String mediaName=monitoringStatistics.getMediaName();
				if(mediaName.equals("请选择")){
					monitoringStatistics.setMediaName("");
				}
				json = monitoringDataService.statisticalRanKingUV(monitoringStatistics,params.get("limite"));
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
				json = monitoringDataService.add(params, userInfo);
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
				json = monitoringDataService.edit(params, userInfo);
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
				json = monitoringDataService.del(idsList, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
}
