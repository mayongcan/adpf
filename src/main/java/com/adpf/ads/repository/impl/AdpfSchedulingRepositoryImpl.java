/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.ads.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.ads.entity.AdpfScheduling;
import com.adpf.ads.repository.custom.AdpfSchedulingRepositoryCustom;

public class AdpfSchedulingRepositoryImpl extends BaseRepository implements AdpfSchedulingRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.SCHEDULING_ID as \"schedulingId\", tb.PUT_BEGIN_DATE as \"putBeginDate\", tb.PUT_END_DATE as \"putEndDate\", tb.PUT_BEGIN_HOUR as \"putBeginHour\", tb.PUT_END_HOUR as \"putEndHour\", tb.OFFER_STATE as \"offerState\", tb.OFFER as \"offer\", tb.ADVERTISEMENT_NAME as \"advertisementName\" "
			+ "FROM adpf_scheduling tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_scheduling tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(AdpfScheduling adpfScheduling, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfScheduling, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.SCHEDULING_ID DESC ", " \"schedulingId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfScheduling adpfScheduling, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfScheduling, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfScheduling adpfScheduling, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		/*if (adpfScheduling != null && adpfScheduling.getAdvertisementName() != null) {
			sqlParams.querySql.append(" AND tb.ADVERTISEMENT_NAME = :advertisementName ");
			sqlParams.paramsList.add("advertisementName");
			sqlParams.valueList.add(adpfScheduling.getAdvertisementName());
		}*/
        return sqlParams;
	}
}