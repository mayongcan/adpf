/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tuiguang.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.tuiguang.entity.UserTuiguang;
import com.adpf.tuiguang.repository.custom.UserTuiguangRepositoryCustom;

public class UserTuiguangRepositoryImpl extends BaseRepository implements UserTuiguangRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.tuiguang_id as \"tuiguangId\", tb.tuiguang_name as \"tuiguangName\", tb.tuiguang_lianjiedizhi as \"tuiguangLianjiedizhi\", tb.tuiguang_disanfangdianji as \"tuiguangDisanfangdianji\", tb.tuiguang_disanfangbaoguang as \"tuiguangDisanfangbaoguang\", tb.tuiguang_chuangjianshijian as \"tuiguangChuangjianshijian\", tb.tuiguang_gengxinshijian as \"tuiguangGengxinshijian\" "
			+ "FROM user_tuiguang tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM user_tuiguang tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(UserTuiguang userTuiguang, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, userTuiguang, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.tuiguang_id DESC ", " \"tuiguangId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(UserTuiguang userTuiguang, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, userTuiguang, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, UserTuiguang userTuiguang, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (userTuiguang != null && !StringUtils.isBlank(userTuiguang.getTuiguangName())) {
            sqlParams.querySql.append(getLikeSql("tb.tuiguang_name", ":tuiguangName"));
			sqlParams.paramsList.add("tuiguangName");
			sqlParams.valueList.add(userTuiguang.getTuiguangName());
		}
        return sqlParams;
	}
}