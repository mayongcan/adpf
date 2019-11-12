/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.province.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adpf.modules.gdtm.province.entity.AdpfProvinceSize;
import com.adpf.modules.gdtm.province.repository.custom.AdpfProvinceSizeRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface AdpfProvinceSizeRepository extends JpaRepository<AdpfProvinceSize, Long>, JpaSpecificationExecutor<AdpfProvinceSize>, AdpfProvinceSizeRepositoryCustom {
	
	
	
}