/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.cms.service.impl;

import java.util.Date;
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

import com.adpf.modules.cms.service.AdpfCmsCategoryService;
import com.adpf.modules.cms.entity.AdpfCmsCategory;
import com.adpf.modules.cms.repository.AdpfCmsCategoryRepository;

@Service
public class AdpfCmsCategoryServiceImpl implements AdpfCmsCategoryService {
	
    @Autowired
    private AdpfCmsCategoryRepository adpfCmsCategoryRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfCmsCategory adpfCmsCategory, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfCmsCategoryRepository.getList(adpfCmsCategory, params, page.getPageNumber(), page.getPageSize());
		int count = adpfCmsCategoryRepository.getListCount(adpfCmsCategory, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
		params.put("categoryTime", new Date());
		params.put("userId", userInfo.getUserId());
	    AdpfCmsCategory adpfCmsCategory = (AdpfCmsCategory) BeanUtils.mapToBean(params, AdpfCmsCategory.class);
		adpfCmsCategoryRepository.save(adpfCmsCategory);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
		params.put("categoryMtime", new Date());
        AdpfCmsCategory adpfCmsCategory = (AdpfCmsCategory) BeanUtils.mapToBean(params, AdpfCmsCategory.class);
		AdpfCmsCategory adpfCmsCategoryInDb = adpfCmsCategoryRepository.findOne(adpfCmsCategory.getCategoryId());
		if(adpfCmsCategoryInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfCmsCategory, adpfCmsCategoryInDb);
		adpfCmsCategoryRepository.save(adpfCmsCategoryInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfCmsCategoryRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject getAll() {
		List<AdpfCmsCategory> list = adpfCmsCategoryRepository.findAll();
		return RestfulRetUtils.getRetSuccess(list);
	}

	@Override
	public JSONObject getSimList(Pageable page, AdpfCmsCategory adpfCmsCategory, Map<String, Object> params) {
		// TODO Auto-generated method stub
		//return null;
		List<Map<String, Object>> list = adpfCmsCategoryRepository.getSimList(adpfCmsCategory, params, page.getPageNumber(), page.getPageSize());
		int count = adpfCmsCategoryRepository.getListCount(adpfCmsCategory, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);
	}

	

}
