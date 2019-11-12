/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.tagLibrary.service.impl;

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
import com.gimplatform.core.utils.RedisUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.gdtm.tagLibrary.service.AdpfTagLibraryService;
import com.adpf.modules.gdtm.tagLibrary.entity.AdpfTagLibrary;
import com.adpf.modules.gdtm.tagLibrary.repository.AdpfTagLibraryRepository;

@Service
public class AdpfTagLibraryServiceImpl implements AdpfTagLibraryService {
	
    @Autowired
    private AdpfTagLibraryRepository adpfTagLibraryRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfTagLibrary adpfTagLibrary, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfTagLibraryRepository.getList(adpfTagLibrary, params, page.getPageNumber(), page.getPageSize());
		int count = adpfTagLibraryRepository.getListCount(adpfTagLibrary, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}
	
	@Override
	public JSONObject getUnboundList(Pageable page, AdpfTagLibrary adpfTagLibrary, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfTagLibraryRepository.getUnboundList(adpfTagLibrary, params, page.getPageNumber(), page.getPageSize());
		int count = adpfTagLibraryRepository.getUnboundListCount(adpfTagLibrary, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdpfTagLibrary adpfTagLibrary = (AdpfTagLibrary) BeanUtils.mapToBean(params, AdpfTagLibrary.class);
		adpfTagLibraryRepository.save(adpfTagLibrary);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdpfTagLibrary adpfTagLibrary = (AdpfTagLibrary) BeanUtils.mapToBean(params, AdpfTagLibrary.class);
		AdpfTagLibrary adpfTagLibraryInDb = adpfTagLibraryRepository.findOne(adpfTagLibrary.getId());
		if(adpfTagLibraryInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfTagLibrary, adpfTagLibraryInDb);
		adpfTagLibraryRepository.save(adpfTagLibraryInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfTagLibraryRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject getTagUrl(Pageable page,AdpfTagLibrary adpfTagLibrary, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfTagLibraryRepository.getTagURL(adpfTagLibrary, params,page.getPageNumber(), page.getPageSize());
		System.out.println(list);
		return RestfulRetUtils.getRetSuccessWithPage(list, 1);	
	}
	
}
