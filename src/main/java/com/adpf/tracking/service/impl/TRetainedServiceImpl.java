/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.service.impl;

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

import com.adpf.tracking.service.TRetainedService;
import com.adpf.tracking.entity.TRetained;
import com.adpf.tracking.repository.TRetainedRepository;

@Service
public class TRetainedServiceImpl implements TRetainedService {
	
    @Autowired
    private TRetainedRepository tRetainedRepository;

	@Override
	public JSONObject getList(Pageable page, TRetained tRetained, Map<String, Object> params) {
		List<Map<String, Object>> list = tRetainedRepository.getList(tRetained, params, page.getPageNumber(), page.getPageSize());
		int count = tRetainedRepository.getListCount(tRetained, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TRetained tRetained = (TRetained) BeanUtils.mapToBean(params, TRetained.class);
		tRetainedRepository.save(tRetained);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TRetained tRetained = (TRetained) BeanUtils.mapToBean(params, TRetained.class);
		TRetained tRetainedInDb = tRetainedRepository.findOne(tRetained.getId());
		if(tRetainedInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tRetained, tRetainedInDb);
		tRetainedRepository.save(tRetainedInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tRetainedRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
