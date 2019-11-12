/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.adpf.modules.cms.entity.AdpfCmsCategory;
import com.adpf.modules.cms.repository.custom.AdpfCmsCategoryRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface AdpfCmsCategoryRepository extends JpaRepository<AdpfCmsCategory, Long>, JpaSpecificationExecutor<AdpfCmsCategory>, AdpfCmsCategoryRepositoryCustom {
	
	
	
}