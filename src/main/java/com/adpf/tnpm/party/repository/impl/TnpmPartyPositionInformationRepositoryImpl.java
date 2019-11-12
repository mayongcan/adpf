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

import com.adpf.tnpm.party.entity.TnpmPartyPositionInformation;
import com.adpf.tnpm.party.repository.custom.TnpmPartyPositionInformationRepositoryCustom;

public class TnpmPartyPositionInformationRepositoryImpl extends BaseRepository implements TnpmPartyPositionInformationRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.NAME_PARTY_ORGANIZATION as \"namePartyOrganization\", tb.PARTY_TITLE as \"partyTitle\", tb.DUTY_LEVEL as \"dutyLevel\", tb.BEGINNING_DATE_SESSION as \"beginningDateSession\", tb.LEAVE_REASON as \"leaveReason\", tb.LEAVE_DATE as \"leaveDate\", tb.PARTY_JOB_DESCRIPTION as \"partyJobDescription\", tb.EXIT_WAY as \"exitWay\", tb.PEOPLE_ID as \"peopleId\" "
			+ "FROM tnpm_party_position_information tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM tnpm_party_position_information tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TnpmPartyPositionInformation tnpmPartyPositionInformation, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tnpmPartyPositionInformation, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TnpmPartyPositionInformation tnpmPartyPositionInformation, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tnpmPartyPositionInformation, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TnpmPartyPositionInformation tnpmPartyPositionInformation, Map<String, Object> params){
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