/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.dataservice.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.dataservice.entity.DataService;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface DataServiceRepositoryCustom {

	/**
	 * 获取DataService列表
	 * @param dataService
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(DataService dataService, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取DataService列表总数
	 * @param dataService
	 * @param params
	 * @return
	 */
	public int getListCount(DataService dataService, Map<String, Object> params);
}