/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.cms.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.cms.entity.AdpfCmsCategory;
import com.adpf.modules.cms.repository.custom.AdpfCmsCategoryRepositoryCustom;

public class AdpfCmsCategoryRepositoryImpl extends BaseRepository implements AdpfCmsCategoryRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.CATEGORY_ID as \"categoryId\", tb.CATEGORY_NAME as \"categoryName\", tb.CATEGORY_DESC as \"categoryDesc\", tb.CATEGORY_TIME as \"categoryTime\", tb.USER_ID as \"userId\", tb.CATEGORY_MTIME as \"categoryMtime\",u.user_code as \"userCode\" "
			+ "FROM adpf_cms_category tb LEFT JOIN sys_user_info u on u.user_id = tb.USER_ID "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_cms_category tb "
			+ "WHERE 1 = 1 ";
	
	private static final String SQL_GET_SIMPLE_LIST="SELECT tb.CATEGORY_ID as \"categoryId\", tb.CATEGORY_NAME as \"categoryName\", tb.CATEGORY_DESC as \"categoryDesc\", tb.CATEGORY_TIME as \"categoryTime\", tb.USER_ID as \"userId\", tb.CATEGORY_MTIME as \"categoryMtime\" "
			+ "FROM adpf_cms_category tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(AdpfCmsCategory adpfCmsCategory, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfCmsCategory, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.CATEGORY_ID DESC ", " \"categoryId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfCmsCategory adpfCmsCategory, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfCmsCategory, params);
		return getResultListTotalCount(sqlParams);
	}
	
	public List<Map<String, Object>> getSimList(AdpfCmsCategory adpfCmsCategory, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_SIMPLE_LIST, adpfCmsCategory, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.CATEGORY_ID DESC ", " \"categoryId\" DESC ");
		return getResultList(sqlParams);
	}
	

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfCmsCategory adpfCmsCategory, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (adpfCmsCategory != null && !StringUtils.isBlank(adpfCmsCategory.getCategoryName())) {
            sqlParams.querySql.append(getLikeSql("tb.CATEGORY_NAME", ":categoryName"));
			sqlParams.paramsList.add("categoryName");
			sqlParams.valueList.add(adpfCmsCategory.getCategoryName());
		}
		if (!StringUtils.isBlank(MapUtils.getString(params, "categoryTimeBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "categoryTimeEnd"))) {
			sqlParams.querySql.append(" AND tb.CATEGORY_TIME between :categoryTimeBegin AND :categoryTimeEnd ");
			sqlParams.paramsList.add("categoryTimeBegin");
			sqlParams.paramsList.add("categoryTimeEnd");
			sqlParams.valueList.add(MapUtils.getString(params, "categoryTimeBegin"));
			sqlParams.valueList.add(MapUtils.getString(params, "categoryTimeEnd"));
		}
        return sqlParams;
	}
}