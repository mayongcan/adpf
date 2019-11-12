/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.cms.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.adpf.modules.cms.entity.AdpfCmsCategory;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface AdpfCmsCategoryService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param adpfCmsCategory
	 * @return
	 */
	public JSONObject getList(Pageable page, AdpfCmsCategory adpfCmsCategory, Map<String, Object> params);
	
	/**
	 * 新增
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject add(Map<String, Object> params, UserInfo userInfo);
	
	/**
	 * 编辑
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo);
	
	/**
	 * 删除
	 * @param idsList
	 * @param userInfo
	 * @return
	 */
	public JSONObject del(String idsList, UserInfo userInfo);
	
	public JSONObject getAll();
	
	public JSONObject getSimList(Pageable page,AdpfCmsCategory adpfCmsCategory, Map<String, Object> params);
}
