/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.openam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adpf.modules.openam.entity.AdpfOpenamFreepawdlog;
import com.adpf.modules.openam.repository.custom.AdpfOpenamFreepawdlogRepositoryCustom;

/**
 * 实体资源类
 * 
 * @version 1.0
 * @author
 *
 */
@Repository
public interface AdpfOpenamFreepawdlogRepository extends JpaRepository<AdpfOpenamFreepawdlog, Long>,
		JpaSpecificationExecutor<AdpfOpenamFreepawdlog>, AdpfOpenamFreepawdlogRepositoryCustom {

	/**
	 * 删除CategoryRecur
	 * 
	 * @param categoryChildId
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE adpf_openam_freepawdlog set MOBILE =:mobile , status='2'  "
			+ "WHERE AES_CACHE_KEY = :aesCacheKey ", nativeQuery = true)
	public void udpateMobile(@Param("mobile") String mobile, @Param("aesCacheKey") String aesCacheKey);

	/**
	 * 根据父ID列表获取ID列表
	 * 
	 * @param idList
	 * @return
	 */
	@Query(value = "SELECT * FROM adpf_openam_freepawdlog WHERE CODE=:code ", nativeQuery = true)
	public AdpfOpenamFreepawdlog getByCode(@Param("code") String code);

}