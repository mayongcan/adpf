/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.tagcat.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.gdtm.tagcat.entity.AdpfTagCategory;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfTagCategoryRepositoryCustom {

	/**
	 * 获取AdpfTagCategory列表
	 * @param adpfTagCategory
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfTagCategory adpfTagCategory, Map<String, Object> params, int pageIndex, int pageSize);
	
	public List<Map<String, Object>> getMonth(AdpfTagCategory adpfTagCategory, Map<String, Object> params);
	
	/**
	 * 获取AdpfTagCategory列表
	 * @param adpfTagCategory
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getListTag(AdpfTagCategory adpfTagCategory, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfTagCategory列表总数
	 * @param adpfTagCategory
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfTagCategory adpfTagCategory, Map<String, Object> params);
	
	/**
	 * 获取AdpfTagCategory列表总数
	 * @param adpfTagCategory
	 * @param params
	 * @return
	 */
	public int getListCountTag(AdpfTagCategory adpfTagCategory, Map<String, Object> params);
}