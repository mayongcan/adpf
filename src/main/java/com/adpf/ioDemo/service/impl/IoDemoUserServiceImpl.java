/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.ioDemo.service.impl;

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

import com.adpf.ioDemo.service.IoDemoUserService;
import com.adpf.ioDemo.entity.IoDemoUser;
import com.adpf.ioDemo.repository.IoDemoUserRepository;

@Service
public class IoDemoUserServiceImpl implements IoDemoUserService {
	
    @Autowired
    private IoDemoUserRepository ioDemoUserRepository;

	@Override
	public JSONObject getList(Pageable page, IoDemoUser ioDemoUser, Map<String, Object> params) {
		List<Map<String, Object>> list = ioDemoUserRepository.getList(ioDemoUser, params, page.getPageNumber(), page.getPageSize());
		int count = ioDemoUserRepository.getListCount(ioDemoUser, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    IoDemoUser ioDemoUser = (IoDemoUser) BeanUtils.mapToBean(params, IoDemoUser.class);
		ioDemoUserRepository.save(ioDemoUser);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        IoDemoUser ioDemoUser = (IoDemoUser) BeanUtils.mapToBean(params, IoDemoUser.class);
		IoDemoUser ioDemoUserInDb = ioDemoUserRepository.findOne(ioDemoUser.getId());
		if(ioDemoUserInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(ioDemoUser, ioDemoUserInDb);
		ioDemoUserRepository.save(ioDemoUserInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			ioDemoUserRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
