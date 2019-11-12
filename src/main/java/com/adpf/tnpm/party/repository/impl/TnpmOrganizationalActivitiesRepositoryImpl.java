/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.tnpm.party.entity.TnpmOrganizationalActivities;
import com.adpf.tnpm.party.repository.custom.TnpmOrganizationalActivitiesRepositoryCustom;

public class TnpmOrganizationalActivitiesRepositoryImpl extends BaseRepository implements TnpmOrganizationalActivitiesRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.DATE_ORGANIZATION as \"dateOrganization\", tb.PLACE_ORGANIZATION as \"placeOrganization\", tb.TYPES_ORGANIZATION as \"typesOrganization\", tb.ACTIVITIES_ORGANIZATIONA as \"activitiesOrganizationa\" "
			+ "FROM tnpm_organizational_activities tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM tnpm_organizational_activities tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TnpmOrganizationalActivities tnpmOrganizationalActivities, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tnpmOrganizationalActivities, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TnpmOrganizationalActivities tnpmOrganizationalActivities, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tnpmOrganizationalActivities, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TnpmOrganizationalActivities tnpmOrganizationalActivities, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(null != params.get("peopleId")){
			sqlParams.querySql.append(" AND tb.PEOPLE_ID = :peopleId ");
			sqlParams.paramsList.add("peopleId");
			sqlParams.valueList.add(params.get("peopleId"));
		}
        return sqlParams;
	}
}