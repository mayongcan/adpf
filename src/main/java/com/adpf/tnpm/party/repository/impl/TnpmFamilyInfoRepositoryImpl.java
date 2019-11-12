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

import com.adpf.tnpm.party.entity.TnpmFamilyInfo;
import com.adpf.tnpm.party.repository.custom.TnpmFamilyInfoRepositoryCustom;

public class TnpmFamilyInfoRepositoryImpl extends BaseRepository implements TnpmFamilyInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.FAMILY_ID as \"familyId\", tb.FAMILY_NAME as \"familyName\", tb.FAMILY_RELATION as \"familyRelation\", tb.SEX as \"sex\", tb.ID_CARD as \"idCard\", tb.NATION as \"nation\", tb.POLITICS_STATUS as \"politicsStatus\", tb.PHONE as \"phone\", tb.JOB_PLACE as \"jobPlace\", tb.PEOPLE_ID as \"peopleId\" "
			+ "FROM tnpm_family_info tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM tnpm_family_info tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TnpmFamilyInfo tnpmFamilyInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tnpmFamilyInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.FAMILY_ID DESC ", " \"familyId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TnpmFamilyInfo tnpmFamilyInfo, Map<String, Object> params) {
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
	private SqlParams genListWhere(String sql, TnpmFamilyInfo tnpmFamilyInfo, Map<String, Object> params){
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