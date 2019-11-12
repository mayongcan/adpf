/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.ads.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.adpf.modules.ads.entity.AdpfExpandTarget;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;


/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface AdpfExpandTargetService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param adpfExpandTarget
	 * @return
	 */
	public JSONObject getList(Pageable page, AdpfExpandTarget adpfExpandTarget, Map<String, Object> params);
	
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
	
	/*
	 * 查找下一级
	 */
	public JSONObject checkNext(Map<String, Object> params, UserInfo userInfo);
	

}
