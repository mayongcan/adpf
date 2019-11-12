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
import com.adpf.modules.atm.entity.AdpfAtmApplink;
import com.adpf.modules.atm.repository.custom.AdpfAtmApplinkRepositoryCustom;

public class AdpfAtmApplinkRepositoryImpl extends BaseRepository implements AdpfAtmApplinkRepositoryCustom {

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.APP_ID as \"appId\", tb.LINK as \"link\", tb.CREATE_BY as \"createBy\", tb.CREATE_DATE as \"createDate\", tb.IS_VALID as \"isValid\" "
			+ "FROM adpf_atm_applink tb " + "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" " + "FROM adpf_atm_applink tb "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";

	public List<Map<String, Object>> getList(AdpfAtmApplink adpfAtmApplink, Map<String, Object> params, int pageIndex,
			int pageSize) {
		// 生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfAtmApplink, params);
		// 添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfAtmApplink adpfAtmApplink, Map<String, Object> params) {
		// 生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfAtmApplink, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfAtmApplink adpfAtmApplink, Map<String, Object> params) {
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		String appId = MapUtils.getString(params, "appId");
		if (!StringUtils.isBlank(appId)) {
			sqlParams.querySql.append(getLikeSql("tb.APP_ID", ":appId"));
			sqlParams.paramsList.add("appId");
			sqlParams.valueList.add(appId);
		}
		return sqlParams;
	}
}