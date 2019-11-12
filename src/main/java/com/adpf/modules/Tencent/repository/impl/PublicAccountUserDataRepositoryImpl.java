/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.Tencent.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.Tencent.entity.PublicAccountUserData;
import com.adpf.modules.Tencent.repository.custom.PublicAccountUserDataRepositoryCustom;
import com.adpf.modules.orient.entity.DirectionalInformation;
import com.adpf.modules.orient.entity.District;

public class PublicAccountUserDataRepositoryImpl extends BaseRepository implements PublicAccountUserDataRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.REGIONAL as \"regional\", tb.DATATYPE as \"datatype\", tb.NUMBER_LABEL_VISITS as \"numberLabelVisits\", tb.LABEL_ONLINE_TIME as \"labelOnlineTime\", tb.LABEL_ONLINE_TRAFFIC as \"labelOnlineTraffic\", tb.LABEL_VISITTIME_SCUMULATIVE as \"labelVisittimeScumulative\", tb.LABEL_ONLINE_TIMEA_CCUMULATED as \"labelOnlineTimeaCcumulated\", tb.LABEL_ONLINE_TRAFFI_CCUMULATIVE as \"labelOnlineTraffiCcumulative\", tb.ACCESS_TIME as \"accessTime\", tb.OPERATING_SYSTEM as \"operatingSystem\", tb.CREATE_TIME as \"createTime\", tb.TEL as \"tel\", tb.PEOPLE_ID as \"peopleId\", tb.CUSTOM_APP as \"customApp\",tb.LINKMAN as \"linkman\",tb.COMPANY as \"company\""
			+ "FROM public_account_user_data tb ";
		

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM public_account_user_data tb "
			+ "WHERE 1 = 1 ";
		//查找联动回显资料
		private static final String SQL_GET_LIST_DATUM = "SELECT tb.DIRECTIONAL_ID as \"directionalId\", tb.ID_CODE as \"idCode\", tb.DIRECTIONAL_NAME as \"directionalName\", tb.FATHER_ID as \"fatherId\", tb.GRADE as \"grade\", tb.STATE as \"state\""
				+ "FROM directional_information tb ";
		//查找联动回显地域资料
		private static final String SQL_GET_LIST_TERRITORY = "SELECT tb.ID as \"id\", tb.PARENT_ID as \"parent_id\", tb.NAME as \"name\", tb.FIRST_LETTER as \"firstLetter\", tb.INITIALS as \"initials\", tb.PINYIN as \"pinyin\", tb.EXTRA as \"extra\", tb.SUFFIX as \"suffix\", tb.CODE as \"code\", tb.AREA_CODE as \"areaCode\", tb.DISP_ORDER as \"dispOrder\""
				+ "FROM sys_district tb ";
	public List<Map<String, Object>> getList(PublicAccountUserData publicAccountUserData, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, publicAccountUserData, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(PublicAccountUserData publicAccountUserData, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, publicAccountUserData, params);
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
			
			@Override
			//根据用户ID查询用户列表
			public List<Map<String, Object>> getListByID(PublicAccountUserData publicAccountUserData,Map<String, Object> params){
				SqlParams sqlParams=getListByID(SQL_GET_LIST,publicAccountUserData,params);
				return getResultList(sqlParams);
				
			}
			
		
	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
			private SqlParams genListWhere(String sql, PublicAccountUserData publicAccountUserData, Map<String, Object> params){
				SqlParams sqlParams = new SqlParams();
				sqlParams.querySql.append(sql);
				if (!StringUtils.isBlank(MapUtils.getString(params, "createTimeBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "createTimeEnd"))) {
					sqlParams.querySql.append(" AND tb.CREATE_TIME between :createTimeBegin AND :createTimeEnd ");
					sqlParams.paramsList.add("createTimeBegin");
					sqlParams.paramsList.add("createTimeEnd");
					sqlParams.valueList.add(MapUtils.getString(params, "createTimeBegin"));
					sqlParams.valueList.add(MapUtils.getString(params, "createTimeEnd"));
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
	
	private SqlParams getListByID(String sql,PublicAccountUserData publicAccountUserData,Map<String, Object>params){
		SqlParams sqlParams=new SqlParams();
		sql=sql+"where tb.PEOPLE_ID="+params.get("peopleId");
		sqlParams.querySql.append(sql);
		return sqlParams;
	}

	
	
	
	
	
}