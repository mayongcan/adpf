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

import com.adpf.tracking.entity.TPromo;
import com.adpf.tracking.repository.custom.TPromoRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface TPromoRepository extends JpaRepository<TPromo, Long>, JpaSpecificationExecutor<TPromo>, TPromoRepositoryCustom {
	
	
	@Query(value = "select GEN_VALUE from sys_tb_generator where GEN_NAME = 'T_PROMO_PK'",nativeQuery = true)
	public  String getValueId();
	
	@Query(value = "SELECT CAHNNEL_TYPE FROM t_channelurl WHERE NAME = :name ",nativeQuery = true)
	public String getChannelType(@Param("name")String name);
	
	
}