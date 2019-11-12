/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tracking.entity.TAgent;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TAgentRepositoryCustom {

	/**
	 * 获取TAgent列表
	 * @param tAgent
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TAgent tAgent, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TAgent列表总数
	 * @param tAgent
	 * @param params
	 * @return
	 */
	public int getListCount(TAgent tAgent, Map<String, Object> params);
}