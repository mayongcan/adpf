/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.crowd.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;

import com.adpf.modules.crowd.entity.DmpCrowdOperatingLog;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface DmpCrowdOperatingLogRepositoryCustom {

	/**
	 * 获取DmpCrowdInfo列表
	 * @param dmpCrowdInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(DmpCrowdOperatingLog dmpCrowdOperatingLog, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取DmpCrowdInfo列表总数
	 * @param dmpCrowdInfo
	 * @param params
	 * @return
	 */
	public int getListCount(DmpCrowdOperatingLog dmpCrowdOperatingLog, Map<String, Object> params);
	
	
	/**
	 * 根据crowdId查询信息
	 */
	public List<Map<String,Object>> selectByCrowdId(DmpCrowdOperatingLog dmpCrowdOperatingLog,Map<String,Object>params);
}