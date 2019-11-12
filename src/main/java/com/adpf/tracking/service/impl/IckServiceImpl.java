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

import com.adpf.tracking.service.IckService;
import com.adpf.tracking.entity.Ick;
import com.adpf.tracking.repository.IckRepository;

@Service
public class IckServiceImpl implements IckService {
	
    @Autowired
    private IckRepository ickRepository;

	@Override
	public JSONObject getList(Pageable page, Ick ick, Map<String, Object> params) {
		List<Map<String, Object>> list = ickRepository.getList(ick, params, page.getPageNumber(), page.getPageSize());
		int count = ickRepository.getListCount(ick, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    Ick ick = (Ick) BeanUtils.mapToBean(params, Ick.class);
		ickRepository.save(ick);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        Ick ick = (Ick) BeanUtils.mapToBean(params, Ick.class);
		Ick ickInDb = ickRepository.findOne(ick.getId());
		if(ickInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(ick, ickInDb);
		ickRepository.save(ickInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			ickRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
