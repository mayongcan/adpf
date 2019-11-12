/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.collection.service.impl;

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

import com.adpf.collection.service.CollectionService;
import com.adpf.collection.entity.Collection;
import com.adpf.collection.repository.CollectionRepository;

@Service
public class CollectionServiceImpl implements CollectionService {
	
    @Autowired
    private CollectionRepository collectionRepository;

	@Override
	public JSONObject getList(Pageable page, Collection collection, Map<String, Object> params) {
		List<Map<String, Object>> list = collectionRepository.getList(collection, params, page.getPageNumber(), page.getPageSize());
		int count = collectionRepository.getListCount(collection, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    Collection collection = (Collection) BeanUtils.mapToBean(params, Collection.class);
		collectionRepository.save(collection);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        Collection collection = (Collection) BeanUtils.mapToBean(params, Collection.class);
		Collection collectionInDb = collectionRepository.findOne(collection.getId());
		if(collectionInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(collection, collectionInDb);
		collectionRepository.save(collectionInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			collectionRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
