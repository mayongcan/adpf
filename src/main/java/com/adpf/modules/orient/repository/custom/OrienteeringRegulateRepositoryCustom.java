/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.orient.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;

import com.adpf.modules.orient.entity.DirectionalInformation;
import com.adpf.modules.orient.entity.District;
import com.adpf.modules.orient.entity.OrienteeringRegulate;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface OrienteeringRegulateRepositoryCustom {

	/**
	 * 获取OrienteeringRegulate列表
	 * @param orienteeringRegulate
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(OrienteeringRegulate orienteeringRegulate, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取OrienteeringRegulate列表总数
	 * @param orienteeringRegulate
	 * @param params
	 * @return
	 */
	public int getListCount(OrienteeringRegulate orienteeringRegulate, Map<String, Object> params);
	/**
	 * 返回资料
	 * @param directionalInformation
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getListDatum(DirectionalInformation directionalInformation, Map<String, Object> params);
	/**
	 * 返回资料
	 * @param district
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getTerritory(District district, Map<String, Object> params);
	
}