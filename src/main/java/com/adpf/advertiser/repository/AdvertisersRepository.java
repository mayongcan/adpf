/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.advertiser.repository;

import com.adpf.advertiser.entity.Advertisers;
import com.adpf.advertiser.repository.custom.AdvertisersRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface AdvertisersRepository extends JpaRepository<Advertisers, Long>, JpaSpecificationExecutor<Advertisers>, AdvertisersRepositoryCustom {


    /**
     *
     *更新金额
     *
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE advertisers ad SET ad.adv_balance =:balance  "
            + "where ad.id = :id ", nativeQuery = true)
    public void udpateBalance(@Param("id") String id, @Param("balance") BigDecimal balance);
	
}