/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adpf.qtn.entity.QtnTaskHanding;
import com.adpf.qtn.repository.custom.QtnTaskHandingRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface QtnTaskHandingRepository extends JpaRepository<QtnTaskHanding, Long>, JpaSpecificationExecutor<QtnTaskHanding>, QtnTaskHandingRepositoryCustom {
	
	/**
	 * 开始办理修改信息
	 * @param isValid
	 * @param idList
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE qtn_task_handing "
			+ "SET STATUS = :status "
			+ "WHERE ORGANIZER_ID = :organizerId AND Date(CREATE_DATE) = :createDate AND FORMAT_NUMBER = :formatNumber", nativeQuery = true)
	public void changeStatus(@Param("status")String status, @Param("organizerId")Long organizerId,@Param("createDate")String createDate,@Param("formatNumber")String formatNumber);


	
	
	/**
	 * 开始办理修改信息
	 * @param isValid
	 * @param idList
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE qtn_task_handing "
			+ "SET STATUS = :status,START_MANAGER_TIME = :startManagerTime "
			+ "WHERE ORGANIZER_ID = :organizerId AND Date(CREATE_DATE) = :createDate AND FORMAT_NUMBER = :formatNumber", nativeQuery = true)
	public void changeStartStatus(@Param("status")String status, @Param("organizerId")Long organizerId,@Param("createDate")String createDate,@Param("formatNumber")String formatNumber,@Param("startManagerTime")Date startManagerTime);


	/**
	 * 结束办理修改信息
	 * @param isValid
	 * @param idList
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE qtn_task_handing "
			+ "SET STATUS = :status,END_MANAGER_TIME = :endManagerTime "
			+ "WHERE ORGANIZER_ID = :organizerId AND Date(CREATE_DATE) = :createDate AND FORMAT_NUMBER = :formatNumber", nativeQuery = true)
	public void changeEndStatus(@Param("status")String status, @Param("organizerId")Long organizerId,@Param("createDate")String createDate,@Param("formatNumber")String formatNumber,@Param("endManagerTime")Date endManagerTime);


	
	/**
	 * 修改为过期信息
	 * @param isValid
	 * @param idList
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE qtn_task_handing "
			+ "SET STATUS = :status "
			+ "WHERE ORGANIZER_ID = :organizerId AND Date(CREATE_DATE) = :createDate AND FORMAT_NUMBER < :formatNumber AND STATUS = '1'", nativeQuery = true)
	public void ModifiedToOverdue (@Param("status")String status, @Param("organizerId")Long organizerId,@Param("createDate")String createDate,@Param("formatNumber")String formatNumber);
	
	
	@Transactional
	@Modifying(clearAutomatically=true)
	@Query(value="select * FROM qtn_task_handing tb " 
			+ "where tb.NUMBER in (:numberList) and tb.STATUS != '2' and Date(tb.CREATE_DATE) = :today and tb.ORGANIZER_ID = :organizerId limit 0,6 ", nativeQuery = true)
	public List<QtnTaskHanding> getDataInList(@Param(value="numberList") List<String> numberList ,@Param(value="today")String today,@Param(value="organizerId")Long organizerId);
	
	
	@Transactional
	@Modifying(clearAutomatically=true)
	@Query(value="select * FROM qtn_task_handing tb " 
			+ "where  tb.STATUS in (:statusList) and tb.OPEN_ID = :openId and Date(tb.CREATE_DATE) = :today ", nativeQuery = true)
	public List<QtnTaskHanding> getListByOpenId(@Param(value="openId") String openId,@Param(value="statusList")List<String> statusList,@Param(value="today")String today);
	
}