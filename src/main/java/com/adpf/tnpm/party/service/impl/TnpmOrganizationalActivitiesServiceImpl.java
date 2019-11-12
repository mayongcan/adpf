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

import com.adpf.tnpm.party.service.TnpmOrganizationalActivitiesService;
import com.adpf.tnpm.party.entity.TnpmOrganizationalActivities;
import com.adpf.tnpm.party.repository.TnpmOrganizationalActivitiesRepository;

@Service
public class TnpmOrganizationalActivitiesServiceImpl implements TnpmOrganizationalActivitiesService {
	
    @Autowired
    private TnpmOrganizationalActivitiesRepository tnpmOrganizationalActivitiesRepository;

	@Override
	public JSONObject getList(Pageable page, TnpmOrganizationalActivities tnpmOrganizationalActivities, Map<String, Object> params) {
		List<Map<String, Object>> list = tnpmOrganizationalActivitiesRepository.getList(tnpmOrganizationalActivities, params, page.getPageNumber(), page.getPageSize());
		int count = tnpmOrganizationalActivitiesRepository.getListCount(tnpmOrganizationalActivities, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TnpmOrganizationalActivities tnpmOrganizationalActivities = (TnpmOrganizationalActivities) BeanUtils.mapToBean(params, TnpmOrganizationalActivities.class);
		tnpmOrganizationalActivitiesRepository.save(tnpmOrganizationalActivities);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TnpmOrganizationalActivities tnpmOrganizationalActivities = (TnpmOrganizationalActivities) BeanUtils.mapToBean(params, TnpmOrganizationalActivities.class);
		TnpmOrganizationalActivities tnpmOrganizationalActivitiesInDb = tnpmOrganizationalActivitiesRepository.findOne(tnpmOrganizationalActivities.getId());
		if(tnpmOrganizationalActivitiesInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tnpmOrganizationalActivities, tnpmOrganizationalActivitiesInDb);
		tnpmOrganizationalActivitiesRepository.save(tnpmOrganizationalActivitiesInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tnpmOrganizationalActivitiesRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
