/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.Tencent.weChatLogin.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.adpf.modules.Tencent.weChatLogin.entity.WechatLoginUserinfo;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface WechatLoginUserinfoService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param wechatLoginUserinfo
	 * @return
	 */
	public JSONObject getList(Pageable page, WechatLoginUserinfo wechatLoginUserinfo, Map<String, Object> params);
	
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
	 * 查询用户
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject queryUser(Map<String, Object> params);
	
	/**
	 * 查询用户
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject queryID(Map<String, Object> params);
	
	/**
	 * 用户信息
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject userInfo(Map<String, Object> params);
	
	/**
	 * 删除
	 * @param idsList
	 * @param userInfo
	 * @return
	 */
	public JSONObject del(String idsList, UserInfo userInfo);
	

}
