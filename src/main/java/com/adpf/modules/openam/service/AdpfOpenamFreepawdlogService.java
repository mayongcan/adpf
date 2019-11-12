/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.openam.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.adpf.modules.openam.entity.AdpfOpenamFreepawdlog;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

/**
 * 服务类接口
 * 
 * @version 1.0
 * @author
 */
public interface AdpfOpenamFreepawdlogService {

	/**
	 * 获取列表
	 * 
	 * @param page
	 * @param adpfOpenamFreepawdlog
	 * @return
	 */
	public JSONObject getList(Pageable page, AdpfOpenamFreepawdlog adpfOpenamFreepawdlog, Map<String, Object> params);

	/**
	 * 新增
	 * 
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject add(Map<String, Object> params, JSONObject jobect, UserInfo userInfo);

	/**
	 * 编辑
	 * 
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo);

	/**
	 * 删除
	 * 
	 * @param idsList
	 * @param userInfo
	 * @return
	 */
	public JSONObject del(String idsList, UserInfo userInfo);

	/**
	 * 更新电话号码
	 * 
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject udpateMobile(String mobile, String aesCacheKey);

	public JSONObject getReportList(AdpfOpenamFreepawdlog adpfOpenamFreepawdlog, Map<String, Object> params);

	public AdpfOpenamFreepawdlog getByCode(String code);

	public JSONObject addIm(Map<String, Object> params, JSONObject jobect, UserInfo userInfo);
}
