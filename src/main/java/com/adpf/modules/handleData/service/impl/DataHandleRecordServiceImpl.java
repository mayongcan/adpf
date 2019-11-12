/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.handleData.service.impl;

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

import com.adpf.modules.handleData.service.DataHandleRecordService;
import com.adpf.modules.handleData.entity.DataHandleRecord;
import com.adpf.modules.handleData.repository.DataHandleRecordRepository;

@Service
public class DataHandleRecordServiceImpl implements DataHandleRecordService {
	
    @Autowired
    private DataHandleRecordRepository dataHandleRecordRepository;

	@Override
	public JSONObject getList(Pageable page, DataHandleRecord dataHandleRecord, Map<String, Object> params) {
		List<Map<String, Object>> list = dataHandleRecordRepository.getList(dataHandleRecord, params, page.getPageNumber(), page.getPageSize());
		int count = dataHandleRecordRepository.getListCount(dataHandleRecord, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    DataHandleRecord dataHandleRecord = (DataHandleRecord) BeanUtils.mapToBean(params, DataHandleRecord.class);
		dataHandleRecordRepository.save(dataHandleRecord);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        DataHandleRecord dataHandleRecord = (DataHandleRecord) BeanUtils.mapToBean(params, DataHandleRecord.class);
		DataHandleRecord dataHandleRecordInDb = dataHandleRecordRepository.findOne(dataHandleRecord.getDataId());
		if(dataHandleRecordInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(dataHandleRecord, dataHandleRecordInDb);
		dataHandleRecordRepository.save(dataHandleRecordInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			dataHandleRecordRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
