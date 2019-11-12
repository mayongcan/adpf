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

import com.adpf.tracking.service.TChannelService;
import com.adpf.tracking.entity.TChannel;
import com.adpf.tracking.repository.TChannelRepository;

@Service
public class TChannelServiceImpl implements TChannelService {
	
    @Autowired
    private TChannelRepository tChannelRepository;

	@Override
	public JSONObject getList(Pageable page, TChannel tChannel, Map<String, Object> params) {
		List<Map<String, Object>> list = tChannelRepository.getList(tChannel, params, page.getPageNumber(), page.getPageSize());
		int count = tChannelRepository.getListCount(tChannel, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TChannel tChannel = (TChannel) BeanUtils.mapToBean(params, TChannel.class);
		tChannelRepository.save(tChannel);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TChannel tChannel = (TChannel) BeanUtils.mapToBean(params, TChannel.class);
		TChannel tChannelInDb = tChannelRepository.findOne(tChannel.getId());
		if(tChannelInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tChannel, tChannelInDb);
		tChannelRepository.save(tChannelInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tChannelRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
