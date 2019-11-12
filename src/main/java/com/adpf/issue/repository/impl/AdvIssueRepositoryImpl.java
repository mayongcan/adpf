/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.issue.repository.impl;

import com.adpf.issue.entity.AdvIssue;
import com.adpf.issue.repository.custom.AdvIssueRepositoryCustom;
import com.adpf.modules.gdtm.util.StringUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;

import java.util.List;
import java.util.Map;

public class AdvIssueRepositoryImpl extends BaseRepository implements AdvIssueRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.adv_id as \"advId\", tb.amount as \"amount\", tb.creat_time as \"creatTime\", tb.creator as \"creator\", tb.remark as \"remark\", adv.advertisers_name as \"advName\" "
			+ "from adv_issue tb LEFT JOIN sys_user_info u on tb.adv_id = u.USER_ID LEFT JOIN advertisers adv ON u.USER_CODE=adv.adv_loginid "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "from adv_issue tb LEFT JOIN sys_user_info u on tb.adv_id = u.USER_ID LEFT JOIN advertisers adv ON u.USER_CODE=adv.adv_loginid "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(AdvIssue advIssue, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, advIssue, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdvIssue advIssue, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, advIssue, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdvIssue advIssue, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		String advId = params.get("advid")+"";
		if (params.get("differenceid").toString() != null && !StringUtils.isBlank(params.get("differenceid").toString())) {
			sqlParams.querySql.append("and adv.differenceid = :differenceid ");
			sqlParams.paramsList.add("differenceid");
			sqlParams.valueList.add(params.get("differenceid").toString());
		}
		if (advId!=null&&!StringUtils.isBlank(advId)) {
			sqlParams.querySql.append("or tb.adv_id = :advid");
			sqlParams.paramsList.add("advid");
			sqlParams.valueList.add(advId);
		}
		return sqlParams;

	}
}