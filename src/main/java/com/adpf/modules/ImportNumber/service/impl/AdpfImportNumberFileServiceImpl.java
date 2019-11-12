/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.ImportNumber.service.impl;

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

import com.adpf.modules.ImportNumber.service.AdpfImportNumberFileService;
import com.adpf.modules.ImportNumber.entity.AdpfImportNumberFile;
import com.adpf.modules.ImportNumber.repository.AdpfImportNumberFileRepository;

@Service
public class AdpfImportNumberFileServiceImpl implements AdpfImportNumberFileService {
	
    @Autowired
    private AdpfImportNumberFileRepository adpfImportNumberFileRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfImportNumberFile adpfImportNumberFile, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfImportNumberFileRepository.getList(adpfImportNumberFile, params, page.getPageNumber(), page.getPageSize());
		int count = adpfImportNumberFileRepository.getListCount(adpfImportNumberFile, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdpfImportNumberFile adpfImportNumberFile = (AdpfImportNumberFile) BeanUtils.mapToBean(params, AdpfImportNumberFile.class);
		adpfImportNumberFileRepository.save(adpfImportNumberFile);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdpfImportNumberFile adpfImportNumberFile = (AdpfImportNumberFile) BeanUtils.mapToBean(params, AdpfImportNumberFile.class);
		AdpfImportNumberFile adpfImportNumberFileInDb = adpfImportNumberFileRepository.findOne(adpfImportNumberFile.getId());
		if(adpfImportNumberFileInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfImportNumberFile, adpfImportNumberFileInDb);
		adpfImportNumberFileRepository.save(adpfImportNumberFileInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfImportNumberFileRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
