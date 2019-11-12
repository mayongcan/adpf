/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.orient.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import com.adpf.modules.orient.entity.DirectionalInformation;
import com.adpf.modules.orient.entity.District;
import com.adpf.modules.orient.entity.OrienteeringRegulate;
import com.adpf.modules.orient.repository.custom.OrienteeringRegulateRepositoryCustom;

public class OrienteeringRegulateRepositoryImpl extends BaseRepository implements OrienteeringRegulateRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ORIENTEER_ID as \"orienteerId\", tb.ORIENTEER_NAME as \"orienteerName\", tb.ORIENTEER_DESCRIBE as \"orienteerDescribe\", tb.TERRITORY as \"territory\", tb.AGEBAND as \"ageband\", tb.GENDER as \"gender\", tb.EDUCATION as \"education\", tb.MRITL_STTUS as \"mritlSttus\", tb.WORKING_POSITION as \"workingPosition\", tb.BUSINESS_INTERESTS as \"businessInterests\", tb.TAG as \"tag\", tb.APP_BEHAVIOR as \"appBehavior\", tb.APP_INSTALL as \"appInstall\", tb.CROWD_ID as \"crowdId\", tb.PAYING_USER as \"payingUser\", tb.CONSUMPTION_STATUS as \"consumptionStatus\", tb.RESIDENTIAL_COMMUNITY_PRICE as \"residentialCommunityPrice\", tb.INTERNET_SCENARIO as \"internetScenario\", tb.KERNEL as \"kernel\", tb.NETWORKING_WAY as \"networkingWay\", tb.MOBILE_OPERATOR as \"mobileOperator\", tb.EQUIPMENT_PRICE as \"equipmentPrice\", tb.MEDIUM_TYPE as \"mediumType\", tb.PUBLIC_TYPE as \"publicType\", tb.TEMPERATURE as \"temperature\", tb.ULTRAVIOLET as \"ultraviolet\", tb.DRESS_INDEX as \"dressIndex\", tb.MAKEUP_INDEX as \"makeupIndex\", tb.METEOROLOGY as \"meteorology\", tb.KEYWORD_FILE as \"keywordFile\", tb.PLANID as \"planid\" "
			+ "FROM orienteering_regulate tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM orienteering_regulate tb "
			+ "WHERE 1 = 1 ";
	//查找联动回显资料
	private static final String SQL_GET_LIST_DATUM = "SELECT tb.DIRECTIONAL_ID as \"directionalId\", tb.ID_CODE as \"idCode\", tb.DIRECTIONAL_NAME as \"directionalName\", tb.FATHER_ID as \"fatherId\", tb.GRADE as \"grade\", tb.STATE as \"state\""
			+ "FROM directional_information tb ";
	//查找联动回显地域资料
	private static final String SQL_GET_LIST_TERRITORY = "SELECT tb.ID as \"id\", tb.PARENT_ID as \"parent_id\", tb.NAME as \"name\", tb.FIRST_LETTER as \"firstLetter\", tb.INITIALS as \"initials\", tb.PINYIN as \"pinyin\", tb.EXTRA as \"extra\", tb.SUFFIX as \"suffix\", tb.CODE as \"code\", tb.AREA_CODE as \"areaCode\", tb.DISP_ORDER as \"dispOrder\""
			+ "FROM sys_district tb ";
	
	public List<Map<String, Object>> getList(OrienteeringRegulate orienteeringRegulate, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, orienteeringRegulate, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ORIENTEER_ID DESC ", " \"orienteerId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(OrienteeringRegulate orienteeringRegulate, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, orienteeringRegulate, params);
		return getResultListTotalCount(sqlParams);
	}
		//返回资料
		public List<Map<String, Object>> getListDatum(DirectionalInformation directionalInformation, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListDatum(SQL_GET_LIST_DATUM, directionalInformation, params);
		return getResultList(sqlParams);
	}
		//返回地域资料
		public List<Map<String, Object>> getTerritory(District district, Map<String, Object> params) {
			//生成查询条件
			SqlParams sqlParams = getTerritory(SQL_GET_LIST_TERRITORY, district, params);
			return getResultList(sqlParams);
	}
	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, OrienteeringRegulate orienteeringRegulate, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(StringUtils.isNotBlank((String)params.get("planid"))) {
			sqlParams.querySql.append(" AND tb.PLANID  = :planid ");
			sqlParams.paramsList.add("planid");
			sqlParams.valueList.add(params.get("planid"));
		}
        return sqlParams;
	}

	private SqlParams genListDatum(String sql, DirectionalInformation directionalInformation, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sql=sql+"where tb.FATHER_ID="+params.get("fatherId");
		sqlParams.querySql.append(sql);
	    return sqlParams;
	}
	private SqlParams getTerritory(String sql, District district, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sql=sql+"where tb.PARENT_ID="+params.get("parentId");
		sqlParams.querySql.append(sql);
	    return sqlParams;
	}
}