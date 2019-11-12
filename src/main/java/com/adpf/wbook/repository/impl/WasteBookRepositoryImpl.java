/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.wbook.repository.impl;

import com.adpf.wbook.entity.WasteBook;
import com.adpf.wbook.repository.custom.WasteBookRepositoryCustom;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.List;
import java.util.Map;

public class WasteBookRepositoryImpl extends BaseRepository implements WasteBookRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.adv_id as \"advId\", tb.operator as \"operator\", tb.operation_time as \"operationTime\", tb.income as \"income\", tb.disbursement as \"disbursement\", tb.remark as \"remark\", adv.advertisers_name as \"advName\" "
			+ "FROM waste_book tb "
			+ "WHERE 1 = 1 ";

	private static String SQL_GET_LIST_BY_ADVID="SELECT tb.id as \"id\", tb.adv_id as \"advId\", tb.operator as \"operator\", tb.operation_time as \"operationTime\", tb.income as \"income\", tb.disbursement as \"disbursement\", tb.remark as \"remark\", adv.advertisers_name as \"advName\" "
			+"from waste_book tb LEFT JOIN sys_user_info u on tb.adv_id = u.USER_ID LEFT JOIN advertisers adv ON u.USER_CODE=adv.adv_loginid "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+"from waste_book tb LEFT JOIN sys_user_info u on tb.adv_id = u.USER_ID LEFT JOIN advertisers adv ON u.USER_CODE=adv.adv_loginid "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(WasteBook wasteBook, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_BY_ADVID, wasteBook, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(WasteBook wasteBook, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, wasteBook, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, WasteBook wasteBook, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		String advId = params.get("advid")+"";
		if (params.get("differenceid").toString() != null && !StringUtils.isBlank(params.get("differenceid").toString())) {
			sqlParams.querySql.append("and adv.differenceid = :differenceid ");
			sqlParams.paramsList.add("differenceid");
			sqlParams.valueList.add(params.get("differenceid").toString());
		}
		if (advId!=null&&!com.adpf.modules.gdtm.util.StringUtils.isBlank(advId)) {
			sqlParams.querySql.append("or tb.adv_id = :advid");
			sqlParams.paramsList.add("advid");
			sqlParams.valueList.add(advId);
		}
		if (!StringUtils.isBlank(MapUtils.getString(params, "operationTimeBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "operationTimeEnd"))) {
			sqlParams.querySql.append(" AND tb.operation_time between :operationTimeBegin AND :operationTimeEnd ");
			sqlParams.paramsList.add("operationTimeBegin");
			sqlParams.paramsList.add("operationTimeEnd");
			sqlParams.valueList.add(MapUtils.getString(params, "operationTimeBegin"));
			sqlParams.valueList.add(MapUtils.getString(params, "operationTimeEnd"));
		}

        return sqlParams;
	}
}