/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.cms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.adpf.modules.cms.entity.AdpfCmsArticle;
import com.adpf.modules.cms.repository.custom.AdpfCmsArticleRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface AdpfCmsArticleRepository extends JpaRepository<AdpfCmsArticle, Long>, JpaSpecificationExecutor<AdpfCmsArticle>, AdpfCmsArticleRepositoryCustom {

	
}