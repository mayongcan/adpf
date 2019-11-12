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
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.tnpm.party.service.TnpmFamilyInfoService;
import com.adpf.tnpm.party.entity.TnpmFamilyInfo;
import com.adpf.tnpm.party.repository.TnpmFamilyInfoRepository;

@Service
public class TnpmFamilyInfoServiceImpl implements TnpmFamilyInfoService {
	
    @Autowired
    private TnpmFamilyInfoRepository tnpmFamilyInfoRepository;
    

	@Override
	public JSONObject getList(Pageable page, TnpmFamilyInfo tnpmFamilyInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = tnpmFamilyInfoRepository.getList(tnpmFamilyInfo, params, page.getPageNumber(), page.getPageSize());
		int count = tnpmFamilyInfoRepository.getListCount(tnpmFamilyInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TnpmFamilyInfo tnpmFamilyInfo = (TnpmFamilyInfo) BeanUtils.mapToBean(params, TnpmFamilyInfo.class);
		tnpmFamilyInfoRepository.save(tnpmFamilyInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TnpmFamilyInfo tnpmFamilyInfo = (TnpmFamilyInfo) BeanUtils.mapToBean(params, TnpmFamilyInfo.class);
		TnpmFamilyInfo tnpmFamilyInfoInDb = tnpmFamilyInfoRepository.findOne(tnpmFamilyInfo.getFamilyId());
		if(tnpmFamilyInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tnpmFamilyInfo, tnpmFamilyInfoInDb);
		tnpmFamilyInfoRepository.save(tnpmFamilyInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tnpmFamilyInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
