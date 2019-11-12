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

import com.adpf.tracking.entity.TPromo;
import com.adpf.tracking.repository.custom.TPromoRepositoryCustom;

public class TPromoRepositoryImpl extends BaseRepository implements TPromoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.name as \"name\", tb.channel_name as \"channelName\", tb.anent_name as \"anentName\", tb.app_name as \"appName\", tb.click_url as \"clickUrl\", tb.show_url as \"showUrl\",tb.landing_url as \"landingUrl\", tb.create_time as \"createTime\" "
			+ "FROM t_promo tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM t_promo tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TPromo tPromo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tPromo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TPromo tPromo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tPromo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TPromo tPromo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (tPromo != null && tPromo.getChannelId() != null) {
			sqlParams.querySql.append(" AND tb.channel_id = :channelId ");
			sqlParams.paramsList.add("channelId");
			sqlParams.valueList.add(tPromo.getChannelId());
		}
		if (tPromo != null && tPromo.getAnentId() != null) {
			sqlParams.querySql.append(" AND tb.anent_id = :anentId ");
			sqlParams.paramsList.add("anentId");
			sqlParams.valueList.add(tPromo.getAnentId());
		}
		if (tPromo != null && tPromo.getAppId() != null) {
			sqlParams.querySql.append(" AND tb.app_id = :appId ");
			sqlParams.paramsList.add("appId");
			sqlParams.valueList.add(tPromo.getAppId());
		}
        return sqlParams;
	}
}