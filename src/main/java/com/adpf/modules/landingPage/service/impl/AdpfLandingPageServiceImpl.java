/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.landingPage.service.impl;

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

import com.adpf.modules.landingPage.service.AdpfLandingPageService;
import com.adpf.modules.landingPage.entity.AdpfLandingPage;
import com.adpf.modules.landingPage.repository.AdpfLandingPageRepository;

@Service
public class AdpfLandingPageServiceImpl implements AdpfLandingPageService {
	
    @Autowired
    private AdpfLandingPageRepository adpfLandingPageRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfLandingPage adpfLandingPage, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfLandingPageRepository.getList(adpfLandingPage, params, page.getPageNumber(), page.getPageSize());
		int count = adpfLandingPageRepository.getListCount(adpfLandingPage, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdpfLandingPage adpfLandingPage = (AdpfLandingPage) BeanUtils.mapToBean(params, AdpfLandingPage.class);
		adpfLandingPageRepository.save(adpfLandingPage);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdpfLandingPage adpfLandingPage = (AdpfLandingPage) BeanUtils.mapToBean(params, AdpfLandingPage.class);
		AdpfLandingPage adpfLandingPageInDb = adpfLandingPageRepository.findOne(adpfLandingPage.getId());
		if(adpfLandingPageInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfLandingPage, adpfLandingPageInDb);
		adpfLandingPageRepository.save(adpfLandingPageInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfLandingPageRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
