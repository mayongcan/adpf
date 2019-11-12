/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.service.impl;

import java.util.Date;
import java.text.SimpleDateFormat;
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

import com.adpf.tracking.service.TAgentService;
import com.adpf.tracking.entity.TAgent;
import com.adpf.tracking.repository.TAgentRepository;

@Service
public class TAgentServiceImpl implements TAgentService {
	
    @Autowired
    private TAgentRepository tAgentRepository;

	@Override
	public JSONObject getList(Pageable page, TAgent tAgent, Map<String, Object> params) {
		List<Map<String, Object>> list = tAgentRepository.getList(tAgent, params, page.getPageNumber(), page.getPageSize());
		int count = tAgentRepository.getListCount(tAgent, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TAgent tAgent = (TAgent) BeanUtils.mapToBean(params, TAgent.class);
	    //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")	    
	    tAgent.setCreateTime(new Date());
		tAgentRepository.save(tAgent);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TAgent tAgent = (TAgent) BeanUtils.mapToBean(params, TAgent.class);
		TAgent tAgentInDb = tAgentRepository.findOne(tAgent.getId());
		if(tAgentInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tAgent, tAgentInDb);
		tAgentRepository.save(tAgentInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tAgentRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
