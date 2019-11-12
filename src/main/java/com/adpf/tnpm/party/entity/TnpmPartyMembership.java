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
 * TnpmPartyMembership数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tnpm_party_membership")
public class TnpmPartyMembership implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 党籍ID
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TnpmPartyMembershipIdGenerator")
	@TableGenerator(name = "TnpmPartyMembershipIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "TNPM_PARTY_MEMBERSHIP_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 党籍状态
	@Column(name = "PARTY_STATE", length = 250)
	private String partyState;
	
	// 入党日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "THE_PARTY_DATE")
	private Date thePartyDate;
	
	// 党龄
	@Column(name = "PARTY_STANDING", precision = 10, scale = 0)
	private Long partyStanding;
	
	// 转为正式党员日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "JUST_DATE")
	private Date justDate;
	
	// 预备党员转正情况
	@Column(name = "PROBATIONARY_PARTY_STATUS", length = 500)
	private String probationaryPartyStatus;
	
	// 入党介绍人
	@Column(name = "INTRODUCER", length = 250)
	private String introducer;
	
	// 入党时所在党支部
	@Column(name = "PARTY_BRANCH", length = 250)
	private String partyBranch;
	
	// 进入党支部类型
	@Column(name = "PARTY_BRANCH_TYPE", length = 250)
	private String partyBranchType;
	
	// 离开党组织日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_DEPARTURE_PARTY")
	private Date dateDepartureParty;
	
	// 是否为本单位发展
	@Column(name = "DEVELOPMENT_UNIT", length = 250)
	private String developmentUnit;
	
	// 入党时一线情况
	@Column(name = "PARTY_AFFILIATION", length = 250)
	private String partyAffiliation;
	
	// 出党原因
	@Column(name = "THE_PARTY_REASON", length = 250)
	private String thePartyReason;
	
	// 加入党组织的类别
	@Column(name = "JOIN_PARTY_ORGANIZATION", length = 250)
	private String joinPartyOrganization;
	
	// 离开党组织的类别
	@Column(name = "OUT_PARTY_ORGANIZATION", length = 250)
	private String outPartyOrganization;
	
	// 回复党员日期
	@Column(name = "RETURN_DATE", length = 250)
	private String returnDate;
	
		// 回复党员日期
		@Column(name = "PEOPLE_ID", length = 250)
		private String peopleId;
	
	
}
