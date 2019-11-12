/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.count.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;

import com.adpf.modules.gdtm.count.entity.AdpfLabelCountProvince;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfLabelCountProvinceRepositoryCustom {

	/**
	 * 获取AdpfLabelCountProvince列表
	 * @param adpfLabelCountProvince
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfLabelCountProvince adpfLabelCountProvince, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfLabelCountProvince省份统计信息
	 * @param adpfLabelCountProvince
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getProvince(AdpfLabelCountProvince adpfLabelCountProvince, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfLabelCountProvince列表总数
	 * @param adpfLabelCountProvince
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfLabelCountProvince adpfLabelCountProvince, Map<String, Object> params);
	
	/**
	 * 获取AdpfLabelCountProvince最新时间
	 * @param adpfLabelCountProvince
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getDate(AdpfLabelCountProvince adpfLabelCountProvince, Map<String, Object> params);
}