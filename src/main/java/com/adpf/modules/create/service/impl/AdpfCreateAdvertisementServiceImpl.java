/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.create.service.impl;

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

import com.adpf.modules.create.service.AdpfCreateAdvertisementService;
import com.adpf.modules.create.entity.AdpfCreateAdvertisement;
import com.adpf.modules.create.repository.AdpfCreateAdvertisementRepository;

@Service
public class AdpfCreateAdvertisementServiceImpl implements AdpfCreateAdvertisementService {
	
    @Autowired
    private AdpfCreateAdvertisementRepository adpfCreateAdvertisementRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfCreateAdvertisement adpfCreateAdvertisement, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfCreateAdvertisementRepository.getList(adpfCreateAdvertisement, params, page.getPageNumber(), page.getPageSize());
		int count = adpfCreateAdvertisementRepository.getListCount(adpfCreateAdvertisement, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdpfCreateAdvertisement adpfCreateAdvertisement = (AdpfCreateAdvertisement) BeanUtils.mapToBean(params, AdpfCreateAdvertisement.class);
		adpfCreateAdvertisementRepository.save(adpfCreateAdvertisement);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdpfCreateAdvertisement adpfCreateAdvertisement = (AdpfCreateAdvertisement) BeanUtils.mapToBean(params, AdpfCreateAdvertisement.class);
		AdpfCreateAdvertisement adpfCreateAdvertisementInDb = adpfCreateAdvertisementRepository.findOne(adpfCreateAdvertisement.getId());
		if(adpfCreateAdvertisementInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfCreateAdvertisement, adpfCreateAdvertisementInDb);
		adpfCreateAdvertisementRepository.save(adpfCreateAdvertisementInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfCreateAdvertisementRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
