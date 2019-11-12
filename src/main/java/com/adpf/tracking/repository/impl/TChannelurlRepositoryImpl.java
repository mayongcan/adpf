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

import com.adpf.tracking.entity.TChannelurl;
import com.adpf.tracking.repository.custom.TChannelurlRepositoryCustom;

public class TChannelurlRepositoryImpl extends BaseRepository implements TChannelurlRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.NAME as \"name\", tb.CAHNNEL_TYPE as \"cahnnelType\", tb.CHANNEL_URL as \"channelUrl\", tb.CREATE_TIME as \"createTime\" "
			+ "FROM t_channelurl tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM t_channelurl tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TChannelurl tChannelurl, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tChannelurl, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TChannelurl tChannelurl, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tChannelurl, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TChannelurl tChannelurl, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (tChannelurl != null && !StringUtils.isBlank(tChannelurl.getName())) {
            sqlParams.querySql.append(getLikeSql("tb.NAME", ":name"));
			sqlParams.paramsList.add("name");
			sqlParams.valueList.add(tChannelurl.getName());
		}
        return sqlParams;
	}
}