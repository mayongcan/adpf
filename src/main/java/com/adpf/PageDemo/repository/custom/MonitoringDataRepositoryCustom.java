/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.PageDemo.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;

import com.adpf.PageDemo.entity.monitoringData;
import com.adpf.PageDemo.entity.monitoringStatistics;


/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface MonitoringDataRepositoryCustom {

	/**
	 * 获取MonitoringData列表
	 * @param MonitoringData
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(monitoringData monitoringData, Map<String, Object> params, int pageIndex, int pageSize);
	//获取查询条件
	public List<Map<String, Object>> getQueryCondition(String condition,String selectip,String limit);
	//获取查询条件
	public List<Map<String, Object>> getQueryMediaName();
	//获取查询数据
	public List<Map<String, Object>> getStatisticalData(monitoringStatistics monitoringStatistics );
	//获取查询数据
	public List<Map<String, Object>> statisticalRanKing(monitoringStatistics monitoringStatistics ,String limit );
	//获取查询数据
	public List<Map<String, Object>> statisticalRanKingUV(monitoringStatistics monitoringStatistics  ,String limit);
	//获取查询数据 饼图
	public List<Map<String, Object>> getStatisticalPie(monitoringStatistics monitoringStatistics );
	//获取查询数据饼图 省分类
	public List<Map<String, Object>> getStatisticalPiePro(monitoringStatistics monitoringStatistics );
	
	/**
	 * 获取MonitoringData列表总数
	 * @param MonitoringData
	 * @param params
	 * @return
	 */
	public int getListCount(monitoringData monitoringData, Map<String, Object> params);
}