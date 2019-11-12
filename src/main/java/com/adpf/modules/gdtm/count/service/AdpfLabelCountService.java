/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.count.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.adpf.modules.gdtm.count.entity.AdpfLabelCount;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface AdpfLabelCountService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param adpfLabelCount
	 * @return
	 */
	public JSONObject getList(Pageable page, AdpfLabelCount adpfLabelCount, Map<String, Object> params);
	
	/**
	 * 图形统计
	 * @param adpfLabelCount
	 * @return
	 */
	public JSONObject getTagG(Pageable page, AdpfLabelCount adpfLabelCount, Map<String, Object> params);
	
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
	

}
