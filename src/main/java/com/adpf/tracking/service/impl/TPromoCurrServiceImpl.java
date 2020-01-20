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

import com.adpf.tracking.service.TPromoCurrService;
import com.adpf.tracking.entity.TPromoCurr;
import com.adpf.tracking.repository.TPromoCurrRepository;

@Service
public class TPromoCurrServiceImpl implements TPromoCurrService {
	
    @Autowired
    private TPromoCurrRepository tPromoCurrRepository;

	@Override
	public JSONObject getList(Pageable page, TPromoCurr tPromoCurr, Map<String, Object> params) {
		List<Map<String, Object>> list = tPromoCurrRepository.getList(tPromoCurr, params, page.getPageNumber(), page.getPageSize());
		int count = tPromoCurrRepository.getListCount(tPromoCurr, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);
		//return RestfulRetUtils.getRetSuccess(list);
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TPromoCurr tPromoCurr = (TPromoCurr) BeanUtils.mapToBean(params, TPromoCurr.class);
		tPromoCurrRepository.save(tPromoCurr);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TPromoCurr tPromoCurr = (TPromoCurr) BeanUtils.mapToBean(params, TPromoCurr.class);
		TPromoCurr tPromoCurrInDb = tPromoCurrRepository.findOne(tPromoCurr.getId());
		if(tPromoCurrInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tPromoCurr, tPromoCurrInDb);
		tPromoCurrRepository.save(tPromoCurrInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tPromoCurrRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject getEquipment(Map<String, Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>>list = tPromoCurrRepository.getEquipmentFrom(params);
		return RestfulRetUtils.getRetSuccess(list);
	}
	
	@Override
	public JSONObject getNature(Pageable page, TPromoCurr tPromoCurr, Map<String, Object> params) {
		List<Map<String, Object>> list = tPromoCurrRepository.getNature(tPromoCurr, params, page.getPageNumber(), page.getPageSize());
		int count = tPromoCurrRepository.getNatureCount(tPromoCurr, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);
		//return RestfulRetUtils.getRetSuccess(list);
	}

}
