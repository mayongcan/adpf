/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.service.impl;

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

import com.adpf.qtn.service.QtnWechatInfoService;
import com.adpf.qtn.util.CallUtil;
import com.adpf.qtn.entity.QtnWechatInfo;
import com.adpf.qtn.repository.QtnWechatInfoRepository;

@Service
public class QtnWechatInfoServiceImpl implements QtnWechatInfoService {
	
    @Autowired
    private QtnWechatInfoRepository qtnWechatInfoRepository;

	@Override
	public JSONObject getList(Pageable page, QtnWechatInfo qtnWechatInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = qtnWechatInfoRepository.getList(qtnWechatInfo, params, page.getPageNumber(), page.getPageSize());
		int count = qtnWechatInfoRepository.getListCount(qtnWechatInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    QtnWechatInfo qtnWechatInfo = (QtnWechatInfo) BeanUtils.mapToBean(params, QtnWechatInfo.class);
		qtnWechatInfo.setCreateDate(new Date());
		qtnWechatInfoRepository.save(qtnWechatInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        QtnWechatInfo qtnWechatInfo = (QtnWechatInfo) BeanUtils.mapToBean(params, QtnWechatInfo.class);
		QtnWechatInfo qtnWechatInfoInDb = qtnWechatInfoRepository.findOne(qtnWechatInfo.getWechatId());
		if(qtnWechatInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(qtnWechatInfo, qtnWechatInfoInDb);
		qtnWechatInfoRepository.save(qtnWechatInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			qtnWechatInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject findOneByOpenId(Map<String, Object> params) {
		List<Map<String, Object>> list = qtnWechatInfoRepository.findOneByOpenId(params);
		return RestfulRetUtils.getRetSuccessWithPage(list, list.size());	
	}


}
