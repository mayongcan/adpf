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

import com.adpf.tracking.entity.TApp;
import com.adpf.tracking.repository.custom.TAppRepositoryCustom;

public class TAppRepositoryImpl extends BaseRepository implements TAppRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.name as \"name\", tb.app_key as \"appKey\", tb.app_type as \"appType\", tb.landing_url as \"landingUrl\", tb.staue as \"staue\", tb.create_time as \"createTime\" "
			+ "FROM t_app tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM t_app tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TApp tApp, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tApp, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TApp tApp, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tApp, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TApp tApp, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        return sqlParams;
	}
}