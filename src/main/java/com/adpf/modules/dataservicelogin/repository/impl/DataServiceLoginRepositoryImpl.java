/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.dataservicelogin.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.dataservicelogin.entity.DataServiceLogin;
import com.adpf.modules.dataservicelogin.repository.custom.DataServiceLoginRepositoryCustom;

public class DataServiceLoginRepositoryImpl extends BaseRepository implements DataServiceLoginRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.USERNAME as \"username\", tb.PASSWORD as \"password\", tb.APPKEY as \"appkey\", tb.TEL as \"tel\", tb.EMAIL as \"email\" "
			+ "FROM data_service_login tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM data_service_login tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(DataServiceLogin dataServiceLogin, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, dataServiceLogin, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}
	
	public List<Map<String, Object>> getUserInfo(String username) {
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(SQL_GET_LIST);
		if (username != null && !"".equals(username)) {
			sqlParams.querySql.append(" AND tb.USERNAME = :username ");
			sqlParams.paramsList.add("username");
			sqlParams.valueList.add(username);
		}
		return getResultList(sqlParams);
	}

	public int getListCount(DataServiceLogin dataServiceLogin, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, dataServiceLogin, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, DataServiceLogin dataServiceLogin, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (dataServiceLogin != null && !StringUtils.isBlank(dataServiceLogin.getUsername())) {
			sqlParams.querySql.append(" AND tb.USERNAME = :username ");
			sqlParams.paramsList.add("username");
			sqlParams.valueList.add(dataServiceLogin.getUsername());
		}
		if (dataServiceLogin != null && !StringUtils.isBlank(dataServiceLogin.getPassword())) {
			sqlParams.querySql.append(" AND tb.PASSWORD = :password ");
			sqlParams.paramsList.add("password");
			sqlParams.valueList.add(dataServiceLogin.getPassword());
		}
		if (dataServiceLogin != null && dataServiceLogin.getTel() != null) {
			sqlParams.querySql.append(" AND tb.TEL = :tel ");
			sqlParams.paramsList.add("tel");
			sqlParams.valueList.add(dataServiceLogin.getTel());
		}
		if (dataServiceLogin != null && !StringUtils.isBlank(dataServiceLogin.getEmail())) {
			sqlParams.querySql.append(" AND tb.EMAIL = :email ");
			sqlParams.paramsList.add("email");
			sqlParams.valueList.add(dataServiceLogin.getEmail());
		}
        return sqlParams;
	}

}