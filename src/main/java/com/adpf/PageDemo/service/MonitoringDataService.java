/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.PageDemo.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.adpf.PageDemo.entity.monitoringData;
import com.adpf.PageDemo.entity.monitoringStatistics;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface MonitoringDataService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param monitoringData
	 * @return
	 */
	public JSONObject getList(Pageable page, monitoringData monitoringData, Map<String, Object> params);
	//获取查询电视剧
	public JSONObject getSelevtTv(String condition,String selectip,String limit);
	//获取查询条件
	public JSONObject getQueryCondition(String condition);
	//获取查询条件
	public JSONObject getQueryMediaName();
	//返回数据
	public JSONObject getStatisticalData(monitoringStatistics monitoringStatistics );
	//返回数据
	public JSONObject statisticalRanKing(monitoringStatistics monitoringStatistics ,String limit);
	//返回数据
	public JSONObject statisticalRanKingUV(monitoringStatistics monitoringStatistics ,String limit );
	//返回数据 饼图
	public JSONObject getStatisticalPie(monitoringStatistics monitoringStatistics );
	//返回数据 饼图省分类
	public JSONObject getStatisticalPiePro(monitoringStatistics monitoringStatistics );
	/**
	 * 新增
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject add(Map<String, Object> params, UserInfo userInfo);
	
	/**
	 * 编辑
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo);
	
	/**
	 * 删除
	 * @param idsList
	 * @param userInfo
	 * @return
	 */
	public JSONObject del(String idsList, UserInfo userInfo);
	

}
