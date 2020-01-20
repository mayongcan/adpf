/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.repository.custom;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.hive.metastore.api.ThriftHiveMetastore.Processor.list_privileges;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tracking.entity.TPromoCurr;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TPromoCurrRepositoryCustom {

	/**
	 * 获取TPromoCurr列表
	 * @param tPromoCurr
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TPromoCurr tPromoCurr, Map<String, Object> params, int pageIndex, int pageSize);
	//public List<Map<String, Object>> getList(TPromoCurr tPromoCurr, Map<String, Object> params);
	
	/**
	 * 获取TPromoCurr列表总数
	 * @param tPromoCurr
	 * @param params
	 * @return
	 */
	public int getListCount(TPromoCurr tPromoCurr, Map<String, Object> params);
	
	public List<Map<String, Object>>getEquipmentFrom(Map<String, Object> params);
	
	public List<Map<String, Object>>getNature(TPromoCurr tPromoCurr, Map<String, Object> params, int pageIndex, int pageSize);
	
	public int getNatureCount(TPromoCurr tPromoCurr, Map<String, Object> params);
	
	
}