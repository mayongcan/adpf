/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.tnpm.party.entity.PartyMembers;
import com.adpf.tnpm.party.repository.custom.PartyMembersRepositoryCustom;

public class PartyMembersRepositoryImpl extends BaseRepository implements PartyMembersRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.PEOPLE_ID as \"peopleId\", tb.NAME as \"name\", tb.ID_CARD as \"idCard\", tb.NATION as \"nation\", tb.AGE as \"age\", tb.PEOPLE_TYPE as \"peopleType\", tb.PARTY_BRANCH as \"partyBranch\", tb.INFO_COMPLETION as \"infoCompletion\", tb.BRITHDAY as \"brithday\", tb.EDUCATION as \"education\", tb.PLACE_OF_WORK as \"placeOfWork\", tb.PHONE as \"phone\", tb.AREA_CODE as \"areaCode\", tb.TELEPHONE as \"telephone\", tb.NATIVE_PLACE as \"nativePlace\", tb.If_TAIWAN as \"ifTAIWAN\", tb.MARITAL_STATUS as \"maritalStatus\", tb.JOIN_WORK_DATE as \"joinWorkDate\", tb.INFO_MANAGEMENT_UNIT_NAME as \"infoManagementUnitName\", tb.WORK_NAME as \"workName\", tb.SITUATION as \"situation\", tb.STRATUM_TYPE as \"stratumType\", tb.IF_MIGRANT_WORKERS as \"ifMigrantWorkers\", tb.PARTY_MEMBER_TRAINING_SITUATION as \"partyMemberTrainingSituation\", tb.IMAGE as \"image\", tb.DEL_FLAG as \"delFlag\", tb.PARTY_TYPE as \"partyType\", tb.GENDER as \"gender\", tb.START_PARTY_TIME as \"startPartyTime\", tb.BECAME_PARTY_TIME as \"becamePartyTime\", tb.PARTY_STATE as \"partyState\", tb.MISSING_PARTY as \"missingParty\", tb.MISSING_DATE as \"missingDate\", tb.FLOW_PARTY as \"flowParty\", tb.FLOW_DIRECTION as \"flowDirection\", tb.HOME_ADDRESS as \"homeAddress\""
			+ "FROM tnpm_join_party_people_info tb "
			+ "WHERE tb.PARTY_TYPE = 0 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM tnpm_join_party_people_info tb "
			+ "WHERE tb.PARTY_TYPE = 0 ";
	
	public List<Map<String, Object>> getList(PartyMembers tnpmJoinPartyPeopleInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tnpmJoinPartyPeopleInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.PEOPLE_ID DESC ", " \"peopleId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(PartyMembers tnpmJoinPartyPeopleInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tnpmJoinPartyPeopleInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, PartyMembers tnpmJoinPartyPeopleInfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(StringUtils.isNotBlank((String)params.get("delFlag"))) {
			sqlParams.querySql.append(" AND tb.DEL_FLAG  = :delFlag ");
			sqlParams.paramsList.add("delFlag");
			sqlParams.valueList.add(params.get("delFlag"));
		}
		if (tnpmJoinPartyPeopleInfo != null && !StringUtils.isBlank(tnpmJoinPartyPeopleInfo.getName())) {
            sqlParams.querySql.append(" AND tb.NAME  like concat('%', :name,'%') ");
			sqlParams.paramsList.add("name");
			sqlParams.valueList.add(tnpmJoinPartyPeopleInfo.getName());
		}
		if (tnpmJoinPartyPeopleInfo != null && !StringUtils.isBlank(tnpmJoinPartyPeopleInfo.getIdCard())) {
			sqlParams.querySql.append(" AND tb.ID_CARD = :idCard ");
			sqlParams.paramsList.add("idCard");
			sqlParams.valueList.add(tnpmJoinPartyPeopleInfo.getIdCard());
		}
		if (tnpmJoinPartyPeopleInfo != null && !StringUtils.isBlank(tnpmJoinPartyPeopleInfo.getPeopleType())) {
			sqlParams.querySql.append(" AND tb.PEOPLE_TYPE = :peopleType ");
			sqlParams.paramsList.add("peopleType");
			sqlParams.valueList.add(tnpmJoinPartyPeopleInfo.getPeopleType());
		}
        return sqlParams;
	}
}