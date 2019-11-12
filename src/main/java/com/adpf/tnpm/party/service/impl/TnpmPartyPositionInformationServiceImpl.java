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

import com.adpf.tnpm.party.service.TnpmPartyPositionInformationService;
import com.adpf.tnpm.party.entity.TnpmPartyPositionInformation;
import com.adpf.tnpm.party.repository.TnpmPartyPositionInformationRepository;

@Service
public class TnpmPartyPositionInformationServiceImpl implements TnpmPartyPositionInformationService {
	
    @Autowired
    private TnpmPartyPositionInformationRepository tnpmPartyPositionInformationRepository;

	@Override
	public JSONObject getList(Pageable page, TnpmPartyPositionInformation tnpmPartyPositionInformation, Map<String, Object> params) {
		List<Map<String, Object>> list = tnpmPartyPositionInformationRepository.getList(tnpmPartyPositionInformation, params, page.getPageNumber(), page.getPageSize());
		int count = tnpmPartyPositionInformationRepository.getListCount(tnpmPartyPositionInformation, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TnpmPartyPositionInformation tnpmPartyPositionInformation = (TnpmPartyPositionInformation) BeanUtils.mapToBean(params, TnpmPartyPositionInformation.class);
		tnpmPartyPositionInformationRepository.save(tnpmPartyPositionInformation);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TnpmPartyPositionInformation tnpmPartyPositionInformation = (TnpmPartyPositionInformation) BeanUtils.mapToBean(params, TnpmPartyPositionInformation.class);
		TnpmPartyPositionInformation tnpmPartyPositionInformationInDb = tnpmPartyPositionInformationRepository.findOne(tnpmPartyPositionInformation.getId());
		if(tnpmPartyPositionInformationInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tnpmPartyPositionInformation, tnpmPartyPositionInformationInDb);
		tnpmPartyPositionInformationRepository.save(tnpmPartyPositionInformationInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tnpmPartyPositionInformationRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
