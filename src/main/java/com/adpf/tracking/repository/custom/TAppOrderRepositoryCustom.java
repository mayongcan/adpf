/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tracking.entity.TAppOrder;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TAppOrderRepositoryCustom {

	/**
	 * 获取TAppOrder列表
	 * @param tAppOrder
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TAppOrder tAppOrder, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TAppOrder列表总数
	 * @param tAppOrder
	 * @param params
	 * @return
	 */
	public int getListCount(TAppOrder tAppOrder, Map<String, Object> params);
}