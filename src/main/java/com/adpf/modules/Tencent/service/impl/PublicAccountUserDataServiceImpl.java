/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.Tencent.service.impl;

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

import com.adpf.modules.Tencent.service.PublicAccountUserDataService;
import com.adpf.modules.orient.entity.DirectionalInformation;
import com.adpf.modules.orient.entity.District;
import com.adpf.modules.Tencent.entity.PublicAccountUserData;
import com.adpf.modules.Tencent.repository.PublicAccountUserDataRepository;

@Service
public class PublicAccountUserDataServiceImpl implements PublicAccountUserDataService {
	
    @Autowired
    private PublicAccountUserDataRepository publicAccountUserDataRepository;

	@Override
	public JSONObject getList(Pageable page, PublicAccountUserData publicAccountUserData, Map<String, Object> params) {
		List<Map<String, Object>> list = publicAccountUserDataRepository.getList(publicAccountUserData, params, page.getPageNumber(), page.getPageSize());
		int count = publicAccountUserDataRepository.getListCount(publicAccountUserData, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params) {
	    PublicAccountUserData publicAccountUserData = (PublicAccountUserData) BeanUtils.mapToBean(params, PublicAccountUserData.class);
		publicAccountUserDataRepository.save(publicAccountUserData);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        PublicAccountUserData publicAccountUserData = (PublicAccountUserData) BeanUtils.mapToBean(params, PublicAccountUserData.class);
		PublicAccountUserData publicAccountUserDataInDb = publicAccountUserDataRepository.findOne(publicAccountUserData.getId());
		if(publicAccountUserDataInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(publicAccountUserData, publicAccountUserDataInDb);
		publicAccountUserDataRepository.save(publicAccountUserDataInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			publicAccountUserDataRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject getListDatum(DirectionalInformation directionalInformation, Map<String, Object> params) {
		List<Map<String, Object>> list = publicAccountUserDataRepository.getListDatum(directionalInformation, params);
		return RestfulRetUtils.getRetSuccess(list);
	}

	@Override
	public JSONObject getTerritory(District district, Map<String, Object> params) {
		List<Map<String, Object>> list = publicAccountUserDataRepository.getTerritory(district, params);
		return RestfulRetUtils.getRetSuccess(list);
	}
	

	@Override
	public JSONObject findone(Map<String, Object> params) {
		// TODO Auto-generated method stub
		//return null;
		
		PublicAccountUserData publicAccountUserData = (PublicAccountUserData) BeanUtils.mapToBean(params, PublicAccountUserData.class);
		PublicAccountUserData publicAccountUserDataInDb=publicAccountUserDataRepository.findOne(publicAccountUserData.getId());
		
		if(publicAccountUserDataInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//BeanUtils.mergeBean(publicAccountUserData, publicAccountUserDataInDb);
		return RestfulRetUtils.getRetSuccess(publicAccountUserDataInDb);
	}

	@Override
	public JSONObject findByID(PublicAccountUserData publicAccountUserData, Map<String, Object> params) {
		// TODO Auto-generated method stub
		//return null;
		List<Map<String, Object>>list=publicAccountUserDataRepository.getListByID(publicAccountUserData, params);
		return RestfulRetUtils.getRetSuccess(list);
		
	}
}
