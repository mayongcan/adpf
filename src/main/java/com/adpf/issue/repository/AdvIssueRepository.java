/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.issue.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adpf.issue.entity.AdvIssue;
import com.adpf.issue.repository.custom.AdvIssueRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface AdvIssueRepository extends JpaRepository<AdvIssue, Long>, JpaSpecificationExecutor<AdvIssue>, AdvIssueRepositoryCustom {
	
	
	
}