/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.tagdetails.service.impl;

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

import com.adpf.modules.gdtm.tagdetails.service.AdpfTagDetailsService;
import com.adpf.modules.gdtm.tagdetails.entity.AdpfTagDetails;
import com.adpf.modules.gdtm.tagdetails.repository.AdpfTagDetailsRepository;

@Service
public class AdpfTagDetailsServiceImpl implements AdpfTagDetailsService {
	
    @Autowired
    private AdpfTagDetailsRepository adpfTagDetailsRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfTagDetails adpfTagDetails, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfTagDetailsRepository.getList(adpfTagDetails, params, page.getPageNumber(), page.getPageSize());
		int count = adpfTagDetailsRepository.getListCount(adpfTagDetails, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdpfTagDetails adpfTagDetails = (AdpfTagDetails) BeanUtils.mapToBean(params, AdpfTagDetails.class);
		adpfTagDetailsRepository.save(adpfTagDetails);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdpfTagDetails adpfTagDetails = (AdpfTagDetails) BeanUtils.mapToBean(params, AdpfTagDetails.class);
		AdpfTagDetails adpfTagDetailsInDb = adpfTagDetailsRepository.findOne(adpfTagDetails.getId());
		if(adpfTagDetailsInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfTagDetails, adpfTagDetailsInDb);
		adpfTagDetailsRepository.save(adpfTagDetailsInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfTagDetailsRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
