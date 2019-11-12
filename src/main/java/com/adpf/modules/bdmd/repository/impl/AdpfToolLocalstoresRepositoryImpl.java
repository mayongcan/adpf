/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.bdmd.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.bdmd.entity.AdpfToolLocalstores;
import com.adpf.modules.bdmd.repository.custom.AdpfToolLocalstoresRepositoryCustom;

public class AdpfToolLocalstoresRepositoryImpl extends BaseRepository implements AdpfToolLocalstoresRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.STORE_ID as \"storeId\", tb.STORE_NAME as \"storeName\", tb.CREATE_TIME as \"createTime\", tb.STORE_ADDRESS as \"storeAddress\", tb.STORE_PHONE as \"storePhone\", tb.STORE_INDUSTRY as \"storeIndustry\", tb.STORE_GARDEN as \"storeGarden\", tb.BUSINESSTIME as \"businesstime\", tb.STOREPRICE as \"storeprice\", tb.STOREFEATURES as \"storefeatures\" "
			+ "FROM adpf_tool_localstores tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_tool_localstores tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(AdpfToolLocalstores adpfToolLocalstores, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfToolLocalstores, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.STORE_ID DESC ", " \"storeId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfToolLocalstores adpfToolLocalstores, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfToolLocalstores, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfToolLocalstores adpfToolLocalstores, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (adpfToolLocalstores != null && !StringUtils.isBlank(adpfToolLocalstores.getStoreName())) {
            sqlParams.querySql.append(getLikeSql("tb.STORE_NAME", ":storeName"));
			sqlParams.paramsList.add("storeName");
			sqlParams.valueList.add(adpfToolLocalstores.getStoreName());
		}
		if (adpfToolLocalstores != null && !StringUtils.isBlank(adpfToolLocalstores.getStoreIndustry())) {
			sqlParams.querySql.append(" AND tb.STORE_INDUSTRY = :storeIndustry ");
			sqlParams.paramsList.add("storeIndustry");
			sqlParams.valueList.add(adpfToolLocalstores.getStoreIndustry());
		}
        return sqlParams;
	}
}