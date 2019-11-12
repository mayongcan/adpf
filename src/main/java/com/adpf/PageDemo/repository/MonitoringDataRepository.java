/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.PageDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.adpf.PageDemo.entity.monitoringData;
import com.adpf.PageDemo.repository.custom.MonitoringDataRepositoryCustom;


/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface MonitoringDataRepository extends JpaRepository<monitoringData, Long>, JpaSpecificationExecutor<monitoringData>, MonitoringDataRepositoryCustom {
	
	
	
}