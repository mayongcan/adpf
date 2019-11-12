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

import com.adpf.tnpm.party.entity.TnpmLeaveAbroad;
import com.adpf.tnpm.party.repository.custom.TnpmLeaveAbroadRepositoryCustom;

public class TnpmLeaveAbroadRepositoryImpl extends BaseRepository implements TnpmLeaveAbroadRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.NATION_NAME as \"nationName\", tb.ABROAD_DATE as \"abroadDate\", tb.ABROAD_GOAL as \"abroadGoal\", tb.CONTACT_PARTY as \"contactParty\", tb.PARTY_MEMBERSHIP_TREATMENT as \"partyMembershipTreatment\", tb.APPLY_RETENTION_TIME as \"applyRetentionTime\", tb.HOME_SITUATION as \"homeSituation\", tb.RETURN_DATE as \"returnDate\", tb.RESPOND_ORGANIZATIONAL_LIFE as \"respondOrganizationalLife\", tb.APPLY_RESUME_ORGANIZATIONAL_DATE as \"applyResumeOrganizationalDate\", tb.RATIFY_RESUME_ORGANIZATIONAL_DATE as \"ratifyResumeOrganizationalDate\", tb.WHETHER_ORGANIZATION_FOREIGN as \"whetherOrganizationForeign\", tb.ABROAD_MATTERS as \"abroadMatters\" "
			+ "FROM tnpm_leave_abroad tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM tnpm_leave_abroad tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TnpmLeaveAbroad tnpmLeaveAbroad, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tnpmLeaveAbroad, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TnpmLeaveAbroad tnpmLeaveAbroad, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tnpmLeaveAbroad, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TnpmLeaveAbroad tnpmLeaveAbroad, Map<String, Object> params){
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