/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adpf.tracking.entity.TApp;
import com.adpf.tracking.repository.custom.TAppRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface TAppRepository extends JpaRepository<TApp, Long>, JpaSpecificationExecutor<TApp>, TAppRepositoryCustom {
	
	
	
}