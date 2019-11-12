/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.seats.repository.impl;

import com.adpf.modules.gdtm.util.StringUtils;
import com.adpf.seats.entity.AdvidSeatsid;
import com.adpf.seats.repository.custom.AdvidSeatsidRepositoryCustom;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;

import java.util.List;
import java.util.Map;

public class AdvidSeatsidRepositoryImpl extends BaseRepository implements AdvidSeatsidRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.adv_id as \"advId\", tb.seats_id as \"seatsId\", adv.advertisers_name as \"advName\" "
			+ "from advid_seatsid tb  LEFT JOIN sys_user_info u on tb.adv_id = u.USER_ID LEFT JOIN advertisers adv ON u.USER_CODE=adv.adv_loginid "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "from advid_seatsid tb  LEFT JOIN sys_user_info u on tb.adv_id = u.USER_ID LEFT JOIN advertisers adv ON u.USER_CODE=adv.adv_loginid "
			+ "WHERE 1 = 1 ";


	private static final String SQL_GET_LIST_BY_SEATSID = "SELECT tb.id as \"id\", tb.adv_id as \"advId\", tb.seats_id as \"seatsId\", adv.advertisers_name as \"advName\" "
			+ "from advid_seatsid tb  LEFT JOIN sys_user_info u on tb.adv_id = u.USER_ID LEFT JOIN advertisers adv ON u.USER_CODE=adv.adv_loginid "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_BY_ADVID = "SELECT tb.id as \"id\", tb.adv_id as \"advId\", tb.seats_id as \"seatsId\" "
			+ "from advid_seatsid tb "
			+ "WHERE 1 = 1 ";

	public List<Map<String, Object>> getList(AdvidSeatsid advidSeatsid, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, advidSeatsid, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdvidSeatsid advidSeatsid, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, advidSeatsid, params);
		return getResultListTotalCount(sqlParams);
	}

	@Override
	public List<Map<String, Object>> getListBySeatsId(String seatsId) {

        SqlParams listBySeatsId = getListBySeatsId(SQL_GET_LIST_BY_SEATSID, seatsId);

        return getResultList(listBySeatsId);
	}

	@Override
	public List<Map<String, Object>> getListByAdvId(String advId) {

		SqlParams listByAdvId = getListByAdvId(SQL_GET_LIST_BY_ADVID, advId);

		return getResultList(listByAdvId);

	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
			 */
	private SqlParams genListWhere(String sql, AdvidSeatsid advidSeatsid, Map<String, Object> params){
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


	/**
	 * 根据seatsId查询
	 * @param sql
	 * @return
	 */
	private SqlParams getListBySeatsId(String sql,String seatsId){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
			if (seatsId != null && !StringUtils.isBlank(seatsId)) {
				sqlParams.querySql.append("and tb.seats_id = :seatsId");
				sqlParams.paramsList.add("seatsId");
				sqlParams.valueList.add(seatsId);
			}
		return sqlParams;
	}
	/**
	 * 根据AdvId查询
	 * @param sql
	 * @return
	 */
	private SqlParams getListByAdvId(String sql,String AdvId){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
			if (AdvId != null && !StringUtils.isBlank(AdvId)) {
				sqlParams.querySql.append("and tb.adv_id = :AdvId");
				sqlParams.paramsList.add("AdvId");
				sqlParams.valueList.add(AdvId);
			}
		return sqlParams;
	}
}