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
 * TnpmLeaveAbroad数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tnpm_leave_abroad")
public class TnpmLeaveAbroad implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TnpmLeaveAbroadIdGenerator")
	@TableGenerator(name = "TnpmLeaveAbroadIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "TNPM_LEAVE_ABROAD_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 出国国家名称
	@Column(name = "NATION_NAME", length = 250)
	private String nationName;
	
	// 出国日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ABROAD_DATE")
	private Date abroadDate;
	
	// 出国目的
	@Column(name = "ABROAD_GOAL", length = 250)
	private String abroadGoal;
	
	// 出国党员与党组织联系情况
	@Column(name = "CONTACT_PARTY", length = 250)
	private String contactParty;
	
	// 党籍处理方式
	@Column(name = "PARTY_MEMBERSHIP_TREATMENT", length = 250)
	private String partyMembershipTreatment;
	
	// 申请保留党籍时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPLY_RETENTION_TIME")
	private Date applyRetentionTime;
	
	// 回国情况
	@Column(name = "HOME_SITUATION", length = 250)
	private String homeSituation;
	
	// 回国日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RETURN_DATE")
	private Date returnDate;
	
	// 恢复组织生活情况
	@Column(name = "RESPOND_ORGANIZATIONAL_LIFE", length = 250)
	private String respondOrganizationalLife;
	
	// 申请恢复组织生活日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPLY_RESUME_ORGANIZATIONAL_DATE")
	private Date applyResumeOrganizationalDate;
	
	// 批准恢复组织生活日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RATIFY_RESUME_ORGANIZATIONAL_DATE")
	private Date ratifyResumeOrganizationalDate;
	
	// 组织关系是否出国时转国外
	@Column(name = "WHETHER_ORGANIZATION_FOREIGN", length = 250)
	private String whetherOrganizationForeign;
	
	// 出过事项说明
	@Column(name = "ABROAD_MATTERS", length = 250)
	private String abroadMatters;
	
	// 人员ID
	@Column(name = "PEOPLE_ID", precision = 10, scale = 0)
	private Long peopleId;
	
}
