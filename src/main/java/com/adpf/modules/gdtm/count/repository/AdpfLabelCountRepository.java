/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.count.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adpf.modules.gdtm.count.entity.AdpfLabelCount;
import com.adpf.modules.gdtm.count.repository.custom.AdpfLabelCountRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface AdpfLabelCountRepository extends JpaRepository<AdpfLabelCount, Long>, JpaSpecificationExecutor<AdpfLabelCount>, AdpfLabelCountRepositoryCustom {
	
	
	
}