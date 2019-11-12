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

import com.adpf.tnpm.party.service.TnpmadministrationDutyInfoService;
import com.adpf.tnpm.party.entity.TnpmadministrationDutyInfo;
import com.adpf.tnpm.party.repository.TnpmadministrationDutyInfoRepository;

@Service
public class TnpmadministrationDutyInfoServiceImpl implements TnpmadministrationDutyInfoService {
	
    @Autowired
    private TnpmadministrationDutyInfoRepository tnpmadministrationDutyInfoRepository;
    

	@Override
	public JSONObject getList(Pageable page, TnpmadministrationDutyInfo tnpmadministrationDutyInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = tnpmadministrationDutyInfoRepository.getList(tnpmadministrationDutyInfo, params, page.getPageNumber(), page.getPageSize());
		int count = tnpmadministrationDutyInfoRepository.getListCount(tnpmadministrationDutyInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TnpmadministrationDutyInfo tnpmadministrationDutyInfo = (TnpmadministrationDutyInfo) BeanUtils.mapToBean(params, TnpmadministrationDutyInfo.class);
		tnpmadministrationDutyInfoRepository.save(tnpmadministrationDutyInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TnpmadministrationDutyInfo tnpmadministrationDutyInfo = (TnpmadministrationDutyInfo) BeanUtils.mapToBean(params, TnpmadministrationDutyInfo.class);
		TnpmadministrationDutyInfo tnpmadministrationDutyInfoInDb = tnpmadministrationDutyInfoRepository.findOne(tnpmadministrationDutyInfo.getDutyId());
		if(tnpmadministrationDutyInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tnpmadministrationDutyInfo, tnpmadministrationDutyInfoInDb);
		tnpmadministrationDutyInfoRepository.save(tnpmadministrationDutyInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tnpmadministrationDutyInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
