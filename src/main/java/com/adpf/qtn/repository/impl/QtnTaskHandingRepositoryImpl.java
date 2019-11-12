/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.qtn.entity.QtnTaskHanding;
import com.adpf.qtn.repository.custom.QtnTaskHandingRepositoryCustom;

public class QtnTaskHandingRepositoryImpl extends BaseRepository implements QtnTaskHandingRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.TASK_ID as \"taskId\", tb.OPEN_ID as \"openId\", tb.STATUS as \"status\", tb.CREATE_DATE as \"createDate\", tb.USER_TYPE as \"userType\", tb.USER_ID as \"userId\",tb.FORMAT_NUMBER as formatNumber, tb.NUMBER as \\\"number\\\", tb.ORGANIZER_ID as organizerId,"
			+ "tb.START_MANAGER_TIME as startManagerTime ,tb.END_MANAGER_TIME as endManagerTime,tb.IS_VIP as isVip, org.ORGANIZER_NAME as organizerName "
			+ "FROM qtn_task_handing tb  inner join  sys_organizer_info org on tb.ORGANIZER_ID = org.ORGANIZER_ID "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM qtn_task_handing tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(QtnTaskHanding qtnTaskHanding, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, qtnTaskHanding, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ORGANIZER_ID ASC ", " \"taskId\" ASC ");
		return getResultList(sqlParams);
	}
	
	public List<Map<String, Object>> getWaitList(QtnTaskHanding qtnTaskHanding, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, qtnTaskHanding, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.IS_VIP DESC ", "");
		return getResultList(sqlParams);
	}
	
	
	public int getListCount(QtnTaskHanding qtnTaskHanding, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, qtnTaskHanding, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, QtnTaskHanding qtnTaskHanding, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(params.get("organizerId") != null  ) {
			sqlParams.querySql.append(" AND tb.ORGANIZER_ID = :organizerId ");
			sqlParams.paramsList.add("organizerId");
			sqlParams.valueList.add(params.get("organizerId"));
		}
		if(StringUtils.isNotBlank((String)params.get("today"))) {
			sqlParams.querySql.append(" AND Date(tb.CREATE_DATE) = :today ");
			sqlParams.paramsList.add("today");
			sqlParams.valueList.add(params.get("today"));
		}
		if(StringUtils.isNotBlank((String)params.get("status"))){
			sqlParams.querySql.append(" AND tb.STATUS = :status");
			sqlParams.paramsList.add("status");
			sqlParams.valueList.add(params.get("status"));
		}
		if(params.get("openId") != null) {
			sqlParams.querySql.append(" AND tb.OPEN_ID = :openId ");
			sqlParams.paramsList.add("openId");
			sqlParams.valueList.add(params.get("openId"));
		}
		if(StringUtils.isNotBlank((String)params.get("statusList"))) {
			sqlParams.querySql.append(" AND tb.status in (:statusList) ");
			sqlParams.paramsList.add("statusList");
			sqlParams.valueList.add(params.get("statusList"));
		}
		if(StringUtils.isNotBlank((String)params.get("isVip"))){
			sqlParams.querySql.append(" AND tb.IS_VIP = :isVip");
			sqlParams.paramsList.add("isVip");
			sqlParams.valueList.add(params.get("isVip"));
		}
        return sqlParams;
	}
}