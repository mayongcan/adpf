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

import com.adpf.tnpm.party.entity.TnpmPartyMembership;
import com.adpf.tnpm.party.repository.custom.TnpmPartyMembershipRepositoryCustom;

public class TnpmPartyMembershipRepositoryImpl extends BaseRepository implements TnpmPartyMembershipRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.PARTY_STATE as \"partyState\", tb.THE_PARTY_DATE as \"thePartyDate\", tb.PARTY_STANDING as \"partyStanding\", tb.JUST_DATE as \"justDate\", tb.PROBATIONARY_PARTY_STATUS as \"probationaryPartyStatus\", tb.INTRODUCER as \"introducer\", tb.PARTY_BRANCH as \"partyBranch\", tb.PARTY_BRANCH_TYPE as \"partyBranchType\", tb.DATE_DEPARTURE_PARTY as \"dateDepartureParty\", tb.DEVELOPMENT_UNIT as \"developmentUnit\", tb.PARTY_AFFILIATION as \"partyAffiliation\", tb.THE_PARTY_REASON as \"thePartyReason\", tb.JOIN_PARTY_ORGANIZATION as \"joinPartyOrganization\", tb.OUT_PARTY_ORGANIZATION as \"outPartyOrganization\", tb.RETURN_DATE as \"returnDate\", tb.PEOPLE_ID as  \"people_ID\""
			+ "FROM tnpm_party_membership tb "
			+ "WHERE 1 = 1 ";
	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM tnpm_party_membership tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TnpmPartyMembership tnpmPartyMembership, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tnpmPartyMembership, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TnpmPartyMembership tnpmPartyMembership, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tnpmPartyMembership, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TnpmPartyMembership tnpmPartyMembership, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		/*System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+params.get("peopleId"));
		sql=sql+"where tb.PEOPLE_ID="+params.get("peopleId");
		System.out.println("??????????????????????????"+sql);
		sqlParams.querySql.append(sql);
		*/
		if(null != params.get("peopleId")){
			sqlParams.querySql.append(" AND tb.PEOPLE_ID = :peopleId ");
			sqlParams.paramsList.add("peopleId");
			sqlParams.valueList.add(params.get("peopleId"));
		}
        return sqlParams;
	}
}