/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.tagLibrary.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.adpf.modules.gdtm.tagLibrary.entity.AdpfTagLibrary;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface AdpfTagLibraryService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param adpfTagLibrary
	 * @return
	 */
	public JSONObject getList(Pageable page, AdpfTagLibrary adpfTagLibrary, Map<String, Object> params);
	
	
	/**
	 * 获取未绑定列表
	 * @param page
	 * @param adpfTagLibrary
	 * @return
	 */
	public JSONObject getUnboundList(Pageable pageable, AdpfTagLibrary adpfTagLibrary, Map<String, Object> params);
	
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
	 * 获取标签url
	 * @param page
	 * @param adpfTagLibrary
	 * @return
	 */
	public JSONObject getTagUrl(Pageable page,AdpfTagLibrary adpfTagLibrary, Map<String, Object> params);

}
