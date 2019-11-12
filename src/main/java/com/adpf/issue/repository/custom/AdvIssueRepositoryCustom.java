/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.issue.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.issue.entity.AdvIssue;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdvIssueRepositoryCustom {

	/**
	 * 获取AdvIssue列表
	 * @param advIssue
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdvIssue advIssue, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdvIssue列表总数
	 * @param advIssue
	 * @param params
	 * @return
	 */
	public int getListCount(AdvIssue advIssue, Map<String, Object> params);
}