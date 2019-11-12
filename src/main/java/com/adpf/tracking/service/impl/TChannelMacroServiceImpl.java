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

import com.adpf.tracking.service.TChannelMacroService;
import com.adpf.tracking.entity.TChannelMacro;
import com.adpf.tracking.repository.TChannelMacroRepository;

@Service
public class TChannelMacroServiceImpl implements TChannelMacroService {
	
    @Autowired
    private TChannelMacroRepository tChannelMacroRepository;

	@Override
	public JSONObject getList(Pageable page, TChannelMacro tChannelMacro, Map<String, Object> params) {
		List<Map<String, Object>> list = tChannelMacroRepository.getList(tChannelMacro, params, page.getPageNumber(), page.getPageSize());
		int count = tChannelMacroRepository.getListCount(tChannelMacro, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TChannelMacro tChannelMacro = (TChannelMacro) BeanUtils.mapToBean(params, TChannelMacro.class);
		tChannelMacroRepository.save(tChannelMacro);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TChannelMacro tChannelMacro = (TChannelMacro) BeanUtils.mapToBean(params, TChannelMacro.class);
		TChannelMacro tChannelMacroInDb = tChannelMacroRepository.findOne(tChannelMacro.getId());
		if(tChannelMacroInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tChannelMacro, tChannelMacroInDb);
		tChannelMacroRepository.save(tChannelMacroInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tChannelMacroRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
