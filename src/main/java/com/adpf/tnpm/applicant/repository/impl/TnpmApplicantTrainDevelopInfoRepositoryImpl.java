/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.applicant.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.tnpm.applicant.entity.TnpmApplicantTrainDevelopInfo;
import com.adpf.tnpm.applicant.repository.custom.TnpmApplicantTrainDevelopInfoRepositoryCustom;

public class TnpmApplicantTrainDevelopInfoRepositoryImpl extends BaseRepository implements TnpmApplicantTrainDevelopInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.DEVELOP_ID as \"developId\", tb.JOIN_DATE as \"joinDate\", tb.JOIN_ACTIVIE_DATE as \"joinActivieDate\", tb.REPORT_DATE as \"reportDate\", tb.INSPECT_DATE as \"inspectDate\", tb.DISCUSS_DATE as \"discussDate\", tb.IS_DEVELOP_DATE as \"isDevelopDate\", tb.BRANCH_SECRETARY as \"branchSecretary\", tb.DEVELOP_LINKMAN as \"developLinkman\", tb.INTRODUCER as \"introducer\", tb.TRAIN_DATE as \"trainDate\", tb.INQUIRY_DATE as \"inquiryDate\", tb.PASS_DATE as \"passDate\", tb.ACTIVIE_DATE as \"activieDate\", tb.TALKER as \"talker\", tb.APPROVE_DATE as \"approveDate\", tb.APPROVE_APPLY_DATE as \"approveApplyDate\", tb.TEAM_TALK_DATE as \"teamTalkDate\", tb.APPROVE_BRANCH_TALK_DATE as \"approveBranchTalkDate\", tb.APPROVE_BRANCH_SECRETARY as \"approveBranchSecretary\", tb.APPROVAL_ORGANIZATION as \"approvalOrganization\", tb.ACTIVIST_EDUCATION_SITUATION as \"activistEducationSituation\", tb.REPORT_DETAILS as \"reportDetails\", tb.PEOPLE_ID as \"peopleId\", tb.INSPECTION_ORGANIZATION as \"inspectionOrganization\" "
			+ "FROM tnpm_train_develop_info tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM tnpm_train_develop_info tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(TnpmApplicantTrainDevelopInfo tnpmTrainDevelopInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, tnpmTrainDevelopInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.DEVELOP_ID DESC ", " \"developId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TnpmApplicantTrainDevelopInfo tnpmTrainDevelopInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tnpmTrainDevelopInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TnpmApplicantTrainDevelopInfo tnpmTrainDevelopInfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        return sqlParams;
	}
}