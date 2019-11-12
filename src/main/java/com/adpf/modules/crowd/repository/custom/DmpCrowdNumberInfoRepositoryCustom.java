/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.crowd.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;

import com.adpf.modules.crowd.entity.DmpCrowdNumberInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface DmpCrowdNumberInfoRepositoryCustom {

	/**
	 * 获取DmpCrowdInfo列表
	 * @param dmpCrowdInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(DmpCrowdNumberInfo dmpCrowdNumberInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取DmpCrowdInfo列表总数
	 * @param dmpCrowdInfo
	 * @param params
	 * @return
	 */
	public int getListCount(DmpCrowdNumberInfo dmpCrowdNumberInfo, Map<String, Object> params);
	
	
}