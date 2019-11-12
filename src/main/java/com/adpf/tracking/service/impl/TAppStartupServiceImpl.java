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

import com.adpf.tracking.service.TAppStartupService;
import com.adpf.tracking.entity.TAppStartup;
import com.adpf.tracking.repository.TAppStartupRepository;

@Service
public class TAppStartupServiceImpl implements TAppStartupService {
	
    @Autowired
    private TAppStartupRepository tAppStartupRepository;

	@Override
	public JSONObject getList(Pageable page, TAppStartup tAppStartup, Map<String, Object> params) {
		List<Map<String, Object>> list = tAppStartupRepository.getList(tAppStartup, params, page.getPageNumber(), page.getPageSize());
		int count = tAppStartupRepository.getListCount(tAppStartup, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TAppStartup tAppStartup = (TAppStartup) BeanUtils.mapToBean(params, TAppStartup.class);
		tAppStartupRepository.save(tAppStartup);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TAppStartup tAppStartup = (TAppStartup) BeanUtils.mapToBean(params, TAppStartup.class);
		TAppStartup tAppStartupInDb = tAppStartupRepository.findOne(tAppStartup.getId());
		if(tAppStartupInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tAppStartup, tAppStartupInDb);
		tAppStartupRepository.save(tAppStartupInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tAppStartupRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
