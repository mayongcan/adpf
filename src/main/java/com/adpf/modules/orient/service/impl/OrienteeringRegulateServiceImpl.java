/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.orient.service.impl;

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

import com.adpf.modules.orient.service.OrienteeringRegulateService;
import com.adpf.modules.orient.entity.DirectionalInformation;
import com.adpf.modules.orient.entity.District;
import com.adpf.modules.orient.entity.OrienteeringRegulate;
import com.adpf.modules.orient.repository.OrienteeringRegulateRepository;

@Service
public class OrienteeringRegulateServiceImpl implements OrienteeringRegulateService {
	
    @Autowired
    private OrienteeringRegulateRepository orienteeringRegulateRepository;
    
    @Override
	public JSONObject getListDatum(DirectionalInformation directionalInformation, Map<String, Object> params) {
		List<Map<String, Object>> list = orienteeringRegulateRepository.getListDatum(directionalInformation, params);
		return RestfulRetUtils.getRetSuccess(list);
	}
    
	@Override
	public JSONObject getList(Pageable page, OrienteeringRegulate orienteeringRegulate, Map<String, Object> params) {
		List<Map<String, Object>> list = orienteeringRegulateRepository.getList(orienteeringRegulate, params, page.getPageNumber(), page.getPageSize());
		int count = orienteeringRegulateRepository.getListCount(orienteeringRegulate, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    OrienteeringRegulate orienteeringRegulate = (OrienteeringRegulate) BeanUtils.mapToBean(params, OrienteeringRegulate.class);
	    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+orienteeringRegulate);
		orienteeringRegulateRepository.save(orienteeringRegulate);
		return RestfulRetUtils.getRetSuccess(orienteeringRegulateRepository.save(orienteeringRegulate));
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        OrienteeringRegulate orienteeringRegulate = (OrienteeringRegulate) BeanUtils.mapToBean(params, OrienteeringRegulate.class);
		OrienteeringRegulate orienteeringRegulateInDb = orienteeringRegulateRepository.findOne(orienteeringRegulate.getOrienteerId());
		if(orienteeringRegulateInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(orienteeringRegulate, orienteeringRegulateInDb);
		orienteeringRegulateRepository.save(orienteeringRegulateInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			orienteeringRegulateRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject getTerritory(District district, Map<String, Object> params) {
		List<Map<String, Object>> list = orienteeringRegulateRepository.getTerritory(district, params);
		return RestfulRetUtils.getRetSuccess(list);
	}
	 
}
