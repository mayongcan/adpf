/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.collection.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.collection.entity.Collection;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface CollectionRepositoryCustom {

	/**
	 * 获取Collection列表
	 * @param collection
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(Collection collection, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取Collection列表总数
	 * @param collection
	 * @param params
	 * @return
	 */
	public int getListCount(Collection collection, Map<String, Object> params);
}