/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.crowd.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import com.adpf.modules.crowd.entity.DmpCrowdInfo;
import com.adpf.modules.crowd.repository.custom.DmpCrowdInfoRepositoryCustom;
import com.alibaba.fastjson.JSONObject;

public class DmpCrowdInfoRepositoryImpl extends BaseRepository implements DmpCrowdInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.CROWD_ID as \"crowdId\", tb.CROWD_NAME as \"crowdName\", tb.STATUS as \"status\", tb.USABLE as \"usable\", tb.CREATE_DATE as \"createDate\", tb.CROWD_DETAILS as \"crowdDetails\",tb.TXT_TYPE as \"txtType\",tb.TXT_PATH as \"txtPath\","
			+ "tb.IS_JOIN as \"isJoin\",tb.TXT_SIZE as \"txtSize\", tb.TXT_SUM as \"txtSum\" "
			+ "FROM dmp_crowd_info tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM dmp_crowd_info tb "
			+ "WHERE 1 = 1 ";
	
	
	public List<Map<String, Object>> getList(DmpCrowdInfo dmpCrowdInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, dmpCrowdInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.CROWD_ID DESC ", " \"crowdId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(DmpCrowdInfo dmpCrowdInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, dmpCrowdInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 获取DmpCrowdInfo中文件未读取保存的列表
	 * 
	 */
	public List<Map<String,Object>> getNotJoinList(DmpCrowdInfo dmpCrowdInfo,Map<String,Object>params){
		SqlParams sqlParams = genListWhere(SQL_GET_LIST,dmpCrowdInfo,params);
		return getResultList(sqlParams);
	};
	
	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, DmpCrowdInfo dmpCrowdInfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if( null != params.get("isJoin")) {
			sqlParams.querySql.append(" AND tb.IS_JOIN = :isJoin");
			sqlParams.paramsList.add("isJoin");
			sqlParams.valueList.add(params.get("isJoin"));
		}
		if(null != params.get("crowdId")) {
			sqlParams.querySql.append(" AND tb.CROWD_ID = :crowdId ");
			sqlParams.paramsList.add("crowdId");
			sqlParams.valueList.add(params.get("crowdId"));
		}
        return sqlParams;
	}

	@Override
	public List<Map<String,Object>> selectByCrowdId(DmpCrowdInfo dmpCrowdInfo,Map<String,Object>params) {
		SqlParams sqlParams = genListWhere(SQL_GET_LIST,dmpCrowdInfo,params);
		return getResultList(sqlParams);
		
	}
}