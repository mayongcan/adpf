/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.cms.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.cms.entity.AdpfCmsCategory;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfCmsCategoryRepositoryCustom {

	/**
	 * 获取AdpfCmsCategory列表
	 * @param adpfCmsCategory
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfCmsCategory adpfCmsCategory, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfCmsCategory列表总数
	 * @param adpfCmsCategory
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfCmsCategory adpfCmsCategory, Map<String, Object> params);
	
	
	public List<Map<String, Object>> getSimList(AdpfCmsCategory adpfCmsCategory, Map<String, Object> params, int pageIndex, int pageSize);
	
	
	
	
	
	
}