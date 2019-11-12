/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.count.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.gdtm.count.entity.AdpfLabelCount;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfLabelCountRepositoryCustom {

	/**
	 * 获取AdpfLabelCount列表
	 * @param adpfLabelCount
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfLabelCount adpfLabelCount, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfLabelCount月份统计列表
	 * @param adpfLabelCount
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getListMonth(AdpfLabelCount adpfLabelCount, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfLabelCount图形统计
	 * @param adpfLabelCount
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getTagG(AdpfLabelCount adpfLabelCount, Map<String, Object> params);
	
	/**
	 * 获取AdpfLabelCount列表总数
	 * @param adpfLabelCount
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfLabelCount adpfLabelCount, Map<String, Object> params);
	
	/**
	 * 获取AdpfLabelCount月份统计列表总数
	 * @param adpfLabelCount
	 * @param params
	 * @return
	 */
	public int getListCountMonth(AdpfLabelCount adpfLabelCount, Map<String, Object> params);
	
	/**
	 * 获取AdpfLabelCount最新时间
	 * @param adpfLabelCount
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getDate(AdpfLabelCount adpfLabelCount, Map<String, Object> params);
}