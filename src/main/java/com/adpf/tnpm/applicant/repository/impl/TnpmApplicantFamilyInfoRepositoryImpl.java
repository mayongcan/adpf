/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.applicant.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;

import com.adpf.tnpm.applicant.entity.TnpmApplicantFamilyInfo;
import com.adpf.tnpm.applicant.repository.custom.TnpmApplicantFamilyInfoRepositoryCustom;

public class TnpmApplicantFamilyInfoRepositoryImpl extends BaseRepository implements TnpmApplicantFamilyInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.family_id as \"familyId\", tb.family_name as \"familyName\", tb.family_relation as \"familyRelation\", tb.id_card as \"idCard\", tb.job_place as \"jobPlace\", tb.nation as \"nation\", tb.people_id as \"peopleId\", tb.phone as \"phone\", tb.politics_status as \"politicsStatus\", tb.sex as \"sex\" "
			+ "FROM tnpm_family_info tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM tnpm_family_info tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TnpmApplicantFamilyInfo tnpmFamilyInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tnpmFamilyInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.family_id DESC ", " \"familyId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TnpmApplicantFamilyInfo tnpmFamilyInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tnpmFamilyInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TnpmApplicantFamilyInfo tnpmFamilyInfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(null != params.get("peopleId")) {
			sqlParams.querySql.append(" AND tb.PEOPLE_ID  = :peopleId ");
			sqlParams.paramsList.add("peopleId");
			sqlParams.valueList.add(params.get("peopleId"));
		}
        return sqlParams;
	}
}