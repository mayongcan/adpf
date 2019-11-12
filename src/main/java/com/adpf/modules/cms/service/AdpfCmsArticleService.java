/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.cms.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.adpf.modules.cms.entity.AdpfCmsArticle;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface AdpfCmsArticleService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param adpfCmsArticle
	 * @return
	 */
	public JSONObject getList(Pageable page, AdpfCmsArticle adpfCmsArticle, Map<String, Object> params);
	
	/**
	 * 获取已发布列表
	 * @param page
	 * @param adpfCmsArticle
	 * @return
	 */	
	public JSONObject getWebList(Pageable page, AdpfCmsArticle adpfCmsArticle, Map<String, Object> params);
	
	public JSONObject getWebIdList(AdpfCmsArticle adpfCmsArticle,Map<String, Object> params);
	
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

	/**
	 * 发布
	 * @param idsList
	 * @param userInfo
	 * @return
	 */
	public JSONObject send(Map<String, Object> params, UserInfo userInfo);
	
	/**
	 * 点击量
	 * @param idsList
	 * @param userInfo
	 * @return
	 */
	public JSONObject addClicks(Map<String, Object> params);

	
	public JSONObject getDetails(Map<String,Object> params,UserInfo userInfo);
	
}
