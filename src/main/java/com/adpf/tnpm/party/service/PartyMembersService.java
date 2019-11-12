/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.adpf.tnpm.party.entity.PartyMembers;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface PartyMembersService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param tnpmJoinPartyPeopleInfo
	 * @return
	 */
	public JSONObject getList(Pageable page, PartyMembers tnpmJoinPartyPeopleInfo, Map<String, Object> params);
	
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
	

	
	public JSONObject updateImg(Map<String, Object> params, UserInfo userInfo);
	
	public JSONObject getDetails(Map<String, Object>params,UserInfo userInfo);
}
