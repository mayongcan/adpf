/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.crowd.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;

import com.adpf.modules.crowd.entity.DmpCrowdNumberInfo;
import com.adpf.modules.crowd.repository.custom.DmpCrowdNumberInfoRepositoryCustom;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;


public class DmpCrowdNumberInfoRepositoryImpl extends BaseRepository implements DmpCrowdNumberInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.Id as \"id\", tb.NUMBER as \"number\",tb.CROWD_ID as \"crowdId\" "
			+ "FROM dmp_crowd_number_info tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM dmp_crowd_number_info tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(DmpCrowdNumberInfo dmpCrowdNumberInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, dmpCrowdNumberInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(DmpCrowdNumberInfo dmpCrowdNumberInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, dmpCrowdNumberInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, DmpCrowdNumberInfo dmpCrowdNumberInfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(params.get("crowdId") != null) {
			sqlParams.querySql.append( "AND tb.CROWD_ID = :crowdId");
			sqlParams.paramsList.add("crowdId");
			sqlParams.valueList.add(params.get("crowdId"));
		}
        return sqlParams;
	}

}