/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.ads.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;

import com.adpf.modules.ads.entity.AdpfExpandTarget;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfExpandTargetRepositoryCustom {

	/**
	 * 获取AdpfExpandTarget列表
	 * @param adpfExpandTarget
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfExpandTarget adpfExpandTarget, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfExpandTarget列表总数
	 * @param adpfExpandTarget
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfExpandTarget adpfExpandTarget, Map<String, Object> params);
	
	/*
	 * 获取下级
	 * 
	 */
	public List<Map<String,Object>> checkNext(AdpfExpandTarget adpfExpandTarget, Map<String, Object> params);
}