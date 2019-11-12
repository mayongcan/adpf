/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.seats.repository.custom;

import com.adpf.seats.entity.AdvidSeatsid;
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
public interface AdvidSeatsidRepositoryCustom {

	/**
	 * 获取AdvidSeatsid列表
	 * @param advidSeatsid
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdvidSeatsid advidSeatsid, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdvidSeatsid列表总数
	 * @param advidSeatsid
	 * @param params
	 * @return
	 */
	public int getListCount(AdvidSeatsid advidSeatsid, Map<String, Object> params);

    List<Map<String, Object>> getListBySeatsId(String seatsId);

	List<Map<String, Object>> getListByAdvId(String advId);
}