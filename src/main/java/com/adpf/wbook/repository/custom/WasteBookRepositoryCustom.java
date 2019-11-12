/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.wbook.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.wbook.entity.WasteBook;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface WasteBookRepositoryCustom {

	/**
	 * 获取WasteBook列表
	 * @param wasteBook
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(WasteBook wasteBook, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取WasteBook列表总数
	 * @param wasteBook
	 * @param params
	 * @return
	 */
	public int getListCount(WasteBook wasteBook, Map<String, Object> params);
}