/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.landingPage.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.landingPage.entity.AdpfLandingPage;
import com.adpf.modules.landingPage.repository.custom.AdpfLandingPageRepositoryCustom;

public class AdpfLandingPageRepositoryImpl extends BaseRepository implements AdpfLandingPageRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.PAGEPATH as \"pagepath\", tb.NAME as \"name\", tb.TYPE as \"type\" "
			+ "FROM adpf_landing_page tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_landing_page tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(AdpfLandingPage adpfLandingPage, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfLandingPage, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfLandingPage adpfLandingPage, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfLandingPage, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfLandingPage adpfLandingPage, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(StringUtils.isNotBlank((String)params.get("name"))) {
			sqlParams.querySql.append(" AND tb.NAME  = :name ");
			sqlParams.paramsList.add("name");
			sqlParams.valueList.add(params.get("name"));
		}
		if(StringUtils.isNotBlank((String)params.get("id"))) {
			sqlParams.querySql.append(" AND tb.ID  = :id ");
			sqlParams.paramsList.add("id");
			sqlParams.valueList.add(params.get("id"));
		}
        return sqlParams;
	}
}