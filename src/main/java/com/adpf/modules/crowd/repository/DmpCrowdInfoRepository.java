/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.crowd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.adpf.modules.crowd.entity.DmpCrowdInfo;
import com.adpf.modules.crowd.repository.custom.DmpCrowdInfoRepositoryCustom;


/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface DmpCrowdInfoRepository extends JpaRepository<DmpCrowdInfo, Long>, JpaSpecificationExecutor<DmpCrowdInfo>, DmpCrowdInfoRepositoryCustom {
	
		
	
	
}