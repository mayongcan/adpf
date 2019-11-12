/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.qtn.entity.QtnTakeNumberInfo;
import com.adpf.qtn.repository.custom.QtnTakeNumberInfoRepositoryCustom;

public class QtnTakeNumberInfoRepositoryImpl extends BaseRepository implements QtnTakeNumberInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.QRCODE_PATH as \"qrcodePath\", tb.ORGANIZER_ID as \"organizerId\", tb.CREATE_DATE as createDate,tb.NUMBER as number , tb.VIP_NUMBER as vipNumber "
			+ "FROM qtn_qrcode_info tb "
			+ "WHERE 1 = 1 ";
	
	private static final String SQL_GET_ORGANIZER_LIST = "SELECT o.ORGANIZER_ID as organizerId ,o.ORGANIZER_NAME as organizerName, o.NAME_PATH as namePath , o.AREA_NAME as areaName, o.CREATE_DATE as createDate,"
			+ "tb.QRCODE_PATH as qrcodePath,tb.NUMBER as number,tb.ID as id,tb.VIP_NUMBER as vipNumber "
			+ "from sys_organizer_info o left join qtn_take_number_info tb on o.ORGANIZER_ID = tb.ORGANIZER_ID "
			+ "where 1=1";
	
	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM qtn_qrcode_info tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(QtnTakeNumberInfo qtnQrcodeInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, qtnQrcodeInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.QRCODE_ID DESC ", " \"qrcodeId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(QtnTakeNumberInfo qtnQrcodeInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, qtnQrcodeInfo, params);
		return getResultListTotalCount(sqlParams);
	}
	
	public List<Map<String,Object>> getOrgList(Map<String,Object>params,int pageIndex,int pageSize){
		SqlParams sqlParams = genOrgListWhere (SQL_GET_ORGANIZER_LIST,params);
		sqlParams = getPageableSql(sqlParams,pageIndex,pageSize, "","");
		return getResultList(sqlParams);
	}
	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, QtnTakeNumberInfo qtnQrcodeInfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(params.get("organizerId") != null) {
			sqlParams.querySql.append(" AND tb.ORGANIZER_ID = :organizerId ");
			sqlParams.valueList.add(params.get("organizerId"));
			sqlParams.paramsList.add("organizerId");
			
		}
        return sqlParams;
	}
	private SqlParams genOrgListWhere(String sql,  Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(params.get("organizerId") != null ) {
			if("113".equals(params.get("organizerId").toString())) {
				sqlParams.querySql.append(" AND o.PARENT_ORG_ID = :organizerId");
				sqlParams.paramsList.add("organizerId");
				sqlParams.valueList.add(params.get("organizerId"));
			}else {
				sqlParams.querySql.append(" AND o.ORGANIZER_ID = :organizerId");
				sqlParams.paramsList.add("organizerId");
				sqlParams.valueList.add(params.get("organizerId"));
			}
		}
        return sqlParams;
	}
	
}