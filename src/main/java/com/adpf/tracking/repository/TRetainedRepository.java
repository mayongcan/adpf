/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adpf.tracking.entity.TRetained;
import com.adpf.tracking.repository.custom.TRetainedRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface TRetainedRepository extends JpaRepository<TRetained, Long>, JpaSpecificationExecutor<TRetained>, TRetainedRepositoryCustom {
	
	@Transactional
	@Modifying(clearAutomatically = true)	
	@Query(value = "insert into t_retained(promo_id,app_type,imei,idfa,create_time,login_count)  select attribution_promo_id,app_type,imei,idfa,create_time,0 from t_app_register tar  "
	+ "where date(tar.create_time) = DATE_SUB(curdate(),INTERVAL 1 DAY) ", nativeQuery = true)
	public void insertRe();
		
	@Transactional
	@Modifying(clearAutomatically = true)	
	@Query(value = "update t_retained trd,(select * from t_app_login where date(create_time)=current_date() group by imei,idfa) tal set trd.login_count = login_count + 1,trd.last_login_time = tal.when1 "
	+ " where case trd.app_type when 'Android' then trd.imei = tal.imei else trd.idfa = tal.idfa end", nativeQuery = true)		
	public void updateRe();
	
	
	
}