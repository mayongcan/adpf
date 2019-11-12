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

import com.adpf.tracking.entity.Ick;
import com.adpf.tracking.repository.custom.IckRepositoryCustom;

public class IckRepositoryImpl extends BaseRepository implements IckRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tpo.name as \"promoName\", tcl.name as \"channelName\", tat.name as \"agentName\", tap.name as \"appName\", tb.app_type as \"appType\", tb.android_id as \"androidid\", tb.idfa as \"idfa\", tb.ip as \"ip\", tb.ua as \"ua\", tb.imei as \"imei\", tb.mac as \"mac\", tb.callback_url as \"callback\", tb.create_time as \"createTime\", tb.android_id_md5 as \"androidid_md5\", tb.imei_md5 as \"imei_md5\" "
			+ "FROM t_click tb LEFT JOIN t_promo tpo ON tb.promo_id = tpo.id LEFT JOIN t_channelurl tcl ON tb.channel_id = tcl.id LEFT JOIN t_agent tat ON tb.agent_id = tat.id LEFT JOIN t_app tap ON tb.app_id = tap.id "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM t_click tb "
			+ "WHERE 1 = 1 ";
	 
	public List<Map<String, Object>> getList(Ick ick, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, ick, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(Ick ick, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, ick, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, Ick ick, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        return sqlParams;
	}
}