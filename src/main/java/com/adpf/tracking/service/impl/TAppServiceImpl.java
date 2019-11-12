/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.service.impl;

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

import com.adpf.tracking.service.TAppService;
import com.adpf.tracking.tools.util;
import com.adpf.tracking.entity.TApp;
import com.adpf.tracking.repository.TAppRepository;

@Service
public class TAppServiceImpl implements TAppService {
	
    @Autowired
    private TAppRepository tAppRepository;

	@Override
	public JSONObject getList(Pageable page, TApp tApp, Map<String, Object> params) {
		List<Map<String, Object>> list = tAppRepository.getList(tApp, params, page.getPageNumber(), page.getPageSize());
		int count = tAppRepository.getListCount(tApp, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
		String appKey = util.usingMath(16);
		params.put("appKey", appKey);
	    TApp tApp = (TApp) BeanUtils.mapToBean(params, TApp.class);
	    tApp.setCreateTime(new Date());
		tAppRepository.save(tApp);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TApp tApp = (TApp) BeanUtils.mapToBean(params, TApp.class);
		TApp tAppInDb = tAppRepository.findOne(tApp.getId());
		if(tAppInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tApp, tAppInDb);
		tAppRepository.save(tAppInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tAppRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
