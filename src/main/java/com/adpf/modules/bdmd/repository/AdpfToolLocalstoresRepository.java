/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.bdmd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.stereotype.Repository;

import com.adpf.modules.bdmd.entity.AdpfToolLocalstores;
import com.adpf.modules.bdmd.repository.custom.AdpfToolLocalstoresRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface AdpfToolLocalstoresRepository extends JpaRepository<AdpfToolLocalstores, Long>, JpaSpecificationExecutor<AdpfToolLocalstores>, AdpfToolLocalstoresRepositoryCustom {
	
	
	
}