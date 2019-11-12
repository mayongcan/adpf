/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.province.service.impl;

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

import com.adpf.modules.gdtm.province.service.AdpfProvinceSizeService;
import com.adpf.modules.gdtm.province.entity.AdpfProvinceSize;
import com.adpf.modules.gdtm.province.repository.AdpfProvinceSizeRepository;

@Service
public class AdpfProvinceSizeServiceImpl implements AdpfProvinceSizeService {
	
    @Autowired
    private AdpfProvinceSizeRepository adpfProvinceSizeRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfProvinceSize adpfProvinceSize, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfProvinceSizeRepository.getList(adpfProvinceSize, params, page.getPageNumber(), page.getPageSize());
		int count = adpfProvinceSizeRepository.getListCount(adpfProvinceSize, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdpfProvinceSize adpfProvinceSize = (AdpfProvinceSize) BeanUtils.mapToBean(params, AdpfProvinceSize.class);
		adpfProvinceSizeRepository.save(adpfProvinceSize);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdpfProvinceSize adpfProvinceSize = (AdpfProvinceSize) BeanUtils.mapToBean(params, AdpfProvinceSize.class);
		AdpfProvinceSize adpfProvinceSizeInDb = adpfProvinceSizeRepository.findOne(adpfProvinceSize.getId());
		if(adpfProvinceSizeInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfProvinceSize, adpfProvinceSizeInDb);
		adpfProvinceSizeRepository.save(adpfProvinceSizeInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfProvinceSizeRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
