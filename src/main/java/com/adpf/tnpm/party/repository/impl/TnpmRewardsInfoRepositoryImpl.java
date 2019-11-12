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

import com.adpf.tnpm.party.entity.TnpmRewardsInfo;
import com.adpf.tnpm.party.repository.custom.TnpmRewardsInfoRepositoryCustom;

public class TnpmRewardsInfoRepositoryImpl extends BaseRepository implements TnpmRewardsInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.REWARDS_ID as \"rewardsId\", tb.REWARDS_NAME as \"rewardsName\", tb.REWARDS_FILE as \"rewardsFile\", tb.REWARDS_REASON as \"rewardsReason\", tb.REWARDS_RATIFY_OFFICE as \"rewardsRatifyOffice\", tb.REWARDS_LEVEL as \"rewardsLevel\", tb.RATIFY_DATE as \"ratifyDate\", tb.CANCEL_DATE as \"cancelDate\", tb.REWARDS_DETAILS as \"rewardsDetails\", tb.PEOPLE_ID as \"peopleId\" "
			+ "FROM tnpm_rewards_info tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM tnpm_rewards_info tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TnpmRewardsInfo tnpmRewardsInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tnpmRewardsInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.REWARDS_ID DESC ", " \"rewardsId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TnpmRewardsInfo tnpmRewardsInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tnpmRewardsInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TnpmRewardsInfo tnpmRewardsInfo, Map<String, Object> params){
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