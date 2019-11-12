/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.tagcat.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.gdtm.tagcat.entity.AdpfTagCategory;
import com.adpf.modules.gdtm.tagcat.repository.custom.AdpfTagCategoryRepositoryCustom;

public class AdpfTagCategoryRepositoryImpl extends BaseRepository implements AdpfTagCategoryRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.NAME as \"name\", tb.PARENT_ID as \"parentId\", tb.DISP_ORDER as \"dispOrder\" "
			+ "FROM adpf_tag_category tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_tag_category tb "
			+ "WHERE 1 = 1 ";
	
	private static final String SQL_GET_LIST_ID = "select tb.tag_id as \"tagId\",tb.cat_id as \"catId\" from adpf_tag_bindings tb where 1 = 1 ";
	
	private static final String SQL_GET_LIST_TAG = "select tc.name as \"name\",tl.id as\"id\",tl.tag_id as \"tag_id\",tl.label as \"label\" " 
			+ "from adpf_tag_category tc "  
			+ "inner join adpf_tag_bindings tb on tc.ID=tb.cat_id " 
			+ "inner join adpf_tag_library tl on tb.tag_id = tl.id "
			+ "WHERE 1 = 1 ";
	
	private static final String SQL_GET_LIST_COUNT_TAG = "select count(1) as \"count\" " 
			+ "from adpf_tag_category tc "  
			+ "inner join adpf_tag_bindings tb on tc.ID=tb.cat_id "  
			+ "inner join adpf_tag_library tl on tb.tag_id = tl.id "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(AdpfTagCategory adpfTagCategory, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfTagCategory, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}
	
	public List<Map<String, Object>> getMonth(AdpfTagCategory adpfTagCategory, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListMonth(SQL_GET_LIST_ID, adpfTagCategory, params);
		//添加分页和排序
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfTagCategory adpfTagCategory, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfTagCategory, params);
		return getResultListTotalCount(sqlParams);
	}
	
	
	public List<Map<String, Object>> getListTag(AdpfTagCategory adpfTagCategory, Map<String, Object> params,
			int pageIndex, int pageSize) {
		//生成查询条件
				SqlParams sqlParams = genListTagWhere(SQL_GET_LIST_TAG, adpfTagCategory, params);
				//添加分页和排序
				sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tc.ID DESC ", " \"id\" DESC ");
				return getResultList(sqlParams);
	}

	public int getListCountTag(AdpfTagCategory adpfTagCategory, Map<String, Object> params) {
		//生成查询条件
				SqlParams sqlParams = genListTagWhere(SQL_GET_LIST_COUNT_TAG, adpfTagCategory, params);
				return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfTagCategory adpfTagCategory, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        return sqlParams;
	}
	
	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListTagWhere(String sql, AdpfTagCategory adpfTagCategory, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(true) {
			sqlParams.querySql.append(" and tc.id= :id");
			sqlParams.paramsList.add("id");
			sqlParams.valueList.add(params.get("cat_id"));
		}
		if (!"".equals( params.get("label"))) {
			sqlParams.querySql.append(" and tl.label= :label");
			sqlParams.paramsList.add("label");
			sqlParams.valueList.add(params.get("label"));
		}
        return sqlParams;
	}
	
	private SqlParams genListMonth(String sql, AdpfTagCategory adpfTagCategory, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(true) {
			sqlParams.querySql.append(" and tb.cat_id = :catId");
			sqlParams.paramsList.add("catId");
			sqlParams.valueList.add(params.get("catId"));
		}
        return sqlParams;
	}
}