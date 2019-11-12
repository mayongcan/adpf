/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tuiguang.service.impl;

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

import com.adpf.tuiguang.service.UserTuiguangService;
import com.adpf.tuiguang.entity.UserTuiguang;
import com.adpf.tuiguang.repository.UserTuiguangRepository;

@Service
public class UserTuiguangServiceImpl implements UserTuiguangService {
	
    @Autowired
    private UserTuiguangRepository userTuiguangRepository;

	@Override
	public JSONObject getList(Pageable page, UserTuiguang userTuiguang, Map<String, Object> params) {
		List<Map<String, Object>> list = userTuiguangRepository.getList(userTuiguang, params, page.getPageNumber(), page.getPageSize());
		int count = userTuiguangRepository.getListCount(userTuiguang, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    UserTuiguang userTuiguang = (UserTuiguang) BeanUtils.mapToBean(params, UserTuiguang.class);
		userTuiguangRepository.save(userTuiguang);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        UserTuiguang userTuiguang = (UserTuiguang) BeanUtils.mapToBean(params, UserTuiguang.class);
		UserTuiguang userTuiguangInDb = userTuiguangRepository.findOne(userTuiguang.getTuiguangId());
		if(userTuiguangInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(userTuiguang, userTuiguangInDb);
		userTuiguangRepository.save(userTuiguangInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			userTuiguangRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
