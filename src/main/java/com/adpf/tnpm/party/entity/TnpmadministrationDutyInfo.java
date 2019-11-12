/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
 * TnpmadministrationDutyInfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tnpm_administration_duty_info")
public class TnpmadministrationDutyInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 职务ID
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TnpmadministrationDutyInfoIdGenerator")
	@TableGenerator(name = "TnpmadministrationDutyInfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "TNPM_ADMINISTRATION_DUTY_INFO_PK", allocationSize = 1)
	@Column(name = "DUTY_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long dutyId;
	
	// 任职机构名称
	@Column(name = "ORGANIZATION_NAME", length = 32)
	private String organizationName;
	
	// 职务名称
	@Column(name = "DUTY_NAME", length = 32)
	private String dutyName;
	
	// 职务级别
	@Column(name = "DUTY_LEVEL", length = 10)
	private String dutyLevel;
	
	// 职务任职起始日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DUTY_START_DATE")
	private Date dutyStartDate;
	
	// 离职原因
	@Column(name = "LEAVE_REASON", length = 512)
	private String leaveReason;
	
	// 离职日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LEAVE_DATE")
	private Date leaveDate;
	
	// 行政职务说明
	@Column(name = "DUTY_DETAILS", length = 2000)
	private String dutyDetails;
	
	// 人员ID
	@Column(name = "PEOPLE_ID", precision = 10, scale = 0)
	private Long peopleId;
	
}
