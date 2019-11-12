/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.ads.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gimplatform.core.annotation.CustomerDateAndTimeDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AdpfExpandPlan数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_expand_plan")
public class AdpfExpandPlan implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 广告计划id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdpfExpandPlanIdGenerator")
	@TableGenerator(name = "AdpfExpandPlanIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADPF_EXPAND_PLAN_PK", allocationSize = 1)
	@Column(name = "PLAN_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long planId;
	
	// 广告计划名称
	@Column(name = "PLAN_NAME", nullable = false, length = 80)
	private String planName;
	
	// 推广目标
	@Column(name = "TARGET_ID", nullable = false, precision = 10, scale = 0)
	private Long targetId;
	
	// 日限额（整数）
	@Column(name = "DAY_MAX", nullable = false, precision = 10, scale = 0)
	private Long dayMax;
	
	// 投放类型
	@Column(name = "EXPAND_TYPE", nullable = false, length = 2)
	private String expandType;
	
	//用户id
	@Column(name="USER_ID",nullable=false,precision = 10, scale = 0)
	private Long userId;
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", nullable = false)
	private Date createDate;
	
	@Column(name="PLAN_STATUS",length=3)
	private String planStatus = "0";
	
	@Column(name="DEL_FLAG",length=2)
	private String delFlag = "0";
}
