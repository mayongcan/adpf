/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import com.adpf.modules.openam.util.StringUtil;
import com.adpf.qtn.entity.QtnWechatInfo;
import com.adpf.qtn.repository.custom.QtnWechatInfoRepositoryCustom;
import com.alibaba.fastjson.JSONObject;

public class QtnWechatInfoRepositoryImpl extends BaseRepository implements QtnWechatInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.WECHAT_ID as \"wechatId\", tb.OPEN_ID as \"openId\", tb.NAME as \"name\", tb.PROVINCE as \"province\", tb.CITY as \"city\", tb.COUNTRY as \"country\", tb.CREATE_DATE as \"createDate\" "
			+ "FROM qtn_wechat_info tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM qtn_wechat_info tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(QtnWechatInfo qtnWechatInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, qtnWechatInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.WECHAT_ID DESC ", " \"wechatId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(QtnWechatInfo qtnWechatInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, qtnWechatInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, QtnWechatInfo qtnWechatInfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(StringUtil.isNotEmpty((String)params.get("openId"))) {
			sqlParams.querySql.append(" AND tb.OPEN_ID  = :openId ");
			sqlParams.paramsList.add("openId");
			sqlParams.valueList.add(params.get("openId"));
		}
        return sqlParams;
	}

	@Override
	public  List<Map<String, Object>> findOneByOpenId(Map<String, Object> params) {
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, new QtnWechatInfo(), params);
		return getResultList(sqlParams);
	}
}