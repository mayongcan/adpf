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

import com.adpf.qtn.entity.QtnWindowInfo;
import com.adpf.qtn.repository.custom.QtnWindowInfoRepositoryCustom;

public class QtnWindowInfoRepositoryImpl extends BaseRepository implements QtnWindowInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.WINDOW_ID as \"windowId\", tb.WINDOW_NAME as \"windowName\", tb.WINDOW_NUMBER as \"windowNumber\", tb.STATUS as \"status\", tb.CREATE_DATE as \"createDate\",tb.CURRENT_NUMBER as currentNumber,tb.USER_ID as userId,tb.ORGANIZER_ID as organizerId, "
			+ "u.USER_NAME as userName,"
			+ "o.ORGANIZER_NAME as organizerName "
			+ "FROM qtn_window_info tb left join sys_user_info u on tb.USER_ID = u.USER_ID "
			+ "left join sys_organizer_info o on tb.ORGANIZER_ID = o.ORGANIZER_ID "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM qtn_window_info tb "
			+ "WHERE 1 = 1 ";
	
	private static final String SQL_GET_LIST_BYORGANIZERID = "SELECT tb.WINDOW_ID as \"windowId\", tb.WINDOW_NAME as \"windowName\", tb.WINDOW_NUMBER as \"windowNumber\", tb.STATUS as \"status\", tb.CREATE_DATE as \"createDate\",tb.CURRENT_NUMBER as currentNumber,tb.USER_ID as userId,tb.ORGANIZER_ID as organizerId "
			+ "FROM qtn_window_info tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(QtnWindowInfo qtnWindowInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, qtnWindowInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.WINDOW_ID ASC ", " \"windowId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(QtnWindowInfo qtnWindowInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, qtnWindowInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, QtnWindowInfo qtnWindowInfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(params.get("organizerId") != null  ) {
			sqlParams.querySql.append(" AND tb.ORGANIZER_ID = :organizerId ");
			sqlParams.paramsList.add("organizerId");
			sqlParams.valueList.add(params.get("organizerId"));
		}
		if(params.get("userId") != null) {
			sqlParams.querySql.append(" AND tb.USER_ID = :userId ");
			sqlParams.paramsList.add("userId");
			sqlParams.valueList.add(params.get("userId"));
		}
        return sqlParams;
	}

	@Override
	public List<Map<String, Object>> getListByOrganizerId(Map<String, Object> params) {
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_BYORGANIZERID,  new QtnWindowInfo(), params);
		return getResultList(sqlParams);
	}
}