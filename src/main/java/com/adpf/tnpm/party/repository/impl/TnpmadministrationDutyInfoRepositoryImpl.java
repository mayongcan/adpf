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

import com.adpf.tnpm.party.entity.TnpmadministrationDutyInfo;
import com.adpf.tnpm.party.repository.custom.TnpmadministrationDutyInfoRepositoryCustom;

public class TnpmadministrationDutyInfoRepositoryImpl extends BaseRepository implements TnpmadministrationDutyInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.DUTY_ID as \"dutyId\", tb.ORGANIZATION_NAME as \"organizationName\", tb.DUTY_NAME as \"dutyName\", tb.DUTY_LEVEL as \"dutyLevel\", tb.DUTY_START_DATE as \"dutyStartDate\", tb.LEAVE_REASON as \"leaveReason\", tb.LEAVE_DATE as \"leaveDate\", tb.DUTY_DETAILS as \"dutyDetails\", tb.PEOPLE_ID as \"peopleId\" "
			+ "FROM tnpm_administration_duty_info tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM tnpm_administration_duty_info tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TnpmadministrationDutyInfo tnpmadministrationDutyInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tnpmadministrationDutyInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.DUTY_ID DESC ", " \"dutyId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TnpmadministrationDutyInfo tnpmadministrationDutyInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tnpmadministrationDutyInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TnpmadministrationDutyInfo tnpmadministrationDutyInfo, Map<String, Object> params){
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