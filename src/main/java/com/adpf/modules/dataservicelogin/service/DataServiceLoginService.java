/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.dataservicelogin.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.adpf.modules.dataservicelogin.entity.DataServiceLogin;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface DataServiceLoginService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param dataServiceLogin
	 * @return
	 */
	public JSONObject getList(Pageable page, DataServiceLogin dataServiceLogin, Map<String, Object> params);
	
	/**
	 * 新增
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject add(Map<String, Object> params);
	
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
	 * 用户信息
	 * @param params
	 * @return
	 */
	public JSONObject userInfo(Map<String, Object> params);
	
	/**
	 * 注册信息
	 * @param params
	 * @return
	 */
	public JSONObject register(Map<String, Object> params);
}
