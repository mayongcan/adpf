/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.dataservice.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.dataservice.service.DataServiceService;
import com.adpf.modules.dataservice.entity.DataService;
import com.adpf.modules.dataservice.repository.DataServiceRepository;

@Service
public class DataServiceServiceImpl implements DataServiceService {
	
    @Autowired
    private DataServiceRepository dataServiceRepository;

	@Override
	public JSONObject getList(Pageable page, DataService dataService, Map<String, Object> params) {
		List<Map<String, Object>> list = dataServiceRepository.getList(dataService, params, page.getPageNumber(), page.getPageSize());
		int count = dataServiceRepository.getListCount(dataService, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    DataService dataService = (DataService) BeanUtils.mapToBean(params, DataService.class);
		dataServiceRepository.save(dataService);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        DataService dataService = (DataService) BeanUtils.mapToBean(params, DataService.class);
		DataService dataServiceInDb = dataServiceRepository.findOne(dataService.getId());
		if(dataServiceInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(dataService, dataServiceInDb);
		dataServiceRepository.save(dataServiceInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			dataServiceRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
