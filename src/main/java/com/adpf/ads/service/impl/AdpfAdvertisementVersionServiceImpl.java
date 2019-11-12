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

import com.adpf.ads.service.AdpfAdvertisementVersionService;
import com.adpf.ads.entity.AdpfAdvertisementVersion;
import com.adpf.ads.repository.AdpfAdvertisementVersionRepository;

@Service
public class AdpfAdvertisementVersionServiceImpl implements AdpfAdvertisementVersionService {
	
    @Autowired
    private AdpfAdvertisementVersionRepository adpfAdvertisementVersionRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfAdvertisementVersion adpfAdvertisementVersion, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfAdvertisementVersionRepository.getList(adpfAdvertisementVersion, params, page.getPageNumber(), page.getPageSize());
		int count = adpfAdvertisementVersionRepository.getListCount(adpfAdvertisementVersion, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdpfAdvertisementVersion adpfAdvertisementVersion = (AdpfAdvertisementVersion) BeanUtils.mapToBean(params, AdpfAdvertisementVersion.class);
		adpfAdvertisementVersionRepository.save(adpfAdvertisementVersion);
		return RestfulRetUtils.getRetSuccess(adpfAdvertisementVersionRepository.save(adpfAdvertisementVersion));
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdpfAdvertisementVersion adpfAdvertisementVersion = (AdpfAdvertisementVersion) BeanUtils.mapToBean(params, AdpfAdvertisementVersion.class);
		AdpfAdvertisementVersion adpfAdvertisementVersionInDb = adpfAdvertisementVersionRepository.findOne(adpfAdvertisementVersion.getVersionId());
		if(adpfAdvertisementVersionInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfAdvertisementVersion, adpfAdvertisementVersionInDb);
		adpfAdvertisementVersionRepository.save(adpfAdvertisementVersionInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfAdvertisementVersionRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
