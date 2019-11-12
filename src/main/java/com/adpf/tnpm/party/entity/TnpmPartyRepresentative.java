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
 * TnpmPartyRepresentative数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tnpm_party_representative")
public class TnpmPartyRepresentative implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 党代表ID
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TnpmPartyRepresentativeIdGenerator")
	@TableGenerator(name = "TnpmPartyRepresentativeIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "TNPM_PARTY_REPRESENTATIVE_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 党代表情况
	@Column(name = "STATUS_PARTY_REPRESENTATIVES", length = 250)
	private String statusPartyRepresentatives;
	
	// 届次
	@Column(name = "FOR_THE_TIME", precision = 10, scale = 0)
	private Long forTheTime;
	
	// 该届起始日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BEGINNING_DATE_SESSION")
	private Date beginningDateSession;
	
	// 该届满届日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "THE_EXPIRY_DATE")
	private Date theExpiryDate;
	
	// 人员ID
	@Column(name = "PEOPLE_ID", precision = 10, scale = 0)
	private Long peopleId;
	
}
