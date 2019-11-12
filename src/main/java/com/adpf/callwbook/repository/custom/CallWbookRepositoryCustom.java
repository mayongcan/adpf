/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.callwbook.repository.custom;

import com.adpf.callwbook.entity.CallWbook;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface CallWbookRepositoryCustom {

	/**
	 * 获取CallWbook列表
	 * @param callWbook
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(CallWbook callWbook, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取CallWbook列表总数
	 * @param callWbook
	 * @param params
	 * @return
	 */
	public int getListCount(CallWbook callWbook, Map<String, Object> params);

    public List<Map<String,Object>> getFistList(String data);


	public List<Map<String,Object>> getMaxTime();
}