/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.count.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.gdtm.count.entity.AdpfLabelCount;
import com.adpf.modules.gdtm.count.repository.custom.AdpfLabelCountRepositoryCustom;


public class AdpfLabelCountRepositoryImpl extends BaseRepository implements AdpfLabelCountRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.date as \"date\", b.label as \"label\", tb.size as \"size\", tb.weight as \"weight\" "
			+ "FROM adpf_label_count tb LEFT JOIN adpf_tag_library b on tb.label = b.tag_id "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_label_count tb LEFT JOIN adpf_tag_library b on tb.label = b.tag_id "
			+ "WHERE 1 = 1 ";
	
	private static final String SQL_GET_LIST_MONTH = "SELECT b.label,sum(size) as num\n" + 
			"FROM adpf_label_count tb LEFT JOIN adpf_tag_library b on tb.label = b.tag_id \n" + 
			"WHERE 1 = 1 ";
	
	private static final String SQL_GET_LIST_COUNT_MONTH = "SELECT count(1) as \"count\" "
			+ "FROM adpf_label_count tb LEFT JOIN adpf_tag_library b on tb.label = b.tag_id "
			+ "WHERE 1 = 1 ";
	
	//查询最新时间sql
	private static final String SQL_DATE = "SELECT tb.id as \"id\",tb.date as \"date\", tb.label as \"label\", tb.size as \"size\", tb.weight as \"weight\" FROM adpf_label_count tb WHERE";
	//查询一星期(7天的数据)sql
	private static final String SQL_GS = "SELECT a.id as \"id\", a.date as \"date\", b.label as \"label\", a.size as \"size\", a.weight as \"weight\" from adpf_label_count a LEFT JOIN adpf_tag_library b on a.label = b.tag_id WHERE";
	
	private static final String SQL_z = "SELECT * " + 
			"FROM (adpf_label_count tb LEFT JOIN adpf_tag_library b on tb.label = b.tag_id) LEFT JOIN adpf_tag_region c on tb.key = c.key " + 
			"WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(AdpfLabelCount adpfLabelCount, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfLabelCount, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.date DESC,tb.size DESC ", " \"size\",\"size\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfLabelCount adpfLabelCount, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfLabelCount, params);
		return getResultListTotalCount(sqlParams);
	}
	
	public List<Map<String, Object>> getListMonth(AdpfLabelCount adpfLabelCount, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhereMonth(SQL_GET_LIST_MONTH, adpfLabelCount, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " num DESC ", " \"num\" DESC ");
		return getResultList(sqlParams);
	}
	
	public int getListCountMonth(AdpfLabelCount adpfLabelCount, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhereMonth(SQL_GET_LIST_COUNT_MONTH, adpfLabelCount, params);
		return getResultListTotalCount(sqlParams);
	}
	
	public List<Map<String, Object>> getDate(AdpfLabelCount adpfLabelCount,Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genDate(SQL_DATE, params);
		return getResultList(sqlParams);
	}
	
	public List<Map<String, Object>> getTagG(AdpfLabelCount adpfLabelCount,Map<String, Object> params) {
		//生成查询条件
		System.out.println(1);
		SqlParams sqlParams = genTagG(SQL_GS, params);
		return getResultList(sqlParams);
	}


	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfLabelCount adpfLabelCount, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (!StringUtils.isBlank(MapUtils.getString(params, "dateBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "dateEnd"))) {
			sqlParams.querySql.append(" AND tb.date between :dateBegin AND :dateEnd ");
			sqlParams.paramsList.add("dateBegin");
			sqlParams.paramsList.add("dateEnd");
			sqlParams.valueList.add(MapUtils.getString(params, "dateBegin"));
			sqlParams.valueList.add(MapUtils.getString(params, "dateEnd"));
		}
		if (adpfLabelCount != null && !StringUtils.isBlank(adpfLabelCount.getLabel())) {
            sqlParams.querySql.append(getLikeSql("b.label", ":label"));
			sqlParams.paramsList.add("label");
			sqlParams.valueList.add(adpfLabelCount.getLabel());
		}
        return sqlParams;
	}
	
	private SqlParams genListWhereMonth(String sql, AdpfLabelCount adpfLabelCount, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (!StringUtils.isBlank(MapUtils.getString(params, "Month"))) {
			sqlParams.querySql.append(" AND tb.date like :Month GROUP BY tb.label ");
			sqlParams.paramsList.add("Month");
			sqlParams.valueList.add(MapUtils.getString(params, "Month"));
		}
//		if (adpfLabelCount != null && !StringUtils.isBlank(adpfLabelCount.getLabel())) {
//            sqlParams.querySql.append(getLikeSql("b.label", ":label"));
//			sqlParams.paramsList.add("label");
//			sqlParams.valueList.add(adpfLabelCount.getLabel());
//		}
        return sqlParams;
	}
	
	private SqlParams genDate(String sql,Map<String, Object> params) {
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(true) {
			sqlParams.querySql.append(" tb.label= :label");
			sqlParams.paramsList.add("label");
			sqlParams.valueList.add(params.get("label"));
		}
		if(true) {
			sqlParams.querySql.append(" and tb.date= :date");
			sqlParams.paramsList.add("date");
			sqlParams.valueList.add(params.get("date"));
		}
		return sqlParams;		
	}
	//拼接查询7天的sql
	private SqlParams genTagG(String sql,Map<String, Object> params) {
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(true) {
			sqlParams.querySql.append(" b.id= :id");
			sqlParams.paramsList.add("id");
			sqlParams.valueList.add(params.get("id"));
		}
		if (!StringUtils.isBlank(MapUtils.getString(params, "TimeBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "TimeEnd"))) {
			sqlParams.querySql.append(" AND a.date between :TimeBegin AND :TimeEnd ");
			sqlParams.paramsList.add("TimeBegin");
			sqlParams.paramsList.add("TimeEnd");
			sqlParams.valueList.add(MapUtils.getString(params, "TimeBegin"));
			sqlParams.valueList.add(MapUtils.getString(params, "TimeEnd"));
		}
		if(StringUtils.isBlank(MapUtils.getString(params, "TimeBegin")) && StringUtils.isBlank(MapUtils.getString(params, "TimeEnd"))) {
			sqlParams.querySql.append(" ORDER BY a.date ASC LIMIT 7");
		}
		return sqlParams;
	}
}