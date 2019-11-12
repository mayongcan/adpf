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

import com.adpf.tnpm.party.service.TnpmPartyRepresentativeService;
import com.adpf.tnpm.party.entity.TnpmPartyRepresentative;
import com.adpf.tnpm.party.repository.TnpmPartyRepresentativeRepository;

@Service
public class TnpmPartyRepresentativeServiceImpl implements TnpmPartyRepresentativeService {
	
    @Autowired
    private TnpmPartyRepresentativeRepository tnpmPartyRepresentativeRepository;

	@Override
	public JSONObject getList(Pageable page, TnpmPartyRepresentative tnpmPartyRepresentative, Map<String, Object> params) {
		List<Map<String, Object>> list = tnpmPartyRepresentativeRepository.getList(tnpmPartyRepresentative, params, page.getPageNumber(), page.getPageSize());
		int count = tnpmPartyRepresentativeRepository.getListCount(tnpmPartyRepresentative, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TnpmPartyRepresentative tnpmPartyRepresentative = (TnpmPartyRepresentative) BeanUtils.mapToBean(params, TnpmPartyRepresentative.class);
		tnpmPartyRepresentativeRepository.save(tnpmPartyRepresentative);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TnpmPartyRepresentative tnpmPartyRepresentative = (TnpmPartyRepresentative) BeanUtils.mapToBean(params, TnpmPartyRepresentative.class);
		TnpmPartyRepresentative tnpmPartyRepresentativeInDb = tnpmPartyRepresentativeRepository.findOne(tnpmPartyRepresentative.getId());
		if(tnpmPartyRepresentativeInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tnpmPartyRepresentative, tnpmPartyRepresentativeInDb);
		tnpmPartyRepresentativeRepository.save(tnpmPartyRepresentativeInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tnpmPartyRepresentativeRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
