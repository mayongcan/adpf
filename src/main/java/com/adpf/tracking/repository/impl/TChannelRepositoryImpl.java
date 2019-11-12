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

import com.adpf.tracking.entity.TChannel;
import com.adpf.tracking.repository.custom.TChannelRepositoryCustom;

public class TChannelRepositoryImpl extends BaseRepository implements TChannelRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.name as \"name\", tb.device_type as \"deviceType\", tb.click_macro as \"clickMacro\", tb.is_callback as \"isCallback\", tb.callback_event as \"callbackEvent\", tb.callback_macro as \"callbackMacro\", tb.create_time as \"createTime\" "
			+ "FROM t_channel tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM t_channel tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TChannel tChannel, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tChannel, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TChannel tChannel, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tChannel, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TChannel tChannel, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        return sqlParams;
	}
}