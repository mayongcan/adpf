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

import com.adpf.tracking.service.TAppLoginService;
import com.adpf.tracking.entity.TAppLogin;
import com.adpf.tracking.repository.TAppLoginRepository;

@Service
public class TAppLoginServiceImpl implements TAppLoginService {
	
    @Autowired
    private TAppLoginRepository tAppLoginRepository;

	@Override
	public JSONObject getList(Pageable page, TAppLogin tAppLogin, Map<String, Object> params) {
		List<Map<String, Object>> list = tAppLoginRepository.getList(tAppLogin, params, page.getPageNumber(), page.getPageSize());
		int count = tAppLoginRepository.getListCount(tAppLogin, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TAppLogin tAppLogin = (TAppLogin) BeanUtils.mapToBean(params, TAppLogin.class);
		tAppLoginRepository.save(tAppLogin);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TAppLogin tAppLogin = (TAppLogin) BeanUtils.mapToBean(params, TAppLogin.class);
		TAppLogin tAppLoginInDb = tAppLoginRepository.findOne(tAppLogin.getId());
		if(tAppLoginInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tAppLogin, tAppLoginInDb);
		tAppLoginRepository.save(tAppLoginInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tAppLoginRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
