/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adpf.qtn.entity.QtnWindowInfo;
import com.adpf.qtn.repository.custom.QtnWindowInfoRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface QtnWindowInfoRepository extends JpaRepository<QtnWindowInfo, Long>, JpaSpecificationExecutor<QtnWindowInfo>, QtnWindowInfoRepositoryCustom {
	
	/**
	 * 修改状态和当前号
	 * @param isValid
	 * @param idList
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE qtn_window_info "
			+ "SET STATUS = :status , CURRENT_NUMBER = :currentNumber "
			+ "WHERE USER_ID = :userId ", nativeQuery = true)
	public void changeStatus(@Param("status")String status, @Param("userId")Long userId, @Param("currentNumber")String currentNumber);

	/**
	 * 将已绑定用户的窗口用户取消绑定
	 * @param isValid
	 * @param idList
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE qtn_window_info "
			+ "SET USER_ID = null "
			+ "WHERE USER_ID = :userId", nativeQuery = true)
	public void changeUser(@Param("userId")Long userId);
	
	
	/**
	 * 修改所有窗口的状态和当前号
	 * @param isValid
	 * @param idList
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE qtn_window_info "
			+ "SET STATUS = '2' , CURRENT_NUMBER = null ", nativeQuery = true)
	public void changeWindowStatusAndNumber();

}