/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.oem.repository.custom;

import com.adpf.oem.entity.AdvertisingOem;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdvertisingOemRepositoryCustom {

	/**
	 * 获取AdvertisingOem列表
	 * @param advertisingOem
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdvertisingOem advertisingOem, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdvertisingOem列表总数
	 * @param advertisingOem
	 * @param params
	 * @return
	 */
	public int getListCount(AdvertisingOem advertisingOem, Map<String, Object> params);

	public List<Map<String, Object>> getRoleList(String id);
}