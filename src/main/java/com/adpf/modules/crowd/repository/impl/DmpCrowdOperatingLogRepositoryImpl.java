/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.crowd.repository.impl;

import java.util.List;
import java.util.Map;

import com.adpf.modules.crowd.entity.DmpCrowdOperatingLog;
import com.adpf.modules.crowd.repository.custom.DmpCrowdOperatingLogRepositoryCustom;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;


public class DmpCrowdOperatingLogRepositoryImpl extends BaseRepository implements DmpCrowdOperatingLogRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.LOG_ID as \"logId\", tb.LOG_NAME as \"logName\", tb.LOG_DETAILS as \"logDetails\", tb.CREATE_DATE as \"createDate\", tb.CROWD_ID as \"crowdId\" "
			+ "FROM dmp_crowd_operating_log tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM dmp_crowd_operating_log tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(DmpCrowdOperatingLog dmpCrowdOperatingLog, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, dmpCrowdOperatingLog, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.LOG_ID DESC ", " \"logId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(DmpCrowdOperatingLog dmpCrowdOperatingLog, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, dmpCrowdOperatingLog, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, DmpCrowdOperatingLog dmpCrowdOperatingLog, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        return sqlParams;
	}

	@Override
	public List<Map<String, Object>> selectByCrowdId(DmpCrowdOperatingLog dmpCrowdOperatingLog,
			Map<String, Object> params) {
		//生成查询条件
				SqlParams sqlParams = genListWhere(SQL_GET_LIST, dmpCrowdOperatingLog, params);
				return getResultList(sqlParams);
	}
}