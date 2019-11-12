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

import com.adpf.tracking.service.TAppRegisterService;
import com.adpf.tracking.entity.TAppRegister;
import com.adpf.tracking.repository.TAppRegisterRepository;

@Service
public class TAppRegisterServiceImpl implements TAppRegisterService {
	
    @Autowired
    private TAppRegisterRepository tAppRegisterRepository;

	@Override
	public JSONObject getList(Pageable page, TAppRegister tAppRegister, Map<String, Object> params) {
		List<Map<String, Object>> list = tAppRegisterRepository.getList(tAppRegister, params, page.getPageNumber(), page.getPageSize());
		int count = tAppRegisterRepository.getListCount(tAppRegister, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TAppRegister tAppRegister = (TAppRegister) BeanUtils.mapToBean(params, TAppRegister.class);
		tAppRegisterRepository.save(tAppRegister);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TAppRegister tAppRegister = (TAppRegister) BeanUtils.mapToBean(params, TAppRegister.class);
		TAppRegister tAppRegisterInDb = tAppRegisterRepository.findOne(tAppRegister.getId());
		if(tAppRegisterInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tAppRegister, tAppRegisterInDb);
		tAppRegisterRepository.save(tAppRegisterInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tAppRegisterRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
