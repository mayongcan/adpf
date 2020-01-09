/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec.SELECT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adpf.tracking.entity.TPromoCurr;
import com.adpf.tracking.repository.custom.TPromoCurrRepositoryCustom;
import com.hp.hpl.sparta.xpath.TrueExpr;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface TPromoCurrRepository extends JpaRepository<TPromoCurr, Long>, JpaSpecificationExecutor<TPromoCurr>, TPromoCurrRepositoryCustom {
	
//	@Query(value = "UPDATE t_app_register a,t_click b,(SELECT MIN(t_click.create_time) as \"min_tiem\" FROM t_click LEFT JOIN t_app_register ON t_click.ip = t_app_register.ip AND t_click.imei = t_app_register.imei )as c \n" +
//			"set a.attribution_promo_id = b.promo_id,a.attribution_time =:dateTime" +
//			" WHERE a.ip = b.ip AND a.imei = b.imei AND b.create_time = c.min_tiem ",nativeQuery = true)
//	public  String updateAppRegister(@Param("dateTime")String dateTime);
	
	
	//注册表更新归因推广活动id，归因时间
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value ="UPDATE t_app_register a,t_click b,(SELECT MIN(t_click.create_time) as \"min_tiem\" ,t_click.ip FROM t_app_register LEFT JOIN t_click ON CASE WHEN t_app_register.imei IS NOT NULL THEN t_click.imei = t_app_register.imei ELSE t_click.ip = t_app_register.ip END GROUP BY t_app_register.ip )as c \n" +
			"set a.attribution_promo_id = b.promo_id,a.attribution_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i'),b.attribution_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i')\n" +
			" WHERE CASE WHEN a.imei is NOT NULL THEN a.imei = b.imei ELSE a.ip = b.ip END AND b.create_time = c.min_tiem AND b.ip = c.ip AND a.when1 BETWEEN date_add(:dateTime, interval - 5 minute) AND :dateTime ;" ,nativeQuery = true)
	public  void updateAppRegister(@Param("dateTime")String dateTime);
	
	
	//登录表更新归因推广活动id，归因时间
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value ="UPDATE t_app_login a,t_click b,(SELECT MIN(t_click.create_time) as \"min_tiem\" ,t_click.ip FROM t_click LEFT JOIN t_app_login ON CASE WHEN t_app_login.imei IS NOT NULL THEN t_click.imei = t_app_login.imei ELSE t_click.ip = t_app_login.ip END GROUP BY t_click.ip )as c \n" +
				"set a.attribution_promo_id = b.promo_id,a.attribution_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i'),b.attribution_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i')\n" +
				" WHERE CASE WHEN a.imei is NOT NULL THEN a.imei = b.imei ELSE a.ip = b.ip END AND b.create_time = c.min_tiem AND b.ip = c.ip AND a.when1 BETWEEN date_add(:dateTime, interval - 5 minute) AND :dateTime ;" ,nativeQuery = true)
	public  void updateAppLogin(@Param("dateTime")String dateTime);
	
	


	//支付表更新归因推广活动id，归因时间
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE t_app_pay a,t_click b,(SELECT MIN(t_click.create_time) as \"min_tiem\" ,t_click.ip FROM t_click LEFT JOIN t_app_pay ON CASE WHEN t_app_pay.imei IS NOT NULL THEN t_click.imei = t_app_pay.imei ELSE t_click.ip = t_app_pay.ip END GROUP BY t_click.ip)as c \n" +
			"set a.attribution_promo_id = b.promo_id,a.attribution_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i')\n" +
			" WHERE CASE WHEN a.imei is NOT NULL THEN a.imei = b.imei ELSE a.ip = b.ip END AND b.create_time = c.min_tiem AND b.ip = c.ip AND a.when1 BETWEEN date_add(:dateTime, interval - 5 minute) AND :dateTime ;" ,nativeQuery = true)
	public void updateAppPay(@Param("dateTime")String dateTime);
	
	//激活表更新归因推广活动id，归因时间
	//点击表更新归因时间
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE t_app_startup a,t_click b\n" +
			"set a.attribution_promo_id = b.promo_id,a.attribution_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i'),b.attribution_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i') where CASE WHEN a.imei is NOT NULL THEN a.imei = b.imei ELSE a.ip = b.ip END  AND a.when1 BETWEEN date_add(:dateTime, interval - 5 minute) AND :dateTime ;",nativeQuery = true)
	public void updateAppStartup(@Param("dateTime")String dateTime) ;
	
	
	//
	@Transactional
	@Modifying(clearAutomatically = true)
	//@Query(value = "INSERT INTO t_promo_curr SET ods_time = :dateTime")
	@Query(value = "INSERT INTO t_promo_curr(promo_id,channel_id,app_id,agent_id,ods_time) SELECT tb.id,tb.channel_id,tb.app_id,tb.anent_id,DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i') from t_promo tb",nativeQuery = true)
	public void insertPromoCurr(@Param("dateTime")String dateTime);
	
	//统计推广表
	//更新点击数，有效点击数，排重点击数，按天排重点击数字段
	@Transactional
	@Modifying(clearAutomatically = true)
	
	
	@Query(value = "UPDATE t_promo_curr a,(SELECT promo_id,COUNT(tck.ip) as 'click',COUNT(IF(tck.imei = NULL,0,tck.imei)) as 'click_valid', COUNT(DISTINCT tck.ip) as 'click_distinct',COUNT(DISTINCT tck.ip,DATE_FORMAT(tck.create_time,'%y-%m-%d %H:%i')) as 'click_distinct_day',attribution_time\n" +
			"FROM t_click tck  WHERE tck.create_time between date_add(:dateTime, interval - 5 minute) and :dateTime  GROUP BY tck.promo_id) b\n" +
			"SET a.click = b.click,a.click_valid = b.click_valid,a.click_distinct = b.click_distinct,a.click_distinct_day = b.click_distinct_day\n" +
			"WHERE a.promo_id = b.promo_id AND a.ods_time =DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i')",nativeQuery = true)
	public void updatePromoClick(@Param("dateTime")String dateTime);
	
	//更新排重激活设备数，当天激活且排重设备数
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE t_promo_curr a,(SELECT COUNT(DISTINCT tas.ip) as 'active_distinct',COUNT(IF(tar.ip =NULL,0,tar.ip)) as 'active_register',tas.attribution_promo_id as 'attribution_promo_id',tas.attribution_time as 'attribution_time' \n" +
			"FROM t_app_startup tas LEFT JOIN t_app_register tar\n" +
			"ON DATE_FORMAT(tas.when1,'%y-%m-%d') = DATE_FORMAT(tar.when1,'%y-%m-%d')\n" +
			"and tar.ip = tas.ip and tar.imei = tas.imei\n" +
			"WHERE tas.attribution_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i')\n" +
			"GROUP BY tas.attribution_promo_id) as b\n" +
			"SET a.active_distinct = b.active_distinct,a.active_register = b.active_register\n" +
			"WHERE a.promo_id = b.attribution_promo_id AND a.ods_time = b.attribution_time",nativeQuery = true)
	public void updatePromoActive(@Param("dateTime")String dateTime);
	
	
	//更新按天排重注册设备数，排重注册设备数
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE t_promo_curr a ,(SELECT COUNT(DISTINCT ip,DATE_FORMAT(t_app_register.when1,'%y-%m-%d')) as 'register_distinct_day',COUNT(DISTINCT ip) as 'register_distinct',attribution_promo_id ,attribution_time FROM t_app_register \n" +
			"WHERE attribution_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i') GROUP BY attribution_promo_id )b\n" +
			"SET a.register_distinct_day = b.register_distinct_day ,a.register_distinct = b.register_distinct\n" +
			"WHERE a.promo_id = b.attribution_promo_id AND a.ods_time = b.attribution_time",nativeQuery = true)
	public void updatePromoRegister(@Param("dateTime")String dateTime);
	
	
	//更新按天排重登录设备数
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE t_promo_curr a ,(SELECT COUNT(DISTINCT ip,DATE_FORMAT(t_app_login.when1,'%y-%m-%d')) as 'login_day',attribution_promo_id ,attribution_time FROM t_app_login \n" +
			"WHERE attribution_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i') GROUP BY attribution_promo_id )b\n" +
			"SET a.login_day = b.login_day\n" +
			"WHERE a.promo_id = b.attribution_promo_id AND a.ods_time = b.attribution_time",nativeQuery = true)
	public void updatePromoLogin(@Param("dateTime")String dateTime);
	
	
	
	//更新新增付费，新增付费设备数
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE t_promo_curr a,(SELECT SUM(free) as 'new_fee',COUNT(tap.ip) as 'pay_device',attribution_promo_id ,attribution_time FROM (SELECT ip,imei,free,attribution_promo_id,attribution_time FROM t_app_pay WHERE t_app_pay.attribution_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i')) tap LEFT JOIN \n" +
			"(SELECT ip,imei from t_app_pay WHERE t_app_pay.attribution_time < DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i')) tap2\n" +
			"ON tap.ip = tap2.ip AND tap.imei = tap2.imei \n" +
			" WHERE tap2.ip is  NULL AND tap2.imei IS NULL GROUP BY tap.attribution_promo_id) b\n" +
			"SET a.new_fee = b.new_fee ,a.pay_device = b.pay_device\n" +
			"WHERE a.promo_id = b.attribution_promo_id AND a.ods_time = b.attribution_time",nativeQuery = true)
	public void updatePromoNewPay(@Param("dateTime")String dateTime);
	
	//更新总付费，总付费设备数
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE t_promo_curr a,(SELECT SUM(free) as 'total_fee',COUNT(DISTINCT ip) as 'total_pay_device',attribution_promo_id,attribution_time\n" +
			" from t_app_pay tap WHERE tap.attribution_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i')  GROUP BY tap.attribution_promo_id )b \n" +
			"SET a.total_fee = b.total_fee,a.total_pay_device = b.total_pay_device\n" +
			"WHERE a.promo_id = b.attribution_promo_id AND a.ods_time = b.attribution_time ",nativeQuery = true)
	public void updatePromoAllPay(@Param("dateTime")String dateTime);
	
	//以下为统计自然量的修改操作
	@Transactional
	@Modifying(clearAutomatically = true)
	//@Query(value = "INSERT INTO t_promo_curr SET ods_time = :dateTime")
	@Query(value = "INSERT INTO t_promo_curr(promo_id,channel_id,app_id,agent_id,ods_time) value (0,0,0,0,DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i'))",nativeQuery = true)
	public void insertNature(@Param("dateTime")String dateTime);
	
	//更新排重激活设备数，当天激活且排重设备数
		@Transactional
		@Modifying(clearAutomatically = true)
		@Query(value = "UPDATE t_promo_curr a,(SELECT COUNT(DISTINCT tas.ip) as 'active_distinct',COUNT(IF(tar.ip =NULL,0,tar.ip)) as 'active_register',tas.attribution_promo_id as 'attribution_promo_id',tas.attribution_time as 'attribution_time' \n" +
				"FROM t_app_startup tas LEFT JOIN t_app_register tar\n" +
				"ON DATE_FORMAT(tas.when1,'%y-%m-%d') = DATE_FORMAT(tar.when1,'%y-%m-%d')\n" +
				"and tar.ip = tas.ip and tar.imei = tas.imei\n" +
				"WHERE tas.when1 between date_add(:dateTime, interval - 5 minute) and :dateTime \n" +
				"AND tas.attribution_promo_id is null) as b\n" +
				"SET a.active_distinct = b.active_distinct,a.active_register = b.active_register\n" +
				"WHERE a.promo_id = 0 AND a.ods_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i') ",nativeQuery = true)
		public void updateNaPromoActive(@Param("dateTime")String dateTime);
		
		//更新按天排重注册设备数，排重注册设备数
		@Transactional
		@Modifying(clearAutomatically = true)
		@Query(value = "UPDATE t_promo_curr a ,(SELECT COUNT(DISTINCT ip,DATE_FORMAT(t_app_register.when1,'%y-%m-%d')) as 'register_distinct_day',COUNT(DISTINCT ip) as 'register_distinct',attribution_promo_id ,attribution_time FROM t_app_register \n" +
				"WHERE t_app_register.when1 between date_add(:dateTime, interval - 5 minute) and :dateTime AND t_app_register.attribution_promo_id is null )b\n" +
				"SET a.register_distinct_day = b.register_distinct_day ,a.register_distinct = b.register_distinct\n" +
				"WHERE a.promo_id = 0 AND a.ods_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i')",nativeQuery = true)
		public void updateNaPromoRegister(@Param("dateTime")String dateTime);
		
	
		//更新按天排重登录设备数
		@Transactional
		@Modifying(clearAutomatically = true)
		@Query(value = "UPDATE t_promo_curr a ,(SELECT COUNT(DISTINCT ip,DATE_FORMAT(t_app_login.when1,'%y-%m-%d')) as 'login_day',attribution_promo_id ,attribution_time FROM t_app_login \n" +
				"WHERE t_app_login.when1 between date_add(:dateTime, interval - 5 minute) and :dateTime AND t_app_login.attribution_promo_id is null)b\n" +
				"SET a.login_day = b.login_day\n" +
				"WHERE a.promo_id = 0 AND a.ods_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i')",nativeQuery = true)
		public void updateNaPromoLogin(@Param("dateTime")String dateTime);
		
		
		
		/*//更新新增付费，新增付费设备数
		@Transactional
		@Modifying(clearAutomatically = true)
		@Query(value = "UPDATE t_promo_curr a,(SELECT SUM(free) as 'new_fee',COUNT(tap.ip) as 'pay_device',attribution_promo_id ,attribution_time FROM (SELECT ip,imei,free,attribution_promo_id,attribution_time FROM t_app_pay WHERE t_app_pay.attribution_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i')) tap LEFT JOIN \n" +
				"(SELECT ip,imei from t_app_pay WHERE t_app_pay.attribution_time < DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i')) tap2\n" +
				"ON tap.ip = tap2.ip AND tap.imei = tap2.imei \n" +
				" WHERE tap2.ip is  NULL AND tap2.imei IS NULL GROUP BY tap.attribution_promo_id) b\n" +
				"SET a.new_fee = b.new_fee ,a.pay_device = b.pay_device\n" +
				"WHERE a.promo_id = b.attribution_promo_id AND a.ods_time = b.attribution_time",nativeQuery = true)
		public void updatePromoNewPay(@Param("dateTime")String dateTime);*/
		
		//更新总付费，总付费设备数
		@Transactional
		@Modifying(clearAutomatically = true)
		@Query(value = "UPDATE t_promo_curr a,(SELECT SUM(free) as 'total_fee',COUNT(DISTINCT ip) as 'total_pay_device',attribution_promo_id,attribution_time\n" +
				" from t_app_pay tap WHERE tap.when1 between date_add(:dateTime, interval - 5 minute) and :dateTime AND tap.attribution_promo_id is null)b \n" +
				"SET a.total_fee = b.total_fee,a.total_pay_device = b.total_pay_device\n" +
				"WHERE a.promo_id = 0 AND a.ods_time = DATE_FORMAT(:dateTime,'%y-%m-%d %H:%i')",nativeQuery = true)
		public void updateNaPromoAllPay(@Param("dateTime")String dateTime);
	
	
	
	
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE from t_promo_curr WHERE click is NULL AND click_valid is NULL AND click_distinct is NULL and click_distinct_day is null AND active_distinct is NULL AND dau is NULL AND active_register is NULL and register_distinct is NULL AND register_distinct_day is NULL AND login_day is NULL AND range_fee is NULL AND new_fee is NULL AND pay_device is NULL AND total_fee is NULL AND total_pay_device is NULL \n" +
			"AND DATE_FORMAT(ods_time,'%Y-%m-%d') = CURDATE()",nativeQuery = true)
	public void deleteEveyrDay();
	
	
	
	
	
	
	
	
	
	
	
		
	
	
	
	
	
}
