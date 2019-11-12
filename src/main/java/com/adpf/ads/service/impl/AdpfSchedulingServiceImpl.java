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

import com.adpf.ads.service.AdpfSchedulingService;
import com.adpf.ads.entity.AdpfScheduling;
import com.adpf.ads.repository.AdpfSchedulingRepository;

@Service
public class AdpfSchedulingServiceImpl implements AdpfSchedulingService {
	
    @Autowired
    private AdpfSchedulingRepository adpfSchedulingRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfScheduling adpfScheduling, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfSchedulingRepository.getList(adpfScheduling, params, page.getPageNumber(), page.getPageSize());
		int count = adpfSchedulingRepository.getListCount(adpfScheduling, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdpfScheduling adpfScheduling = (AdpfScheduling) BeanUtils.mapToBean(params, AdpfScheduling.class);
		adpfSchedulingRepository.save(adpfScheduling);
		return RestfulRetUtils.getRetSuccess(adpfSchedulingRepository.save(adpfScheduling));
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdpfScheduling adpfScheduling = (AdpfScheduling) BeanUtils.mapToBean(params, AdpfScheduling.class);
		AdpfScheduling adpfSchedulingInDb = adpfSchedulingRepository.findOne(adpfScheduling.getSchedulingId());
		if(adpfSchedulingInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfScheduling, adpfSchedulingInDb);
		adpfSchedulingRepository.save(adpfSchedulingInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfSchedulingRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
