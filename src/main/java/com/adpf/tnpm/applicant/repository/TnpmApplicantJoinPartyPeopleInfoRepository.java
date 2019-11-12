/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.applicant.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adpf.tnpm.applicant.entity.TnpmApplicantJoinPartyPeopleInfo;
import com.adpf.tnpm.applicant.repository.custom.TnpmApplicantJoinPartyPeopleInfoRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface TnpmApplicantJoinPartyPeopleInfoRepository extends JpaRepository<TnpmApplicantJoinPartyPeopleInfo, Long>, JpaSpecificationExecutor<TnpmApplicantJoinPartyPeopleInfo>, TnpmApplicantJoinPartyPeopleInfoRepositoryCustom {
	
	/**
	 * 删除信息（将信息的IS_VALID设置为1）
	 * @param isValid
	 * @param idList
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE tnpm_join_party_people_info tb "
			+ "SET tb.DEL_FLAG= 1 "
			+ "WHERE PEOPLE_ID = :peopleId", nativeQuery = true)
	public void delEntity( @Param("peopleId")Long peopleId);
	
}