/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.count.service.impl;

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

import com.adpf.modules.gdtm.count.service.AdpfLabelCountService;
import com.adpf.modules.gdtm.count.utils.DateUtils;
import com.adpf.modules.gdtm.count.entity.AdpfLabelCount;
import com.adpf.modules.gdtm.count.repository.AdpfLabelCountRepository;

@Service
public class AdpfLabelCountServiceImpl implements AdpfLabelCountService {
	
    @Autowired
    private AdpfLabelCountRepository adpfLabelCountRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfLabelCount adpfLabelCount, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfLabelCountRepository.getList(adpfLabelCount, params, page.getPageNumber(), page.getPageSize());
		int count = adpfLabelCountRepository.getListCount(adpfLabelCount, params);
		return DateUtils.getRetSuccessWithPage(list, count);
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdpfLabelCount adpfLabelCount = (AdpfLabelCount) BeanUtils.mapToBean(params, AdpfLabelCount.class);
		adpfLabelCountRepository.save(adpfLabelCount);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdpfLabelCount adpfLabelCount = (AdpfLabelCount) BeanUtils.mapToBean(params, AdpfLabelCount.class);
		AdpfLabelCount adpfLabelCountInDb = adpfLabelCountRepository.findOne(adpfLabelCount.getId());
		if(adpfLabelCountInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfLabelCount, adpfLabelCountInDb);
		adpfLabelCountRepository.save(adpfLabelCountInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfLabelCountRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject getTagG(Pageable page, AdpfLabelCount adpfLabelCount, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfLabelCountRepository.getTagG(adpfLabelCount, params);
		int count = adpfLabelCountRepository.getListCount(adpfLabelCount, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

}
