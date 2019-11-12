/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.ads.service.impl;

import java.util.Date;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.adpf.modules.ads.entity.AdpfExpandTarget;
import com.adpf.modules.ads.repository.AdpfExpandTargetRepository;
import com.adpf.modules.ads.service.AdpfExpandTargetService;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;


@Service
public class AdpfExpandTargetServiceImpl implements AdpfExpandTargetService {

	@Autowired
	private AdpfExpandTargetRepository adpfExpandTargetRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfExpandTarget adpfExpandTarget, Map<String, Object> params) {
		params.put("parentId", "0");
		List<Map<String, Object>> list = adpfExpandTargetRepository.getList(adpfExpandTarget, params, page.getPageNumber(), page.getPageSize());
		int count = adpfExpandTargetRepository.getListCount(adpfExpandTarget, params);
		//查询是否有下一级的推广目标
		List<Map<String,Object>> newList=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : list) {
			params.put("parentId", map.get("targetId"));
			List<Map<String,Object>> nextList=adpfExpandTargetRepository.getList(adpfExpandTarget, params, page.getPageNumber(), page.getPageSize());	
			map.put("nextList", nextList);
			newList.add(map);
		}
		return RestfulRetUtils.getRetSuccessWithPage(newList, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
		AdpfExpandTarget adpfExpandTarget = (AdpfExpandTarget) BeanUtils.mapToBean(params, AdpfExpandTarget.class);
		adpfExpandTarget.setCreateDate(new Date());
		adpfExpandTargetRepository.save(adpfExpandTarget);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
		AdpfExpandTarget adpfExpandTarget = (AdpfExpandTarget) BeanUtils.mapToBean(params, AdpfExpandTarget.class);
		AdpfExpandTarget adpfExpandTargetInDb = adpfExpandTargetRepository.findOne(adpfExpandTarget.getTargetId());
		if(adpfExpandTargetInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfExpandTarget, adpfExpandTargetInDb);
		adpfExpandTargetRepository.save(adpfExpandTargetInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfExpandTargetRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject checkNext(Map<String, Object> params, UserInfo userInfo) {
		AdpfExpandTarget adpfExpandTarget = (AdpfExpandTarget) BeanUtils.mapToBean(params, AdpfExpandTarget.class);
		AdpfExpandTarget aet =  adpfExpandTargetRepository.findOne(adpfExpandTarget.getParentId());
		if(aet == null) {
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		 List<Map<String,Object>> list = adpfExpandTargetRepository.checkNext(adpfExpandTarget,params);
		return RestfulRetUtils.getRetSuccessWithPage(list, list.size());
	}

	
	
}
