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
 * TnpmTrainDevelopInfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tnpm_train_develop_info")
public class TnpmTrainDevelopInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 培养发展ID
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TnpmTrainDevelopInfoIdGenerator")
	@TableGenerator(name = "TnpmTrainDevelopInfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "TNPM_TRAIN_DEVELOP_INFO_PK", allocationSize = 1)
	@Column(name = "DEVELOP_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long developId;
	
	// 入党日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "JOIN_DATE")
	private Date joinDate;
	
	// 入党积极分子日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "JOIN_ACTIVIE_DATE")
	private Date joinActivieDate;
	
	// 向组织汇报时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REPORT_DATE")
	private Date reportDate;
	
	// 考察时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSPECT_DATE")
	private Date inspectDate;
	
	// 确定发展对象党小组讨论日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DISCUSS_DATE")
	private Date discussDate;
	
	// 列为发展对象日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "IS_DEVELOP_DATE")
	private Date isDevelopDate;
	
	// 确定发展对象党支部书记
	@Column(name = "BRANCH_SECRETARY", length = 32)
	private String branchSecretary;
	
	// 发展联系人
	@Column(name = "DEVELOP_LINKMAN", length = 32)
	private String developLinkman;
	
	// 入党介绍人
	@Column(name = "INTRODUCER", length = 32)
	private String introducer;
	
	// 集中培训日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRAIN_DATE")
	private Date trainDate;
	
	// 发展党员上集组织预审日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INQUIRY_DATE")
	private Date inquiryDate;
	
	// 支部大会通过入党日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PASS_DATE")
	private Date passDate;
	
	// 发展党员上级组织激活日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTIVIE_DATE")
	private Date activieDate;
	
	// 发展党员谈话人
	@Column(name = "TALKER", length = 32)
	private String talker;
	
	// 党员转正上级审批日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPROVE_DATE")
	private Date approveDate;
	
	// 党员转正申请日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPROVE_APPLY_DATE")
	private Date approveApplyDate;
	
	// 党员转正党小组讨论日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TEAM_TALK_DATE")
	private Date teamTalkDate;
	
	// 党员转正支部大会讨论日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPROVE_BRANCH_TALK_DATE")
	private Date approveBranchTalkDate;
	
	// 党员转正党支部书记
	@Column(name = "APPROVE_BRANCH_SECRETARY", length = 32)
	private String approveBranchSecretary;
	
	// 党员转正上集审批组织
	@Column(name = "APPROVAL_ORGANIZATION", length = 32)
	private String approvalOrganization;
	
	// 积极分子受组织教育情况
	@Column(name = "ACTIVIST_EDUCATION_SITUATION", length = 2000)
	private String activistEducationSituation;
	
	// 向组织汇报内容
	@Column(name = "REPORT_DETAILS", length = 2000)
	private String reportDetails;
	
	// 人员ID
	@Column(name = "PEOPLE_ID", precision = 10, scale = 0)
	private Long peopleId;
	
	// 考查组织
	@Column(name = "INSPECTION_ORGANIZATION", length = 256)
	private String inspectionOrganization;
	
}
