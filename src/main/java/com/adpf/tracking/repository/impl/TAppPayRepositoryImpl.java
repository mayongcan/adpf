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

import com.adpf.tracking.entity.TAppPay;
import com.adpf.tracking.repository.custom.TAppPayRepositoryCustom;

public class TAppPayRepositoryImpl extends BaseRepository implements TAppPayRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.when1 as \"when1\", tb.app_id as \"appId\", tb.app_key as \"appKey\", tb.app_type as \"appType\", tb.android_id as \"androidId\", tb.idfa as \"idfa\", tb.ip as \"ip\", tb.ua as \"ua\", tb.imei as \"imei\", tb.mac as \"mac\", tb.account_id as \"accountId\", tb.order_id as \"orderId\", tb.pay_type as \"payType\", tb.currency_type as \"currencyType\", tb.free as \"free\", tb.create_time as \"createTime\" "
			+ "FROM t_app_pay tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM t_app_pay tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TAppPay tAppPay, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tAppPay, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TAppPay tAppPay, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tAppPay, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TAppPay tAppPay, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        return sqlParams;
	}
}