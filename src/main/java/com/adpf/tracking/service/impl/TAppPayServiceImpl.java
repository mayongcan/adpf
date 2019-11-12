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

import com.adpf.tracking.service.TAppPayService;
import com.adpf.tracking.entity.TAppPay;
import com.adpf.tracking.repository.TAppPayRepository;

@Service
public class TAppPayServiceImpl implements TAppPayService {
	
    @Autowired
    private TAppPayRepository tAppPayRepository;

	@Override
	public JSONObject getList(Pageable page, TAppPay tAppPay, Map<String, Object> params) {
		List<Map<String, Object>> list = tAppPayRepository.getList(tAppPay, params, page.getPageNumber(), page.getPageSize());
		int count = tAppPayRepository.getListCount(tAppPay, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TAppPay tAppPay = (TAppPay) BeanUtils.mapToBean(params, TAppPay.class);
		tAppPayRepository.save(tAppPay);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TAppPay tAppPay = (TAppPay) BeanUtils.mapToBean(params, TAppPay.class);
		TAppPay tAppPayInDb = tAppPayRepository.findOne(tAppPay.getId());
		if(tAppPayInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tAppPay, tAppPayInDb);
		tAppPayRepository.save(tAppPayInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tAppPayRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
