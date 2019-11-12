/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.customer.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.customer.entity.Customer;
import com.adpf.customer.repository.custom.CustomerRepositoryCustom;

public class CustomerRepositoryImpl extends BaseRepository implements CustomerRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.customer_id as \"customerId\", tb.customer_name as \"customerName\", tb.customer_guanggaozhuzizhi as \"customerGuanggaozhuzizhi\", tb.customer_suoshuhangye as \"customerSuoshuhangye\", tb.customer_lianxiren as \"customerLianxiren\", tb.customer_iphone as \"customerIphone\", tb.customer_address as \"customerAddress\", tb.customer_logo as \"customerLogo\", tb.customer_yyzz as \"customerYyzz\", tb.customer_beian as \"customerBeian\", tb.customer_zhuangtai as \"customerZhuangtai\" "
			+ "FROM customer tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM customer tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(Customer customer, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, customer, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.customer_id DESC ", " \"customerId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(Customer customer, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, customer, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, Customer customer, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (customer != null && !StringUtils.isBlank(customer.getCustomerName())) {
            sqlParams.querySql.append(getLikeSql("tb.customer_name", ":customerName"));
			sqlParams.paramsList.add("customerName");
			sqlParams.valueList.add(customer.getCustomerName());
		}
		if (customer != null && !StringUtils.isBlank(customer.getCustomerLianxiren())) {
            sqlParams.querySql.append(getLikeSql("tb.customer_lianxiren", ":customerLianxiren"));
			sqlParams.paramsList.add("customerLianxiren");
			sqlParams.valueList.add(customer.getCustomerLianxiren());
		}
        return sqlParams;
	}
}