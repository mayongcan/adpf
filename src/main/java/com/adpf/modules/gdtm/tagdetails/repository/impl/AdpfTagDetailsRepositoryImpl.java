/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.tagdetails.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.gdtm.tagdetails.entity.AdpfTagDetails;
import com.adpf.modules.gdtm.tagdetails.repository.custom.AdpfTagDetailsRepositoryCustom;

public class AdpfTagDetailsRepositoryImpl extends BaseRepository implements AdpfTagDetailsRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.PROVIENCES as \"proviences\", tb.EQUIPMENT_CODE as \"equipmentCode\", tb.PHONE_NUM as \"phoneNum\", tb.VISITS as \"visits\", tb.APP_ID as \"appId\", tl.label as\"appName\" "
			+ "FROM adpf_tag_details tb inner join adpf_tag_library tl on tb.APP_ID=tl.tag_id "
			+ "WHERE 1 = 1";


	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_tag_details tb "
			+ "WHERE 1 = 1 ";
	
	//private static final String SQL_GET_APPNAME="SELECT tag_id as \"""
	
	public List<Map<String, Object>> getList(AdpfTagDetails adpfTagDetails, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfTagDetails, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfTagDetails adpfTagDetails, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfTagDetails, params);
		return getResultListTotalCount(sqlParams);
	}
	
	

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfTagDetails adpfTagDetails   , Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        return sqlParams;
	}
}