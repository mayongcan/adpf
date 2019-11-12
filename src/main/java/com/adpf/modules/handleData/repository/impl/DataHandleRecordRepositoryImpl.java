/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.handleData.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.handleData.entity.DataHandleRecord;
import com.adpf.modules.handleData.repository.custom.DataHandleRecordRepositoryCustom;

public class DataHandleRecordRepositoryImpl extends BaseRepository implements DataHandleRecordRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.DATA_ID as \"dataId\", tb.TBALE_NAME as \"tableName\", tb.FILE_NAME as \"fileName\", tb.CREATE_TABLE as \"createTable\", tb.DATA_TYPE as \"dataType\", tb.DATE as \"date\" "
			+ "FROM data_handle_record tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM data_handle_record tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(DataHandleRecord dataHandleRecord, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, dataHandleRecord, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.DATA_ID DESC ", " \"dataId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(DataHandleRecord dataHandleRecord, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, dataHandleRecord, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, DataHandleRecord dataHandleRecord, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (!StringUtils.isBlank(MapUtils.getString(params, "dateBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "dateEnd"))) {
			sqlParams.querySql.append(" AND tb.DATE between :dateBegin AND :dateEnd ");
			sqlParams.paramsList.add("dateBegin");
			sqlParams.paramsList.add("dateEnd");
			sqlParams.valueList.add(MapUtils.getString(params, "dateBegin"));
			sqlParams.valueList.add(MapUtils.getString(params, "dateEnd"));
		}
        return sqlParams;
	}
}