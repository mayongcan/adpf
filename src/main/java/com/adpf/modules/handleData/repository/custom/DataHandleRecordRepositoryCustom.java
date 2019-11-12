/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.handleData.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.handleData.entity.DataHandleRecord;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface DataHandleRecordRepositoryCustom {

	/**
	 * 获取DataHandleRecord列表
	 * @param dataHandleRecord
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(DataHandleRecord dataHandleRecord, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取DataHandleRecord列表总数
	 * @param dataHandleRecord
	 * @param params
	 * @return
	 */
	public int getListCount(DataHandleRecord dataHandleRecord, Map<String, Object> params);
}