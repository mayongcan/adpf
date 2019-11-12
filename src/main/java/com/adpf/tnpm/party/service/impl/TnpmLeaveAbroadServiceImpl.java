/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.service.impl;

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

import com.adpf.tnpm.party.service.TnpmLeaveAbroadService;
import com.adpf.tnpm.party.entity.TnpmLeaveAbroad;
import com.adpf.tnpm.party.repository.TnpmLeaveAbroadRepository;

@Service
public class TnpmLeaveAbroadServiceImpl implements TnpmLeaveAbroadService {
	
    @Autowired
    private TnpmLeaveAbroadRepository tnpmLeaveAbroadRepository;

	@Override
	public JSONObject getList(Pageable page, TnpmLeaveAbroad tnpmLeaveAbroad, Map<String, Object> params) {
		List<Map<String, Object>> list = tnpmLeaveAbroadRepository.getList(tnpmLeaveAbroad, params, page.getPageNumber(), page.getPageSize());
		int count = tnpmLeaveAbroadRepository.getListCount(tnpmLeaveAbroad, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TnpmLeaveAbroad tnpmLeaveAbroad = (TnpmLeaveAbroad) BeanUtils.mapToBean(params, TnpmLeaveAbroad.class);
		tnpmLeaveAbroadRepository.save(tnpmLeaveAbroad);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TnpmLeaveAbroad tnpmLeaveAbroad = (TnpmLeaveAbroad) BeanUtils.mapToBean(params, TnpmLeaveAbroad.class);
		TnpmLeaveAbroad tnpmLeaveAbroadInDb = tnpmLeaveAbroadRepository.findOne(tnpmLeaveAbroad.getId());
		if(tnpmLeaveAbroadInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tnpmLeaveAbroad, tnpmLeaveAbroadInDb);
		tnpmLeaveAbroadRepository.save(tnpmLeaveAbroadInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tnpmLeaveAbroadRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
