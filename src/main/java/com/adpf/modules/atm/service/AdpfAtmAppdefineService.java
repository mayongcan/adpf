/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.atm.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.adpf.modules.atm.entity.AdpfAtmAppdefine;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface AdpfAtmAppdefineService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param adpfAtmAppdefine
	 * @return
	 */
	public JSONObject getList(Pageable page, AdpfAtmAppdefine adpfAtmAppdefine, Map<String, Object> params);
	
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
