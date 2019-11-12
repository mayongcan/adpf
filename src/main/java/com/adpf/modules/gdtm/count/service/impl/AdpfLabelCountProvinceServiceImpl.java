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

import com.adpf.modules.gdtm.count.service.AdpfLabelCountProvinceService;
import com.adpf.modules.gdtm.count.utils.DateUtils;
import com.adpf.modules.gdtm.count.entity.AdpfLabelCountProvince;
import com.adpf.modules.gdtm.count.repository.AdpfLabelCountProvinceRepository;

@Service
public class AdpfLabelCountProvinceServiceImpl implements AdpfLabelCountProvinceService {
	
    @Autowired
    private AdpfLabelCountProvinceRepository adpfLabelCountProvinceRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfLabelCountProvince adpfLabelCountProvince, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfLabelCountProvinceRepository.getList(adpfLabelCountProvince, params, page.getPageNumber(), page.getPageSize());
		int count = adpfLabelCountProvinceRepository.getListCount(adpfLabelCountProvince, params);
		return DateUtils.getRetSuccessWithPage(list, count);	
	}
	
	@Override
	public JSONObject getProvince(Pageable page, AdpfLabelCountProvince adpfLabelCountProvince,
			Map<String, Object> params) {
		List<Map<String, Object>> list = adpfLabelCountProvinceRepository.getProvince(adpfLabelCountProvince, params, page.getPageNumber(), page.getPageSize());
		int count = adpfLabelCountProvinceRepository.getListCount(adpfLabelCountProvince, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdpfLabelCountProvince adpfLabelCountProvince = (AdpfLabelCountProvince) BeanUtils.mapToBean(params, AdpfLabelCountProvince.class);
		adpfLabelCountProvinceRepository.save(adpfLabelCountProvince);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdpfLabelCountProvince adpfLabelCountProvince = (AdpfLabelCountProvince) BeanUtils.mapToBean(params, AdpfLabelCountProvince.class);
		AdpfLabelCountProvince adpfLabelCountProvinceInDb = adpfLabelCountProvinceRepository.findOne(adpfLabelCountProvince.getId());
		if(adpfLabelCountProvinceInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfLabelCountProvince, adpfLabelCountProvinceInDb);
		adpfLabelCountProvinceRepository.save(adpfLabelCountProvinceInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfLabelCountProvinceRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
