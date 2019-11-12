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

import com.adpf.tnpm.party.service.PartyMembersService;
import com.adpf.tnpm.party.entity.PartyMembers;
import com.adpf.tnpm.party.repository.PartyMembersRepository;

@Service
public class PartyMembersServiceImpl implements PartyMembersService {
	
    @Autowired
    private PartyMembersRepository tnpmJoinPartyPeopleInfoRepository;

	@Override
	public JSONObject getList(Pageable page, PartyMembers tnpmJoinPartyPeopleInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = tnpmJoinPartyPeopleInfoRepository.getList(tnpmJoinPartyPeopleInfo, params, page.getPageNumber(), page.getPageSize());
		int count = tnpmJoinPartyPeopleInfoRepository.getListCount(tnpmJoinPartyPeopleInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    PartyMembers tnpmJoinPartyPeopleInfo = (PartyMembers) BeanUtils.mapToBean(params, PartyMembers.class);
		tnpmJoinPartyPeopleInfoRepository.save(tnpmJoinPartyPeopleInfo);
		return RestfulRetUtils.getRetSuccess(tnpmJoinPartyPeopleInfoRepository.save(tnpmJoinPartyPeopleInfo));
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        PartyMembers tnpmJoinPartyPeopleInfo = (PartyMembers) BeanUtils.mapToBean(params, PartyMembers.class);
		PartyMembers tnpmJoinPartyPeopleInfoInDb = tnpmJoinPartyPeopleInfoRepository.findOne(tnpmJoinPartyPeopleInfo.getPeopleId());
		if(tnpmJoinPartyPeopleInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tnpmJoinPartyPeopleInfo, tnpmJoinPartyPeopleInfoInDb);
		tnpmJoinPartyPeopleInfoRepository.save(tnpmJoinPartyPeopleInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			long peopleId=Long.parseLong(ids[i]);
			tnpmJoinPartyPeopleInfoRepository.delEntity(peopleId);
		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject updateImg(Map<String, Object> params, UserInfo userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getDetails(Map<String, Object> params, UserInfo userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
