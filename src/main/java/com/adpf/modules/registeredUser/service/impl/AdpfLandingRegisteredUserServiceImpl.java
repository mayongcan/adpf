/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.registeredUser.service.impl;

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

import com.adpf.modules.registeredUser.service.AdpfLandingRegisteredUserService;
import com.adpf.modules.registeredUser.entity.AdpfLandingRegisteredUser;
import com.adpf.modules.registeredUser.repository.AdpfLandingRegisteredUserRepository;

@Service
public class AdpfLandingRegisteredUserServiceImpl implements AdpfLandingRegisteredUserService {
	
    @Autowired
    private AdpfLandingRegisteredUserRepository adpfLandingRegisteredUserRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfLandingRegisteredUser adpfLandingRegisteredUser, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfLandingRegisteredUserRepository.getList(adpfLandingRegisteredUser, params, page.getPageNumber(), page.getPageSize());
		int count = adpfLandingRegisteredUserRepository.getListCount(adpfLandingRegisteredUser, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdpfLandingRegisteredUser adpfLandingRegisteredUser = (AdpfLandingRegisteredUser) BeanUtils.mapToBean(params, AdpfLandingRegisteredUser.class);
		adpfLandingRegisteredUserRepository.save(adpfLandingRegisteredUser);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdpfLandingRegisteredUser adpfLandingRegisteredUser = (AdpfLandingRegisteredUser) BeanUtils.mapToBean(params, AdpfLandingRegisteredUser.class);
		AdpfLandingRegisteredUser adpfLandingRegisteredUserInDb = adpfLandingRegisteredUserRepository.findOne(adpfLandingRegisteredUser.getId());
		if(adpfLandingRegisteredUserInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfLandingRegisteredUser, adpfLandingRegisteredUserInDb);
		adpfLandingRegisteredUserRepository.save(adpfLandingRegisteredUserInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfLandingRegisteredUserRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
