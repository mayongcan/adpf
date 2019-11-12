/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.crowd.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adpf.modules.crowd.entity.DmpCrowdNumberInfo;
import com.adpf.modules.crowd.repository.custom.DmpCrowdNumberInfoRepositoryCustom;


/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface DmpCrowdNumberInfoRepository extends JpaRepository<DmpCrowdNumberInfo, Long>, JpaSpecificationExecutor<DmpCrowdNumberInfo>, DmpCrowdNumberInfoRepositoryCustom {
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from dmp_crowd_number_info "
			+ "WHERE CROWD_ID IN (:crowdId)", nativeQuery = true)
	public void delByCrowdId(@Param("crowdId")String crowdId);

	
	
}