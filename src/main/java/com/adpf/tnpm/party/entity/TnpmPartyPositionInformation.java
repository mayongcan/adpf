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
 * TnpmPartyPositionInformation数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tnpm_party_position_information")
public class TnpmPartyPositionInformation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 职务ID
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TnpmPartyPositionInformationIdGenerator")
	@TableGenerator(name = "TnpmPartyPositionInformationIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "TNPM_PARTY_POSITION_INFORMATION_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 任职党组织名称
	@Column(name = "NAME_PARTY_ORGANIZATION", length = 250)
	private String namePartyOrganization;
	
	// 党内职务名称
	@Column(name = "PARTY_TITLE", length = 250)
	private String partyTitle;
	
	// 职务级别
	@Column(name = "DUTY_LEVEL", length = 250)
	private String dutyLevel;
	
	// 该届起始日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BEGINNING_DATE_SESSION")
	private Date beginningDateSession;
	
	// 离职原因
	@Column(name = "LEAVE_REASON", length = 250)
	private String leaveReason;
	
	// 离职日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LEAVE_DATE")
	private Date leaveDate;
	
	// 党内职务说明
	@Column(name = "PARTY_JOB_DESCRIPTION", length = 250)
	private String partyJobDescription;
	
	// 离职方式
	@Column(name = "EXIT_WAY", length = 250)
	private String exitWay;
	
	// 人员ID
	@Column(name = "PEOPLE_ID", precision = 10, scale = 0)
	private Long peopleId;
	
}
