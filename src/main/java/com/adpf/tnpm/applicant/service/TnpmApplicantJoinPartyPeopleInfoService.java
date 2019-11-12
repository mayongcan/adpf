/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.applicant.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.adpf.tnpm.applicant.entity.TnpmApplicantJoinPartyPeopleInfo;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface TnpmApplicantJoinPartyPeopleInfoService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param tnpmJoinPartyPeopleInfo
	 * @return
	 */
	public JSONObject getList(Pageable page, TnpmApplicantJoinPartyPeopleInfo tnpmJoinPartyPeopleInfo, Map<String, Object> params);
	
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
	public JSONObject del(Long peopleId, UserInfo userInfo);
	

	
	public JSONObject updateImg(Map<String, Object> params, UserInfo userInfo);
	
	public JSONObject getDetails(Map<String, Object>params,UserInfo userInfo);
}
