/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.applicant.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;

import com.adpf.tnpm.applicant.entity.TnpmApplicantAdministrationDutyInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TnpmApplicantAdministrationDutyInfoRepositoryCustom {

	/**
	 * 获取Tnpm administrationDutyInfo列表
	 * @param tnpm administrationDutyInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TnpmApplicantAdministrationDutyInfo tnpmAdministrationDutyInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取Tnpm administrationDutyInfo列表总数
	 * @param tnpm administrationDutyInfo
	 * @param params
	 * @return
	 */
	public int getListCount(TnpmApplicantAdministrationDutyInfo tnpmAdministrationDutyInfo, Map<String, Object> params);
}