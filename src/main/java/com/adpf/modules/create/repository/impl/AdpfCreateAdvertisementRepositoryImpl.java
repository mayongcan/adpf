/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.create.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.create.entity.AdpfCreateAdvertisement;
import com.adpf.modules.create.repository.custom.AdpfCreateAdvertisementRepositoryCustom;

public class AdpfCreateAdvertisementRepositoryImpl extends BaseRepository implements AdpfCreateAdvertisementRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.ADVERTISEMNT_NAME as \"advertisemntName\", tb.ORIENTEER_ID as \"orienteerId\", tb.PLAN_ID as \"planId\", tb.SCHEDULING_ID as \"schedulingId\", tb.VERSION_ID as \"versionId\", tb.ORIGINALITY_ID as \"originalityId\", tb.FINANCE_ID as \"financeId\", tb.COUNT_ID as \"countId\", tb.HISTDATA as \"histdata\", tb.EXPENDITURE as \"expenditure\", tb.CPC as \"cpc\", tb.STATE as \"state\", tb.PAGEID as \"pageid\" "
			+ "FROM adpf_create_advertisement tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_create_advertisement tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(AdpfCreateAdvertisement adpfCreateAdvertisement, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfCreateAdvertisement, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfCreateAdvertisement adpfCreateAdvertisement, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfCreateAdvertisement, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfCreateAdvertisement adpfCreateAdvertisement, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(StringUtils.isNotBlank((String)params.get("id"))) {
			sqlParams.querySql.append(" AND tb.ID  = :id ");
			sqlParams.paramsList.add("id");
			sqlParams.valueList.add(params.get("id"));
		}
		if(StringUtils.isNotBlank((String)params.get("orienteerId"))) {
			sqlParams.querySql.append(" AND tb.ORIENTEER_ID  = :orienteerId ");
			sqlParams.paramsList.add("orienteerId");
			sqlParams.valueList.add(params.get("orienteerId"));
		}
        return sqlParams;
	}
}