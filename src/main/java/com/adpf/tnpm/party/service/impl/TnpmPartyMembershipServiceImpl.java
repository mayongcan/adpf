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

import com.adpf.tnpm.party.service.TnpmPartyMembershipService;
import com.adpf.tnpm.party.entity.TnpmPartyMembership;
import com.adpf.tnpm.party.repository.TnpmPartyMembershipRepository;

@Service
public class TnpmPartyMembershipServiceImpl implements TnpmPartyMembershipService {
	
    @Autowired
    private TnpmPartyMembershipRepository tnpmPartyMembershipRepository;

	@Override
	public JSONObject getList(Pageable page, TnpmPartyMembership tnpmPartyMembership, Map<String, Object> params) {
		List<Map<String, Object>> list = tnpmPartyMembershipRepository.getList(tnpmPartyMembership, params, page.getPageNumber(), page.getPageSize());
		int count = tnpmPartyMembershipRepository.getListCount(tnpmPartyMembership, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TnpmPartyMembership tnpmPartyMembership = (TnpmPartyMembership) BeanUtils.mapToBean(params, TnpmPartyMembership.class);
		tnpmPartyMembershipRepository.save(tnpmPartyMembership);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TnpmPartyMembership tnpmPartyMembership = (TnpmPartyMembership) BeanUtils.mapToBean(params, TnpmPartyMembership.class);
		TnpmPartyMembership tnpmPartyMembershipInDb = tnpmPartyMembershipRepository.findOne(tnpmPartyMembership.getId());
		if(tnpmPartyMembershipInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tnpmPartyMembership, tnpmPartyMembershipInDb);
		tnpmPartyMembershipRepository.save(tnpmPartyMembershipInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tnpmPartyMembershipRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
