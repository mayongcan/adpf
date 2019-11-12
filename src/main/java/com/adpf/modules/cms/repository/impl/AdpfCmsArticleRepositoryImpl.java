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

import com.adpf.modules.cms.entity.AdpfCmsArticle;
import com.adpf.modules.cms.repository.custom.AdpfCmsArticleRepositoryCustom;

public class AdpfCmsArticleRepositoryImpl extends BaseRepository implements AdpfCmsArticleRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ARTICLE_ID as \"articleId\", tb.ARTICLE_TITLE as \"articleTitle\", tb.ARTICLE_IMAGE as \"articleImage\", tb.ARTICLE_TIME as \"articleTime\", tb.ARTICLE_STATUS as \"articleStatus\", tb.ARTICLE_CLICK as \"articleClick\", tb.ARTICLE_CONTENT as \"articleContent\", tb.CATEGORY_ID as \"categoryId\", tb.USER_ID as \"userId\", tb.ARTICLE_MTIME as \"articleMtime\", ct.CATEGORY_Name as \"categoryName\", u.user_code as \"userCode\" "
			+ "FROM (adpf_cms_article tb left join adpf_cms_category ct on ct.CATEGORY_ID = tb.CATEGORY_ID) LEFT JOIN sys_user_info u on u.user_id = tb.USER_ID "
			+ "WHERE 1 = 1 ";
	
	private static final String SQL_GET_WEB_LIST = "SELECT tb.ARTICLE_ID as \"articleId\", tb.ARTICLE_TITLE as \"articleTitle\", tb.ARTICLE_IMAGE as \"articleImage\", tb.ARTICLE_TIME as \"articleTime\", tb.ARTICLE_STATUS as \"articleStatus\", tb.ARTICLE_CLICK as \"articleClick\", tb.ARTICLE_CONTENT as \"articleContent\", tb.CATEGORY_ID as \"categoryId\", tb.USER_ID as \"userId\", tb.ARTICLE_MTIME as \"articleMtime\", ct.CATEGORY_Name as \"categoryName\", u.user_code as \"userCode\" "
			+ "FROM (adpf_cms_article tb left join adpf_cms_category ct on ct.CATEGORY_ID = tb.CATEGORY_ID) LEFT JOIN sys_user_info u on u.user_id = tb.USER_ID "
			+ "WHERE tb.ARTICLE_STATUS = 1 AND 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_cms_article tb "
			+ "WHERE 1 = 1 ";
	
	private static final String SQL_GET_LIST_WEB_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_cms_article tb "
			+ "WHERE tb.ARTICLE_STATUS = 1 AND 1 = 1 ";
	
	public List<Map<String, Object>> getList(AdpfCmsArticle adpfCmsArticle, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfCmsArticle, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ARTICLE_ID DESC ", " \"articleId\" DESC ");
		return getResultList(sqlParams);
	}
	
	public List<Map<String, Object>> getWebList(AdpfCmsArticle adpfCmsArticle, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_WEB_LIST, adpfCmsArticle, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ARTICLE_ID DESC ", " \"articleId\" DESC ");
		return getResultList(sqlParams);
	}
	
	public List<Map<String, Object>> getWebIdList(AdpfCmsArticle adpfCmsArticle,Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genIdListWhere(SQL_GET_WEB_LIST,adpfCmsArticle, params);
		//添加分页和排序
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfCmsArticle adpfCmsArticle, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfCmsArticle, params);
		return getResultListTotalCount(sqlParams);
	}
	
	public int getWebListCount(AdpfCmsArticle adpfCmsArticle, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_WEB_COUNT, adpfCmsArticle, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfCmsArticle adpfCmsArticle, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (adpfCmsArticle != null && !StringUtils.isBlank(adpfCmsArticle.getArticleTitle())) {
            sqlParams.querySql.append(getLikeSql("tb.ARTICLE_TITLE", ":articleTitle"));
			sqlParams.paramsList.add("articleTitle");
			sqlParams.valueList.add(adpfCmsArticle.getArticleTitle());
		}
		if (!StringUtils.isBlank(MapUtils.getString(params, "articleTimeBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "articleTimeEnd"))) {
			sqlParams.querySql.append(" AND tb.ARTICLE_TIME between :articleTimeBegin AND :articleTimeEnd ");
			sqlParams.paramsList.add("articleTimeBegin");
			sqlParams.paramsList.add("articleTimeEnd");
			sqlParams.valueList.add(MapUtils.getString(params, "articleTimeBegin"));
			sqlParams.valueList.add(MapUtils.getString(params, "articleTimeEnd"));
		}
		if(null != params.get("categoryId")) {
			sqlParams.querySql.append(" AND tb.CATEGORY_ID = :categoryId ");
			sqlParams.paramsList.add("categoryId");
			sqlParams.valueList.add(params.get("categoryId"));
		}
        return sqlParams;
	}
	private SqlParams genIdListWhere(String sql,AdpfCmsArticle adpfCmsArticle,Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        sqlParams.querySql.append(getLikeSql("tb.ARTICLE_ID", ":articleId"));
		sqlParams.paramsList.add("articleId");
		sqlParams.valueList.add(adpfCmsArticle.getArticleId());
        return sqlParams;
	}
}