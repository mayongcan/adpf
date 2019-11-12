/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tracking.entity.TPromo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TPromoRepositoryCustom {

	/**
	 * 获取TPromo列表
	 * @param tPromo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TPromo tPromo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TPromo列表总数
	 * @param tPromo
	 * @param params
	 * @return
	 */
	public int getListCount(TPromo tPromo, Map<String, Object> params);
}