/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adpf.qtn.entity.QtnTakeNumberInfo;
import com.adpf.qtn.repository.custom.QtnTakeNumberInfoRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface QtnTakeNumberInfoRepository extends JpaRepository<QtnTakeNumberInfo, Long>, JpaSpecificationExecutor<QtnTakeNumberInfo>, QtnTakeNumberInfoRepositoryCustom {
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE qtn_take_number_info tb "
			+ "SET tb.NUMBER= '000', tb.VIP_NUMBER = '000' ", nativeQuery = true)
	public void changeNumber();
	
}