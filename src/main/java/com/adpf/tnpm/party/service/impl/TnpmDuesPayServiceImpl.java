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

import com.adpf.tnpm.party.service.TnpmDuesPayService;
import com.adpf.tnpm.party.entity.TnpmDuesPay;
import com.adpf.tnpm.party.repository.TnpmDuesPayRepository;

@Service
public class TnpmDuesPayServiceImpl implements TnpmDuesPayService {
	
    @Autowired
    private TnpmDuesPayRepository tnpmDuesPayRepository;

	@Override
	public JSONObject getList(Pageable page, TnpmDuesPay tnpmDuesPay, Map<String, Object> params) {
		List<Map<String, Object>> list = tnpmDuesPayRepository.getList(tnpmDuesPay, params, page.getPageNumber(), page.getPageSize());
		int count = tnpmDuesPayRepository.getListCount(tnpmDuesPay, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TnpmDuesPay tnpmDuesPay = (TnpmDuesPay) BeanUtils.mapToBean(params, TnpmDuesPay.class);
		tnpmDuesPayRepository.save(tnpmDuesPay);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TnpmDuesPay tnpmDuesPay = (TnpmDuesPay) BeanUtils.mapToBean(params, TnpmDuesPay.class);
		TnpmDuesPay tnpmDuesPayInDb = tnpmDuesPayRepository.findOne(tnpmDuesPay.getId());
		if(tnpmDuesPayInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tnpmDuesPay, tnpmDuesPayInDb);
		tnpmDuesPayRepository.save(tnpmDuesPayInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tnpmDuesPayRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
