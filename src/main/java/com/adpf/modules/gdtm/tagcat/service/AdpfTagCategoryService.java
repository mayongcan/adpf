/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.tagcat.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.adpf.modules.gdtm.tagcat.entity.AdpfTagCategory;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface AdpfTagCategoryService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param adpfTagCategory
	 * @return
	 */
	public JSONObject getList(Pageable page, AdpfTagCategory adpfTagCategory, Map<String, Object> params);
	
	/**
	 * 获取月份统计列表
	 * @param page
	 * @param adpfTagCategory
	 * @return
	 */
	public JSONObject getMonth(Map<String, Object> params);
	
	
	/**
	 * 获取关联列表
	 * @param page
	 * @param adpfTagCategory
	 * @return
	 */
	public JSONObject getListTag(Pageable page, AdpfTagCategory adpfTagCategory, Map<String, Object> params);
	
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
	 * 获取树内容
	 * @return
	 */
	public JSONObject getTreeList();

	/**
	 * 根据父标志，获取列表
	 * @param adpfTagCategory
	 * @return
	 */
	public List<AdpfTagCategory> getListByParentIds(AdpfTagCategory adpfTagCategory);
	
	/**
     * 保存绑定
     * @param userInfo
     * @param catId
     * @param tagIdList
     * @return
     */
    public JSONObject addBindings(UserInfo userInfo, Long catId, List<Long> tagIdList);
    
    
    /**
     * 删除绑定
     * @param userInfo
     * @param catId
     * @param tagIdList
     * @return
     */
    public JSONObject delBindings(UserInfo userInfo, Long catId, Long tagId);
	

}
