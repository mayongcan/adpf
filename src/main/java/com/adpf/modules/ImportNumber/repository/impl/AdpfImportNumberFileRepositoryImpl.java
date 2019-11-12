/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.ImportNumber.repository.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.adpf.modules.ImportNumber.entity.AdpfImportNumberFile;
import com.adpf.modules.ImportNumber.repository.custom.AdpfImportNumberFileRepositoryCustom;

public class AdpfImportNumberFileRepositoryImpl extends BaseRepository implements AdpfImportNumberFileRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.OPERATOR as \"operator\", tb.PROVINCE as \"province\", tb.CITY as \"city\", tb.AREA_CODE as \"areaCode\", tb.PHONE as \"phone\", tb.FILE_PATH as \"filePath\", tb.SERIAL_NUMBER as \"serialNumber\" "
			+ "FROM adpf_import_number_file tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_import_number_file tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(AdpfImportNumberFile adpfImportNumberFile, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfImportNumberFile, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfImportNumberFile adpfImportNumberFile, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfImportNumberFile, params);
		return getResultListTotalCount(sqlParams);
	}
	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfImportNumberFile adpfImportNumberFile, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        return sqlParams;
	}
}