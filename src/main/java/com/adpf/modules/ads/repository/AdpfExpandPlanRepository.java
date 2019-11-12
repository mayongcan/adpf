/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.adpf.modules.ads.entity.AdpfExpandPlan;
import com.adpf.modules.ads.repository.custom.AdpfExpandPlanRepositoryCustom;


/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface AdpfExpandPlanRepository extends JpaRepository<AdpfExpandPlan, Long>, JpaSpecificationExecutor<AdpfExpandPlan>, AdpfExpandPlanRepositoryCustom {
	
	
	
}