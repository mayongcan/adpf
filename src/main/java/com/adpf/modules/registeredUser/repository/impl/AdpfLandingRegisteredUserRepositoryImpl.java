/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.registeredUser.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.registeredUser.entity.AdpfLandingRegisteredUser;
import com.adpf.modules.registeredUser.repository.custom.AdpfLandingRegisteredUserRepositoryCustom;

public class AdpfLandingRegisteredUserRepositoryImpl extends BaseRepository implements AdpfLandingRegisteredUserRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.LANDING_ID as \"landingId\", tb.REGISTERED_DATA as \"registeredData\", tb.PHONE_MODEL as \"phoneModel\", tb.OPERATING_SYSTEM as \"operatingSystem\", tb.IP_ADDRESSING as \"ipAddressing\" "
			+ "FROM adpf_landing_registered_user tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_landing_registered_user tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(AdpfLandingRegisteredUser adpfLandingRegisteredUser, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfLandingRegisteredUser, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfLandingRegisteredUser adpfLandingRegisteredUser, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfLandingRegisteredUser, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfLandingRegisteredUser adpfLandingRegisteredUser, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(StringUtils.isNotBlank((String)params.get("landingId"))) {
			sqlParams.querySql.append(" AND tb.LANDING_ID  = :landingId ");
			sqlParams.paramsList.add("landingId");
			sqlParams.valueList.add(params.get("landingId"));
		}
        return sqlParams;
	}
}