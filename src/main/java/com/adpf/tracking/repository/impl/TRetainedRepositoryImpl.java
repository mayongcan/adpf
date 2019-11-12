/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.tracking.entity.TRetained;
import com.adpf.tracking.repository.custom.TRetainedRepositoryCustom;

public class TRetainedRepositoryImpl extends BaseRepository implements TRetainedRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.app_id as \"appId\", tb.app_type as \"appType\", tb.imei as \"imei\", tb.idfa as \"idfa\", tb.create_time as \"createTime\", tb.login_count as \"loginCount\" "
			+ "FROM t_retained tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM t_retained tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TRetained tRetained, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tRetained, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TRetained tRetained, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tRetained, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TRetained tRetained, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        return sqlParams;
	}
}