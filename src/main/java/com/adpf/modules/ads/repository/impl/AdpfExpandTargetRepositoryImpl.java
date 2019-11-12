/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.ads.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;

import com.adpf.modules.ads.entity.AdpfExpandTarget;
import com.adpf.modules.ads.repository.custom.AdpfExpandTargetRepositoryCustom;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;


public class AdpfExpandTargetRepositoryImpl extends BaseRepository implements AdpfExpandTargetRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.TARGET_ID as \"targetId\", tb.TARGET_NAME as \"targetName\", tb.TARGET_DETAILS as \"targetDetails\", tb.TARGET_LOGO as \"targetLogo\", tb.CREATE_DATE as \"createDate\",tb.PARENT_ID as \"parentId\" "
			+ "FROM adpf_expand_target tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_expand_target tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(AdpfExpandTarget adpfExpandTarget, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfExpandTarget, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.CREATE_DATE asc ", " \"createDate\" asc ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfExpandTarget adpfExpandTarget, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfExpandTarget, params);
		return getResultListTotalCount(sqlParams);
	}

	
	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfExpandTarget adpfExpandTarget, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(null != params.get("parentId")) {
			sqlParams.querySql.append(" AND tb.PARENT_ID = :parentId");
			sqlParams.paramsList.add("parentId");
			sqlParams.valueList.add(params.get("parentId"));
		}
		
        return sqlParams;
	}

	@Override
	public List<Map<String, Object>> checkNext(AdpfExpandTarget adpfExpandTarget, Map<String, Object> params) {
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfExpandTarget, params);
		return getResultList(sqlParams);
	}
}