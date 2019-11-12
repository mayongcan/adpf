/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.openam.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import com.adpf.modules.openam.entity.AdpfOpenamFreepawdlog;
import com.adpf.modules.openam.repository.custom.AdpfOpenamFreepawdlogRepositoryCustom;
import com.alibaba.fastjson.JSONObject;

public class AdpfOpenamFreepawdlogRepositoryImpl extends BaseRepository
		implements AdpfOpenamFreepawdlogRepositoryCustom {

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.ORGANIZER_ID as \"organizerId\", tb.VERSION as \"version\", tb.CLIENT_IP as \"clientIp\","
			+ " tb.CLIENT_TYPE as \"clientType\", tb.IMEI as \"imei\", tb.IDFA as \"idfa\", tb.AES_CACHE_KEY as \"aesCacheKey\", tb.CODE as \"code\", tb.STATUS as \"status\", "
			+ "tb.MOBILE as \"mobile\", tb.CREATE_DATE as \"createDate\", tb.GETMOBILE_DATE as \"getmobileDate\" , tb.NICK_NAME as \"nickName\""
			+ "  , tb.OWNER as \"owner\", tb.OPEN_ID as \"openId\", tb.USER_ICON_URL as \"userIconUrl\", tb.MODEL as \"model\", tb.OPERATING as \"operating\" , tb.RESULT as \"result\"  , tb.NETWORK_TYPE as \"networkType\" "
			+ "FROM adpf_openam_freepawdlog tb " + "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_openam_freepawdlog tb " + "WHERE 1 = 1 ";

	private static final String SQL_GET_REPORT_LIST = "SELECT count(1) as count ,date(tb.CREATE_DATE) as createDate FROM adpf_openam_freepawdlog tb "
			+ " WHERE 1 = 1  ";;;

	public List<Map<String, Object>> getList(AdpfOpenamFreepawdlog adpfOpenamFreepawdlog, Map<String, Object> params,
			int pageIndex, int pageSize) {
		// 生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfOpenamFreepawdlog, params);
		// 添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfOpenamFreepawdlog adpfOpenamFreepawdlog, Map<String, Object> params) {
		// 生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfOpenamFreepawdlog, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfOpenamFreepawdlog adpfOpenamFreepawdlog,
			Map<String, Object> params) {
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (StringUtils.isNotBlank((String) params.get("owner"))) {
			sqlParams.querySql.append(" AND tb.OWNER = :owner ");
			sqlParams.paramsList.add("owner");
			sqlParams.valueList.add(params.get("owner"));
		}
		if (StringUtils.isNotBlank((String) params.get("status"))) {
			sqlParams.querySql.append(" AND tb.STATUS = :status ");
			sqlParams.paramsList.add("status");
			sqlParams.valueList.add(params.get("status"));
		}
		if (StringUtils.isNotBlank(String.valueOf(adpfOpenamFreepawdlog.getOrganizerId()))) {
			sqlParams.querySql.append(" AND tb.ORGANIZER_ID = :organizerId ");
			sqlParams.paramsList.add("organizerId");
			sqlParams.valueList.add(adpfOpenamFreepawdlog.getOrganizerId());
		}
		if ("" != params.get("startTime") && "" != params.get("endTime")) {
//			sqlParams.querySql.append(" AND tb.CREATE_DATE between CONCAT(:startTime,' 00::00') AND CONCAT(:endTime, ' 00:00:00')");
			sqlParams.querySql.append(" AND tb.CREATE_DATE between :startTime AND :endTime");
			sqlParams.paramsList.add("startTime");
			sqlParams.paramsList.add("endTime");
			sqlParams.valueList.add(params.get("startTime"));
			sqlParams.valueList.add(params.get("endTime"));
		}
		return sqlParams;
	}

	@Override
	public List<Map<String, Object>> getReportList(AdpfOpenamFreepawdlog adpfOpenamFreepawdlog,
			Map<String, Object> params) {
		// 生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_REPORT_LIST, adpfOpenamFreepawdlog, params);
		// 添加分页和排序
		sqlParams.querySql.append(" group by date(tb.CREATE_DATE)  order by tb.CREATE_DATE ");
		return getResultList(sqlParams);
	}
}