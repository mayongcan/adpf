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

import com.adpf.tracking.service.TAppOrderService;
import com.adpf.tracking.entity.TAppOrder;
import com.adpf.tracking.repository.TAppOrderRepository;

@Service
public class TAppOrderServiceImpl implements TAppOrderService {
	
    @Autowired
    private TAppOrderRepository tAppOrderRepository;

	@Override
	public JSONObject getList(Pageable page, TAppOrder tAppOrder, Map<String, Object> params) {
		List<Map<String, Object>> list = tAppOrderRepository.getList(tAppOrder, params, page.getPageNumber(), page.getPageSize());
		int count = tAppOrderRepository.getListCount(tAppOrder, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TAppOrder tAppOrder = (TAppOrder) BeanUtils.mapToBean(params, TAppOrder.class);
		tAppOrderRepository.save(tAppOrder);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TAppOrder tAppOrder = (TAppOrder) BeanUtils.mapToBean(params, TAppOrder.class);
		TAppOrder tAppOrderInDb = tAppOrderRepository.findOne(tAppOrder.getId());
		if(tAppOrderInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tAppOrder, tAppOrderInDb);
		tAppOrderRepository.save(tAppOrderInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tAppOrderRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
