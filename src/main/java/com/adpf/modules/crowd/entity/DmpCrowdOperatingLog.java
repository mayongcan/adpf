/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.crowd.entity;

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
 * DmpCrowdInfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dmp_crowd_operating_log")
public class DmpCrowdOperatingLog implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "DmpCrowdOperatingLogIdGenerator")
	@TableGenerator(name = "DmpCrowdOperatingLogIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "DMP_CROWD_OPERATING_LOG_PK", allocationSize = 1)
	@Column(name = "LOG_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long logId;
	
	@Column(name = "LOG_NAME", nullable = false, length = 64)
	private String logName;
	
	@Column(name = "LOG_DETAILS", length =128)
	private String logDetails;
	
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", nullable = false)
	private Date createDate;
	
	@Column(name = "CROWD_ID", precision = 10, scale = 0)
	private Long crowdId;
	
}
