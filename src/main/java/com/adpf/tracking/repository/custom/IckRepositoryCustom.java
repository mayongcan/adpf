/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tracking.entity.Ick;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface IckRepositoryCustom {

	/**
	 * 获取Ick列表
	 * @param ick
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(Ick ick, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取Ick列表总数
	 * @param ick
	 * @param params
	 * @return
	 */
	public int getListCount(Ick ick, Map<String, Object> params);
}