/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.ads.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.adpf.modules.ads.entity.AdpfExpandPlan;
import com.adpf.modules.ads.repository.AdpfExpandPlanRepository;
import com.adpf.modules.ads.service.AdpfExpandPlanService;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;


@Service
public class AdpfExpandPlanServiceImpl implements AdpfExpandPlanService {
	
    @Autowired
    private AdpfExpandPlanRepository adpfExpandPlanRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfExpandPlan adpfExpandPlan, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfExpandPlanRepository.getList(adpfExpandPlan, params, page.getPageNumber(), page.getPageSize());
		int count = adpfExpandPlanRepository.getListCount(adpfExpandPlan, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdpfExpandPlan adpfExpandPlan = (AdpfExpandPlan) BeanUtils.mapToBean(params, AdpfExpandPlan.class);
		adpfExpandPlan.setCreateDate(new Date());
		adpfExpandPlan.setUserId(userInfo.getUserId());
		adpfExpandPlan.setPlanStatus("0");
		adpfExpandPlanRepository.save(adpfExpandPlan);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdpfExpandPlan adpfExpandPlan = (AdpfExpandPlan) BeanUtils.mapToBean(params, AdpfExpandPlan.class);
		AdpfExpandPlan adpfExpandPlanInDb = adpfExpandPlanRepository.findOne(adpfExpandPlan.getPlanId());
		if(adpfExpandPlanInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfExpandPlan, adpfExpandPlanInDb);
		adpfExpandPlanRepository.save(adpfExpandPlanInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfExpandPlanRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
