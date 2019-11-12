/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.Tencent.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.adpf.modules.Tencent.entity.PublicAccountUserData;
import com.adpf.modules.orient.entity.DirectionalInformation;
import com.adpf.modules.orient.entity.District;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface PublicAccountUserDataService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param publicAccountUserData
	 * @return
	 */
	public JSONObject getList(Pageable page, PublicAccountUserData publicAccountUserData, Map<String, Object> params);
	
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
	 * 返回资料
	 * @param directionalInformation
	 * @param params
	 * @return
	 */
	public JSONObject getListDatum(DirectionalInformation directionalInformation, Map<String, Object> params);
	/**
	 * 返回地域
	 * @param district
	 * @param params
	 * @return
	 */
	public JSONObject getTerritory(District district, Map<String, Object> params);
	
	public JSONObject findone(Map<String, Object> params);
	
	public JSONObject findByID(PublicAccountUserData publicAccountUserData, Map<String, Object> params);

}
