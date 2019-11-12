/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.openam.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;

import com.adpf.modules.openam.entity.AdpfOpenamFreepawdlog;
import com.alibaba.fastjson.JSONObject;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfOpenamFreepawdlogRepositoryCustom {

	/**
	 * 获取AdpfOpenamFreepawdlog列表
	 * @param adpfOpenamFreepawdlog
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfOpenamFreepawdlog adpfOpenamFreepawdlog, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfOpenamFreepawdlog列表总数
	 * @param adpfOpenamFreepawdlog
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfOpenamFreepawdlog adpfOpenamFreepawdlog, Map<String, Object> params);
	
	public  List<Map<String, Object>> getReportList(AdpfOpenamFreepawdlog adpfOpenamFreepawdlog, Map<String, Object> params);
}