/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.PageDemo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.adpf.PageDemo.entity.monitoringData;
import com.adpf.PageDemo.entity.monitoringStatistics;
import com.adpf.PageDemo.repository.MonitoringDataRepository;
import com.adpf.PageDemo.service.MonitoringDataService;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

@Service
public class MonitoringDataUserServiceImpl implements MonitoringDataService {
	
    @Autowired
    private MonitoringDataRepository monitoringDataRepository;

	@Override
	public JSONObject getList(Pageable page, monitoringData monitoringData, Map<String, Object> params) {
		List<Map<String, Object>> list = monitoringDataRepository.getList(monitoringData, params, page.getPageNumber(), page.getPageSize());
		int count = monitoringDataRepository.getListCount(monitoringData, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}
	@Override
	public JSONObject getSelevtTv(String condition,String selectip,String limit) {
		List<Map<String, Object>> list = monitoringDataRepository.getQueryCondition(condition, selectip,limit);
		return RestfulRetUtils.getRetSuccess(list);
	}
	@Override
	public JSONObject getQueryCondition(String condition) {
		List<Map<String, Object>> list = monitoringDataRepository.getQueryCondition(condition,"false","0,24");
		List<Map<String, Object>> list1 = monitoringDataRepository.getQueryCondition("false","false","0,24");
		for(Map<String, Object> data : list1){
			list.add(data);
		}
		return RestfulRetUtils.getRetSuccess(list);
	}
	@Override
	public JSONObject getQueryMediaName() {
		List<Map<String, Object>> list = monitoringDataRepository.getQueryMediaName();
		return RestfulRetUtils.getRetSuccess(list);
	}
	@Override
	public JSONObject statisticalRanKing(monitoringStatistics monitoringStatistics, String limit ) {
		List<Map<String, Object>> list = monitoringDataRepository.statisticalRanKing( monitoringStatistics ,limit);
		return RestfulRetUtils.getRetSuccess(list);
	}
	@Override
	public JSONObject statisticalRanKingUV(monitoringStatistics monitoringStatistics ,String limit ) {
		List<Map<String, Object>> list = monitoringDataRepository.statisticalRanKingUV( monitoringStatistics,limit );
		return RestfulRetUtils.getRetSuccess(list);
	}
	@Override
	public JSONObject getStatisticalPie(monitoringStatistics monitoringStatistics ) {
		List<Map<String, Object>> list = monitoringDataRepository.getStatisticalPie(monitoringStatistics);
		return RestfulRetUtils.getRetSuccess(list);
	}
	@Override
	public JSONObject getStatisticalPiePro(monitoringStatistics monitoringStatistics ) {
		List<Map<String, Object>> list = monitoringDataRepository.getStatisticalPiePro(monitoringStatistics);
		return RestfulRetUtils.getRetSuccess(list);
	}
	@Override
	public JSONObject getStatisticalData(monitoringStatistics monitoringStatistics ) {
		List<Map<String, Object>> list = monitoringDataRepository.getStatisticalData(monitoringStatistics);
		return RestfulRetUtils.getRetSuccess(list);
	}
	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    monitoringData monitoringData = (monitoringData) BeanUtils.mapToBean(params, monitoringData.class);
		monitoringDataRepository.save(monitoringData);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        monitoringData monitoringData = (monitoringData) BeanUtils.mapToBean(params, monitoringData.class);
		monitoringData monitoringDataInDb = monitoringDataRepository.findOne(monitoringData.getId());
		if(monitoringDataInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(monitoringData, monitoringDataInDb);
		monitoringDataRepository.save(monitoringDataInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			monitoringDataRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
