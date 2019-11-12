/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.ads.service.impl;

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

import com.adpf.ads.service.AdpfAdvertisementOriginalityService;
import com.adpf.ads.entity.AdpfAdvertisementOriginality;
import com.adpf.ads.repository.AdpfAdvertisementOriginalityRepository;

@Service
public class AdpfAdvertisementOriginalityServiceImpl implements AdpfAdvertisementOriginalityService {
	
    @Autowired
    private AdpfAdvertisementOriginalityRepository adpfAdvertisementOriginalityRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfAdvertisementOriginality adpfAdvertisementOriginality, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfAdvertisementOriginalityRepository.getList(adpfAdvertisementOriginality, params, page.getPageNumber(), page.getPageSize());
		int count = adpfAdvertisementOriginalityRepository.getListCount(adpfAdvertisementOriginality, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdpfAdvertisementOriginality adpfAdvertisementOriginality = (AdpfAdvertisementOriginality) BeanUtils.mapToBean(params, AdpfAdvertisementOriginality.class);
		adpfAdvertisementOriginalityRepository.save(adpfAdvertisementOriginality);
		return RestfulRetUtils.getRetSuccess(adpfAdvertisementOriginalityRepository.save(adpfAdvertisementOriginality));
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdpfAdvertisementOriginality adpfAdvertisementOriginality = (AdpfAdvertisementOriginality) BeanUtils.mapToBean(params, AdpfAdvertisementOriginality.class);
		AdpfAdvertisementOriginality adpfAdvertisementOriginalityInDb = adpfAdvertisementOriginalityRepository.findOne(adpfAdvertisementOriginality.getOriginalityId());
		if(adpfAdvertisementOriginalityInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfAdvertisementOriginality, adpfAdvertisementOriginalityInDb);
		adpfAdvertisementOriginalityRepository.save(adpfAdvertisementOriginalityInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfAdvertisementOriginalityRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
