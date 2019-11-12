/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.tagLibrary.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.gdtm.tagLibrary.entity.AdpfTagLibrary;
import com.adpf.modules.gdtm.tagLibrary.repository.custom.AdpfTagLibraryRepositoryCustom;

public class AdpfTagLibraryRepositoryImpl extends BaseRepository implements AdpfTagLibraryRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.label as \"label\", tb.tag_id as \"key\" "
			+ "FROM adpf_tag_library tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_tag_library tb "
			+ "WHERE 1 = 1 ";
	//查询未绑定的标签
	private static final String SQL_GET_LIST_N = "SELECT tb.id as \"id\", tb.label as \"label\", tb.tag_id as \"key\" "
			+ "FROM adpf_tag_library tb "
			+ "WHERE 1 = 1 and tb.is_valid='N' ";
	
	//标签和URL关联
	private static final String SQL_GET_LIST_URL_TAG = "SELECT lu.label_url as \"url\" "
			+ "FROM adpf_tag_library tb inner join adpf_label_url lu on tb.tag_id = lu.label_code "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT_N = "SELECT count(1) as \"count\" "
			+ "FROM adpf_tag_library tb "
			+ "WHERE 1 = 1 and tb.is_valid='N' ";
	
	public List<Map<String, Object>> getList(AdpfTagLibrary adpfTagLibrary, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfTagLibrary, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}
		
	@Override
	public List<Map<String, Object>> getTagURL(AdpfTagLibrary adpfTagLibrary, Map<String, Object> params,int pageIndex, int pageSize) {
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_URL_TAG, adpfTagLibrary, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfTagLibrary adpfTagLibrary, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfTagLibrary, params);
		return getResultListTotalCount(sqlParams);
	}
	
	public List<Map<String, Object>> getUnboundList(AdpfTagLibrary adpfTagLibrary, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_N, adpfTagLibrary, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getUnboundListCount(AdpfTagLibrary adpfTagLibrary, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT_N, adpfTagLibrary, params);
		return getResultListTotalCount(sqlParams);
	}
	
	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfTagLibrary adpfTagLibrary, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (adpfTagLibrary != null && !StringUtils.isBlank(adpfTagLibrary.getLabel())) {
			sqlParams.querySql.append(getLikeSql("tb.label", ":label"));
			sqlParams.paramsList.add("label");
			sqlParams.valueList.add(adpfTagLibrary.getLabel());
		}
		if (adpfTagLibrary != null && !StringUtils.isBlank(adpfTagLibrary.getTagId())) {
			sqlParams.querySql.append(" AND tb.tag_id = :tagId ");
			sqlParams.paramsList.add("tagId");
			sqlParams.valueList.add(adpfTagLibrary.getTagId());
		}
        return sqlParams;
	}
}