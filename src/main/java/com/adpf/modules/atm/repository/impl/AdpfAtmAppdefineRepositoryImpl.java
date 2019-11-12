/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.atm.repository.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import com.adpf.modules.atm.entity.AdpfAtmAppdefine;
import com.adpf.modules.atm.repository.custom.AdpfAtmAppdefineRepositoryCustom;

public class AdpfAtmAppdefineRepositoryImpl extends BaseRepository implements AdpfAtmAppdefineRepositoryCustom {

	private static final String SQL_GET_LIST = "SELECT  tb.TYPE as \"type\",  tb.ID as \"id\", tb.NAME as \"name\", tb.CODE as \"code\", tb.COUNT as \"count\", tb.CREATE_BY as \"createBy\", tb.CREATE_DATE as \"createDate\", tb.IS_VALID as \"isValid\" "
			+ "FROM adpf_atm_appdefine tb " + "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" " + "FROM adpf_atm_appdefine tb "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";

	public List<Map<String, Object>> getList(AdpfAtmAppdefine adpfAtmAppdefine, Map<String, Object> params,
			int pageIndex, int pageSize) {
		// 生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfAtmAppdefine, params);
		// 添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfAtmAppdefine adpfAtmAppdefine, Map<String, Object> params) {
		// 生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfAtmAppdefine, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfAtmAppdefine adpfAtmAppdefine, Map<String, Object> params) {
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		String type = MapUtils.getString(params, "type");
		if (!StringUtils.isBlank(type)) {
			sqlParams.querySql.append(getLikeSql("tb.type", ":type"));
			sqlParams.paramsList.add("type");
			sqlParams.valueList.add(type);
		}
		return sqlParams;
	}
}