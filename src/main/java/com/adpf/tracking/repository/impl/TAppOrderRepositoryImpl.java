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

import com.adpf.tracking.entity.TAppOrder;
import com.adpf.tracking.repository.custom.TAppOrderRepositoryCustom;

public class TAppOrderRepositoryImpl extends BaseRepository implements TAppOrderRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.when1 as \"when1\", tb.app_id as \"appId\", tb.app_key as \"appKey\", tb.app_type as \"appType\", tb.android_id as \"androidId\", tb.idfa as \"idfa\", tb.ip as \"ip\", tb.imei as \"imei\", tb.mac as \"mac\", tb.account_id as \"accountId\", tb.order_id as \"orderId\", tb.pay_type as \"payType\", tb.fee as \"fee\", tb.create_time as \"createTime\",ta.name as \"name\" "
			+ "FROM t_app_order tb LEFT JOIN  t_app ta ON tb.app_id = ta.id "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM t_app_order tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TAppOrder tAppOrder, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tAppOrder, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TAppOrder tAppOrder, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tAppOrder, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TAppOrder tAppOrder, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (tAppOrder != null && !StringUtils.isBlank(tAppOrder.getIp())) {
			sqlParams.querySql.append(" AND tb.ip = :ip ");
			sqlParams.paramsList.add("ip");
			sqlParams.valueList.add(tAppOrder.getIp());
		}
		if (tAppOrder != null && !StringUtils.isBlank(tAppOrder.getImei())) {
			sqlParams.querySql.append(" AND tb.imei = :imei ");
			sqlParams.paramsList.add("imei");
			sqlParams.valueList.add(tAppOrder.getImei());
		}
        return sqlParams;
	}
}