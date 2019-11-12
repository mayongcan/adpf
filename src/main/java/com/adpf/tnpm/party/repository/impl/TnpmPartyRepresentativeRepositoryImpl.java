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

import com.adpf.tnpm.party.entity.TnpmPartyRepresentative;
import com.adpf.tnpm.party.repository.custom.TnpmPartyRepresentativeRepositoryCustom;

public class TnpmPartyRepresentativeRepositoryImpl extends BaseRepository implements TnpmPartyRepresentativeRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.STATUS_PARTY_REPRESENTATIVES as \"statusPartyRepresentatives\", tb.FOR_THE_TIME as \"forTheTime\", tb.BEGINNING_DATE_SESSION as \"beginningDateSession\", tb.THE_EXPIRY_DATE as \"theExpiryDate\", tb.PEOPLE_ID as \"peopleId\" "
			+ "FROM tnpm_party_representative tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM tnpm_party_representative tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TnpmPartyRepresentative tnpmPartyRepresentative, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tnpmPartyRepresentative, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TnpmPartyRepresentative tnpmPartyRepresentative, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tnpmPartyRepresentative, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TnpmPartyRepresentative tnpmPartyRepresentative, Map<String, Object> params){
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