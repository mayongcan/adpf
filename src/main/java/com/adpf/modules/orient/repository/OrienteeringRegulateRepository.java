/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.orient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.adpf.modules.orient.entity.OrienteeringRegulate;
import com.adpf.modules.orient.repository.custom.OrienteeringRegulateRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface OrienteeringRegulateRepository extends JpaRepository<OrienteeringRegulate, Long>, JpaSpecificationExecutor<OrienteeringRegulate>, OrienteeringRegulateRepositoryCustom {
	
	
	
}