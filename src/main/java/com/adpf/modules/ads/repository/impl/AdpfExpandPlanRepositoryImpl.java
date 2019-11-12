/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.ads.repository.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.adpf.modules.ads.entity.AdpfExpandPlan;
import com.adpf.modules.ads.repository.custom.AdpfExpandPlanRepositoryCustom;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;


public class AdpfExpandPlanRepositoryImpl extends BaseRepository implements AdpfExpandPlanRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.PLAN_ID as \"planId\", tb.PLAN_NAME as \"planName\", tb.TARGET_ID as \"targetId\", tb.DAY_MAX as \"dayMax\", tb.EXPAND_TYPE as \"expandType\", tb.CREATE_DATE as \"createDate\", "
			+"t.TARGET_NAME as targetName,tb.PLAN_STATUS as \"planStatus\",tb.DEL_FLAG as \"delFlag\" "
			+ "FROM adpf_expand_plan tb "
			+ "left join adpf_expand_target t on tb.TARGET_ID = t.TARGET_ID "
			+ "WHERE tb.DEL_FLAG = 0 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_expand_plan tb "
			+ "left join adpf_expand_target t on tb.TARGET_ID = t.TARGET_ID "
			+ "WHERE tb.DEL_FLAG = 0 ";
	
	public List<Map<String, Object>> getList(AdpfExpandPlan adpfExpandPlan, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfExpandPlan, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.CREATE_DATE DESC ", " \"createDate\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfExpandPlan adpfExpandPlan, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfExpandPlan, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfExpandPlan adpfExpandPlan, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if( null != adpfExpandPlan.getUserId()) {
			sqlParams.querySql.append(" AND tb.USER_ID = :userId");
			sqlParams.paramsList.add("userId");
			sqlParams.valueList.add(adpfExpandPlan.getUserId());
		}
		if(null != params.get("planId") && !"".equals(params.get("planId"))) {
			sqlParams.querySql.append(" AND tb.PLAN_ID = :planId ");
			sqlParams.paramsList.add("planId");
			sqlParams.valueList.add(params.get("planId"));
		}
		if(StringUtils.isNotBlank((String)params.get("planStatus"))) {
			sqlParams.querySql.append(" AND tb.PLAN_STATUS = :planStatus");
			sqlParams.paramsList.add("planStatus");
			sqlParams.valueList.add(params.get("planStatus"));
		}
		if(StringUtils.isNotBlank((String)params.get("planName"))){
			sqlParams.querySql.append(" AND tb.PLAN_NAME LIKE concat('%',:planName,'%')");
			sqlParams.paramsList.add("planName");
			sqlParams.valueList.add(params.get("planName"));
		}
        return sqlParams;
        
	}
}

