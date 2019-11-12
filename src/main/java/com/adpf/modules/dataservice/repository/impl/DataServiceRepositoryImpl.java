/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.dataservice.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.dataservice.entity.DataService;
import com.adpf.modules.dataservice.repository.custom.DataServiceRepositoryCustom;

public class DataServiceRepositoryImpl extends BaseRepository implements DataServiceRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.TEL as \"tel\", tb.TIMES as \"times\", tb.ORDERS as \"orders\" "
			+ "FROM data_service tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM data_service tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(DataService dataService, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, dataService, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(DataService dataService, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, dataService, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, DataService dataService, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (dataService != null && !StringUtils.isBlank(dataService.getOrders())) {
			sqlParams.querySql.append(" AND tb.ORDERS = :orders ");
			sqlParams.paramsList.add("orders");
			sqlParams.valueList.add(dataService.getOrders());
		}
        return sqlParams;
	}
}