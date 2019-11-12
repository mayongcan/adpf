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

import com.adpf.modules.gdtm.count.entity.AdpfLabelCountProvince;
import com.adpf.modules.gdtm.count.repository.custom.AdpfLabelCountProvinceRepositoryCustom;

public class AdpfLabelCountProvinceRepositoryImpl extends BaseRepository implements AdpfLabelCountProvinceRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.date as \"date\", c.province as \"province\", b.label as \"label\", tb.size as \"size\", tb.weight as \"weight\" "
			+ "FROM (adpf_label_count_province tb LEFT JOIN adpf_tag_library b on tb.label = b.tag_id) LEFT JOIN adpf_tag_region c on tb.province = c.code "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM (adpf_label_count_province tb LEFT JOIN adpf_tag_library b on tb.label = b.tag_id) LEFT JOIN adpf_tag_region c on tb.province = c.code "
			+ "WHERE 1 = 1 ";
	
	//查询最新时间sql
	private static final String SQL_DATE = "SELECT tb.id as \"id\", tb.date as \"date\", tb.label as \"label\", tb.size as \"size\", tb.weight as \"weight\", tb.province as \"province\" FROM adpf_label_count_province tb WHERE";
	
	//查询某标签的信息sql
	private static final String SQL_TAG = "SELECT tb.id as \"id\", tb.date as \"date\", b.province as \"province\", c.label as \"label\", tb.size as \"size\" " 
			+ "FROM (adpf_label_count_province tb LEFT JOIN adpf_tag_region b ON tb.province = b.code) LEFT JOIN adpf_tag_library c ON tb.label = c.tag_id "
			+ "WHERE ";
	
	public List<Map<String, Object>> getList(AdpfLabelCountProvince adpfLabelCountProvince, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfLabelCountProvince, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.date DESC,tb.size DESC ", " \"size\",\"size\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfLabelCountProvince adpfLabelCountProvince, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfLabelCountProvince, params);
		return getResultListTotalCount(sqlParams);
	}
	
	public List<Map<String, Object>> getDate(AdpfLabelCountProvince adpfLabelCountProvince,
			Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genDate(SQL_DATE, params);
		return getResultList(sqlParams);
	}

	public List<Map<String, Object>> getProvince(AdpfLabelCountProvince adpfLabelCountProvince, Map<String, Object> params,
			int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genProvince(SQL_TAG,adpfLabelCountProvince, params);
		System.out.println(getResultList(sqlParams));
		return getResultList(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfLabelCountProvince adpfLabelCountProvince, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (!StringUtils.isBlank(MapUtils.getString(params, "dateBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "dateEnd"))) {
			sqlParams.querySql.append(" AND tb.date between :dateBegin AND :dateEnd ");
			sqlParams.paramsList.add("dateBegin");
			sqlParams.paramsList.add("dateEnd");
			sqlParams.valueList.add(MapUtils.getString(params, "dateBegin"));
			sqlParams.valueList.add(MapUtils.getString(params, "dateEnd"));
		}
		if (adpfLabelCountProvince != null && !StringUtils.isBlank(adpfLabelCountProvince.getProvince())) {
            sqlParams.querySql.append(getLikeSql("c.province", ":province"));
			sqlParams.paramsList.add("province");
			sqlParams.valueList.add(adpfLabelCountProvince.getProvince());
		}
		if (adpfLabelCountProvince != null && !StringUtils.isBlank(adpfLabelCountProvince.getLabel())) {
            sqlParams.querySql.append(getLikeSql("b.label", ":label"));
			sqlParams.paramsList.add("label");
			sqlParams.valueList.add(adpfLabelCountProvince.getLabel());
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
			sqlParams.querySql.append(" and tb.label= :label");
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
	
	private SqlParams genProvince(String sql,AdpfLabelCountProvince adpfLabelCountProvince,Map<String, Object> params) {
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(true) {
			sqlParams.querySql.append(" c.id= :id");
			sqlParams.paramsList.add("id");
			sqlParams.valueList.add(params.get("id"));
		}
		if (true) {
			sqlParams.querySql.append(" AND tb.date= :dateBegin ");
			sqlParams.paramsList.add("dateBegin");
			sqlParams.valueList.add(MapUtils.getString(params, "dateBegin"));
		}
		return sqlParams;	
	}

}