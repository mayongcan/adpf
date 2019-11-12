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

import com.adpf.tnpm.party.entity.TnpmDuesPay;
import com.adpf.tnpm.party.repository.custom.TnpmDuesPayRepositoryCustom;

public class TnpmDuesPayRepositoryImpl extends BaseRepository implements TnpmDuesPayRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.PARTY_PAYMENT_STANDARDS as \"partyPaymentStandards\", tb.PAY_PARTY_BASE as \"payPartyBase\", tb.PARTY_CALCULATED_PROPORTION as \"partyCalculatedProportion\", tb.PARTY_PAID_MONTHLY as \"partyPaidMonthly\", tb.PAYMENT_PARTY_TYPE as \"paymentPartyType\", tb.PAYMENT_PARTY_MONTH as \"paymentPartyMonth\", tb.AMOUNT_PAID as \"amountPaid\", tb.PARTY_PAYMENT_DATE as \"partyPaymentDate\", tb.LARGE_AMOUNT_PARTY as \"largeAmountParty\" "
			+ "FROM tnpm_dues_pay tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM tnpm_dues_pay tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TnpmDuesPay tnpmDuesPay, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tnpmDuesPay, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TnpmDuesPay tnpmDuesPay, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tnpmDuesPay, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TnpmDuesPay tnpmDuesPay, Map<String, Object> params){
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