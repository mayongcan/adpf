/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.oem.repository.impl;

import com.adpf.oem.entity.AdvertisingOem;
import com.adpf.oem.repository.custom.AdvertisingOemRepositoryCustom;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.List;
import java.util.Map;

public class AdvertisingOemRepositoryImpl extends BaseRepository implements AdvertisingOemRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.oem_name as \"oemName\", tb.oem_linkman as \"oemLinkman\", tb.oem_phone as \"oemPhone\", tb.oem_loginid as \"oemLoginid\", tb.oem_password as \"oemPassword\", tb.oem_state as \"oemState\", tb.oem_cost as \"oemCost\", tb.oem_role as \"oemRole\" "
			+ "FROM advertising_oem tb "
			+ "WHERE 1 = 1 ";
	private static final String SQL_GET_ROLR_LIST = "SELECT  tb.oem_role as \"oemRole\" "
			+ "FROM advertising_oem tb ";


	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM advertising_oem tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(AdvertisingOem advertisingOem, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = getListWhere(SQL_GET_LIST, advertisingOem, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdvertisingOem advertisingOem, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = getListWhere(SQL_GET_LIST_COUNT, advertisingOem, params);
		return getResultListTotalCount(sqlParams);
	}

	@Override
	public List<Map<String, Object>> getRoleList(String id) {
        SqlParams sqlParams = getListByid(SQL_GET_ROLR_LIST, id);
        return getResultList(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams getListWhere(String sql, AdvertisingOem advertisingOem, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (MapUtils.getString(params,"creator") != null && !StringUtils.isBlank(MapUtils.getString(params,"creator"))) {
			sqlParams.querySql.append(getLikeSql("tb.orm_creator", ":creator"));
			sqlParams.paramsList.add("creator");
			sqlParams.valueList.add(MapUtils.getString(params,"creator"));
		}
        return sqlParams;
	}


	/**
	 * 根据id查询role
	 * @param sql
	 * @param id
	 * @return
	 */
	private SqlParams getListByid(String sql,String id){
		SqlParams sqlParams = new SqlParams();
		sql=sql+"where tb.id=\""+(id)+"\"";
		sqlParams.querySql.append(sql);
		return sqlParams;
	}
}