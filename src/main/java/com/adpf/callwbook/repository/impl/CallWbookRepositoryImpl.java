/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.callwbook.repository.impl;

import com.adpf.callwbook.entity.CallWbook;
import com.adpf.callwbook.repository.custom.CallWbookRepositoryCustom;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.List;
import java.util.Map;

public class CallWbookRepositoryImpl extends BaseRepository implements CallWbookRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.adv_id as \"advId\", tb.seat_id as \"seatId\", tb.call_id as \"callId\", tb.call_time as \"callTime\", tb.date_time as \"dateTime\", tb.call_price as \"callPrice\", adv.advertisers_name as \"advName\" "
			+ "FROM call_wbook tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "from call_wbook tb LEFT JOIN sys_user_info u ON tb.adv_id = u.USER_ID LEFT JOIN advertisers adv on u.USER_CODE=adv.adv_loginid "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_BYADVID = "SELECT tb.id as \"id\", tb.adv_id as \"advId\", tb.seat_id as \"seatId\", tb.call_id as \"callId\", tb.call_time as \"callTime\", tb.date_time as \"dateTime\", tb.call_price as \"callPrice\", adv.advertisers_name as \"advName\" "
			+ "from call_wbook tb LEFT JOIN sys_user_info u ON tb.adv_id = u.USER_ID LEFT JOIN advertisers adv on u.USER_CODE=adv.adv_loginid "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_FILST_LIST = "SELECT tb.id as \"id\", tb.adv_id as \"advId\", tb.seat_id as \"seatId\", tb.call_id as \"callId\", tb.call_time as \"callTime\", tb.date_time as \"dateTime\", tb.call_price as \"callPrice\", adv.advertisers_name as \"advName\" "
			+ "FROM call_wbook tb "
			+ "WHERE 1 = 1 ";
	private static final String SQL_GET_MAX_TIME = "select MAX(tb.date_time) as \"maxTime\" "
			+ "FROM call_wbook tb "
			+ "WHERE 1 = 1 ";

	public List<Map<String, Object>> getList(CallWbook callWbook, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_BYADVID, callWbook, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(CallWbook callWbook, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, callWbook, params);
		return getResultListTotalCount(sqlParams);
	}

	@Override
	public List<Map<String, Object>> getFistList(String data) {

        SqlParams sqlParams = genFilstListWhere(SQL_GET_FILST_LIST,data);
        return getResultList(sqlParams);
	}

    @Override
    public List<Map<String, Object>> getMaxTime() {
        SqlParams sqlParams = genMaxListWhere(SQL_GET_MAX_TIME);
        return getResultList(sqlParams);
    }

    /**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, CallWbook callWbook, Map<String, Object> params){
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
		
		if (!StringUtils.isBlank(MapUtils.getString(params, "dateTimeBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "dateTimeEnd"))) {
			sqlParams.querySql.append(" AND tb.date_time between :dateTimeBegin AND :dateTimeEnd ");
			sqlParams.paramsList.add("dateTimeBegin");
			sqlParams.paramsList.add("dateTimeEnd");
			sqlParams.valueList.add(MapUtils.getString(params, "dateTimeBegin"));
			sqlParams.valueList.add(MapUtils.getString(params, "dateTimeEnd"));
		}
        return sqlParams;
	}
	/**
	 * 生成查询条件
	 * @param sql
	 * @return
	 */
	private SqlParams genFilstListWhere(String sql,String data){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        sql=sql+"and tb.date_time=\""+(data)+"\"";
        return sqlParams;

	}
	/**
	 * 生成查询条件
	 * @param sql
	 * @return
	 */
	private SqlParams genMaxListWhere(String sql){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        return sqlParams;
	}
}