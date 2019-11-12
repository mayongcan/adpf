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

import com.adpf.tracking.service.TChannelurlService;
import com.adpf.tracking.entity.TChannelurl;
import com.adpf.tracking.repository.TChannelurlRepository;

@Service
public class TChannelurlServiceImpl implements TChannelurlService {
	
    @Autowired
    private TChannelurlRepository tChannelurlRepository;

	@Override
	public JSONObject getList(Pageable page, TChannelurl tChannelurl, Map<String, Object> params) {
		List<Map<String, Object>> list = tChannelurlRepository.getList(tChannelurl, params, page.getPageNumber(), page.getPageSize());
		int count = tChannelurlRepository.getListCount(tChannelurl, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TChannelurl tChannelurl = (TChannelurl) BeanUtils.mapToBean(params, TChannelurl.class);
	    tChannelurl.setCreateTime(new Date());
		tChannelurlRepository.save(tChannelurl);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TChannelurl tChannelurl = (TChannelurl) BeanUtils.mapToBean(params, TChannelurl.class);
		TChannelurl tChannelurlInDb = tChannelurlRepository.findOne(tChannelurl.getId());
		if(tChannelurlInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tChannelurl, tChannelurlInDb);
		tChannelurlRepository.save(tChannelurlInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tChannelurlRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
