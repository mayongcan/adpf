/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.ads.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;

import com.adpf.modules.ads.entity.AdpfExpandPlan;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfExpandPlanRepositoryCustom {

	/**
	 * 获取AdpfExpandPlan列表
	 * @param adpfExpandPlan
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfExpandPlan adpfExpandPlan, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfExpandPlan列表总数
	 * @param adpfExpandPlan
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfExpandPlan adpfExpandPlan, Map<String, Object> params);
}