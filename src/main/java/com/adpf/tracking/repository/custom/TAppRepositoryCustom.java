/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tracking.entity.TApp;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TAppRepositoryCustom {

	/**
	 * 获取TApp列表
	 * @param tApp
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TApp tApp, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TApp列表总数
	 * @param tApp
	 * @param params
	 * @return
	 */
	public int getListCount(TApp tApp, Map<String, Object> params);
}