/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.province.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.gdtm.province.entity.AdpfProvinceSize;
import com.adpf.modules.gdtm.province.repository.custom.AdpfProvinceSizeRepositoryCustom;

public class AdpfProvinceSizeRepositoryImpl extends BaseRepository implements AdpfProvinceSizeRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.PROVINCE as \"province\", tb.SIZE as \"size\", tb.DATE as \"date\" "
			+ "FROM adpf_province_size tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_province_size tb "
			+ "WHERE 1 = 1 ";
	
	//查询最新时间sql
	private static final String SQL_DATE = "SELECT tb.id as \"id\",tb.date as \"date\", tb.province as \"province\", tb.size as \"size\", tb.weight as \"weight\" FROM adpf_label_count tb WHERE";
	
	public List<Map<String, Object>> getList(AdpfProvinceSize adpfProvinceSize, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfProvinceSize, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfProvinceSize adpfProvinceSize, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfProvinceSize, params);
		return getResultListTotalCount(sqlParams);
	}
	
	public List<Map<String, Object>> getDate(AdpfProvinceSize adpfProvinceSize, Map<String, Object> params) {
		SqlParams sqlParams = genDate(SQL_DATE, params);
		return getResultList(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfProvinceSize adpfProvinceSize, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (adpfProvinceSize != null && !StringUtils.isBlank(adpfProvinceSize.getProvince())) {
            sqlParams.querySql.append(getLikeSql("tb.PROVINCE", ":province"));
			sqlParams.paramsList.add("province");
			sqlParams.valueList.add(adpfProvinceSize.getProvince());
		}
		if (!StringUtils.isBlank(MapUtils.getString(params, "dateBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "dateEnd"))) {
			sqlParams.querySql.append(" AND tb.DATE between :dateBegin AND :dateEnd ");
			sqlParams.paramsList.add("dateBegin");
			sqlParams.paramsList.add("dateEnd");
			sqlParams.valueList.add(MapUtils.getString(params, "dateBegin"));
			sqlParams.valueList.add(MapUtils.getString(params, "dateEnd"));
		}
        return sqlParams;
	}
	
	private SqlParams genDate(String sql,Map<String, Object> params) {
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(true) {
			sqlParams.querySql.append(" tb.province= :province");
			sqlParams.paramsList.add("province");
			sqlParams.valueList.add(params.get("province"));
		}
		if(true) {
			sqlParams.querySql.append(" and tb.date= :date");
			sqlParams.paramsList.add("date");
			sqlParams.valueList.add(params.get("date"));
		}
		return sqlParams;
	}
}