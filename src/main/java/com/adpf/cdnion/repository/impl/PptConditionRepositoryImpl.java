/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.cdnion.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.cdnion.entity.PptCondition;
import com.adpf.cdnion.repository.custom.PptConditionRepositoryCustom;

public class PptConditionRepositoryImpl extends BaseRepository implements PptConditionRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.cdn_name as \"cdnName\", tb.media_name as \"mediaName\", tb.data_data as \"dataData\", tb.chart_url as \"chartUrl\", tb.video_name as \"videoName\", tb.user_name as \"userName\", tb.create_time as \"createTime\" "
			+ "FROM ppt_condition tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM ppt_condition tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_BYID = "SELECT tb.id as \"id\", tb.cdn_name as \"cdnName\", tb.media_name as \"mediaName\", tb.data_data as \"dataData\", tb.chart_url as \"chartUrl\", tb.video_name as \"videoName\", tb.user_name as \"userName\", tb.create_time as \"createTime\" "
			+ "FROM ppt_condition tb ";

	
	public List<Map<String, Object>> getList(PptCondition pptCondition, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, pptCondition, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(PptCondition pptCondition, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, pptCondition, params);
		return getResultListTotalCount(sqlParams);
	}

	@Override
	public List<Map<String, Object>> getListByid(String id) {

		SqlParams sqlParams = getListByid(SQL_GET_LIST_BYID,id);
		return getResultList(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, PptCondition pptCondition, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        return sqlParams;
	}

	/**
	 * 根据id查询
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