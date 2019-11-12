/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.crowd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.adpf.modules.crowd.entity.DmpCrowdOperatingLog;
import com.adpf.modules.crowd.repository.DmpCrowdOperatingLogRepository;
import com.adpf.modules.crowd.service.DmpCrowdOperatingLogService;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

@Service
public class DmpCrowdOperatingLogServiceImpl implements DmpCrowdOperatingLogService {
	
    @Autowired
    private DmpCrowdOperatingLogRepository dmpCrowdOperatingLogRepository;

	@Override
	public JSONObject getList(Pageable page, DmpCrowdOperatingLog dmpCrowdInfoOperatingLog, Map<String, Object> params) {
		List<Map<String, Object>> list = dmpCrowdOperatingLogRepository.getList(dmpCrowdInfoOperatingLog, params, page.getPageNumber(), page.getPageSize());
		int count = dmpCrowdOperatingLogRepository.getListCount(dmpCrowdInfoOperatingLog, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    DmpCrowdOperatingLog dmpCrowdOperatingLog = (DmpCrowdOperatingLog) BeanUtils.mapToBean(params, DmpCrowdOperatingLog.class);
	    dmpCrowdOperatingLog.setCreateDate(new Date());
		dmpCrowdOperatingLogRepository.save(dmpCrowdOperatingLog);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
		DmpCrowdOperatingLog dmpCrowdOperatingLog = (DmpCrowdOperatingLog) BeanUtils.mapToBean(params, DmpCrowdOperatingLog.class);
		DmpCrowdOperatingLog dmpCrowdOperatingLogInDb = dmpCrowdOperatingLogRepository.findOne(dmpCrowdOperatingLog.getLogId());
		if(dmpCrowdOperatingLogInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(dmpCrowdOperatingLog, dmpCrowdOperatingLogInDb);
		dmpCrowdOperatingLogRepository.save(dmpCrowdOperatingLogInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			dmpCrowdOperatingLogRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}


	
}
