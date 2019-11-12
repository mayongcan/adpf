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

import com.adpf.tnpm.party.entity.TnpmDifficultSituation;
import com.adpf.tnpm.party.repository.custom.TnpmDifficultSituationRepositoryCustom;

public class TnpmDifficultSituationRepositoryImpl extends BaseRepository implements TnpmDifficultSituationRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.DIFFICULT_TYPE as \"difficultType\", tb.ENJOY_LOW as \"enjoyLow\", tb.HEALTH_CONDITION as \"healthCondition\", tb.ENJOY_BENEFITS as \"enjoyBenefits\", tb.A_WHOLE as \"aWhole\", tb.LIFE_HARD as \"lifeHard\" "
			+ "FROM tnpm_difficult_situation tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM tnpm_difficult_situation tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TnpmDifficultSituation tnpmDifficultSituation, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tnpmDifficultSituation, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TnpmDifficultSituation tnpmDifficultSituation, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tnpmDifficultSituation, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TnpmDifficultSituation tnpmDifficultSituation, Map<String, Object> params){
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