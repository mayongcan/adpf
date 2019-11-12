/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.applicant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.adpf.tnpm.applicant.entity.TnpmApplicantRewardsInfo;
import com.adpf.tnpm.applicant.repository.custom.TnpmApplicantRewardsInfoRepositoryCustom;


/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface TnpmApplicantRewardsInfoRepository extends JpaRepository<TnpmApplicantRewardsInfo, Long>, JpaSpecificationExecutor<TnpmApplicantRewardsInfo>, TnpmApplicantRewardsInfoRepositoryCustom {
	
	
	
}