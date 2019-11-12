/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.collection.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.collection.entity.Collection;
import com.adpf.collection.repository.custom.CollectionRepositoryCustom;

public class CollectionRepositoryImpl extends BaseRepository implements CollectionRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.APKNAME as \"apkname\", tb.NAME as \"name\",tb.IMGPATH as \"imgpath\",tb.APKSIZE as \"apksize\",tb.INSTALLURL as \"installurl\",tb.CATEID as \"cateid\" "
			+ "FROM collection tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM collection tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(Collection collection, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, collection, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(Collection collection, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, collection, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, Collection collection, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (collection != null && collection.getCateid() != null) {
			sqlParams.querySql.append(" AND tb.CATEID = :cateid ");
			sqlParams.paramsList.add("cateid");
			sqlParams.valueList.add(collection.getCateid());
		}
		
        return sqlParams;
	}
}