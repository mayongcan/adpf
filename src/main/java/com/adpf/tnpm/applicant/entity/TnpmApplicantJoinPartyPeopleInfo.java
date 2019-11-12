/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.applicant.entity;

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
 * TnpmJoinPartyPeopleInfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tnpm_join_party_people_info")
public class TnpmApplicantJoinPartyPeopleInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 人员ID
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TnpmJoinPartyPeopleInfoIdGenerator")
	@TableGenerator(name = "TnpmJoinPartyPeopleInfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "TNPM_JOIN_PARTY_PEOPLE_INFO_PK", allocationSize = 1)
	@Column(name = "PEOPLE_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long peopleId;
	
	// 姓名
	@Column(name = "NAME", length = 32)
	private String name;
	
	// 身份证号码
	@Column(name = "ID_CARD", length = 32)
	private String idCard;
	
	// 民族
	@Column(name = "NATION", length = 32)
	private String nation;
	
	// 年龄
	@Column(name = "AGE", precision = 10, scale = 0)
	private Long age;
	
	// 人员类别
	@Column(name = "PEOPLE_TYPE", length = 10)
	private String peopleType;
	
	// 所在党支部
	@Column(name = "PARTY_BRANCH", length = 10)
	private String partyBranch;
	
	// 信息完整度
	@Column(name = "INFO_COMPLETION", length = 10)
	private String infoCompletion;
	
	// 出生日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BRITHDAY")
	private Date brithday;
	
	// 学历
	@Column(name = "EDUCATION", length = 10)
	private String education;
	
	// 工作岗位
	@Column(name = "PLACE_OF_WORK", length = 16)
	private String placeOfWork;
	
	// 手机号码
	@Column(name = "PHONE", length = 11)
	private String phone;
	
	// 区号
	@Column(name = "AREA_CODE", length = 10)
	private String areaCode;
	
	// 固定电话
	@Column(name = "TELEPHONE", length = 10)
	private String telephone;
	
	// 籍贯
	@Column(name = "NATIVE_PLACE", length = 32)
	private String nativePlace;
	
	// 是否台湾籍贯
	@Column(name = "If_TAIWAN", length = 2)
	private String ifTAIWAN;
	
	// 婚姻状况
	@Column(name = "MARITAL_STATUS", length = 6)
	private String maritalStatus;
	
	// 参加工作日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "JOIN_WORK_DATE")
	private Date joinWorkDate;
	
	// 档案管理单位名称
	@Column(name = "INFO_MANAGEMENT_UNIT_NAME", length = 32)
	private String infoManagementUnitName;
	
	// 聘任专业技术职务名称
	@Column(name = "WORK_NAME", length = 32)
	private String workName;
	
	// 一线情况
	@Column(name = "SITUATION", length = 64)
	private String situation;
	
	// 新社会阶层类型
	@Column(name = "STRATUM_TYPE", length = 10)
	private String stratumType;
	
	// 是否是农名工
	@Column(name = "IF_MIGRANT_WORKERS", length = 2)
	private String ifMigrantWorkers;
	
	// 党员培训情况
	@Column(name = "PARTY_MEMBER_TRAINING_SITUATION", length = 10)
	private String partyMemberTrainingSituation;
	
	// 头像
	@Column(name = "IMAGE", length = 128)
	private String image;
	
	// 删除标记
	@Column(name = "DEL_FLAG", length = 2)
	private String delFlag = "0";
	
	// 人员状态
	@Column(name = "PARTY_TYPE", length = 2)
	private String partyType;
	
	// 性别
	@Column(name = "GENDER", length = 3)
	private String gender;
	
	// 加入党组织日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_PARTY_TIME")
	private Date startPartyTime;
	
	// 转为正式党员日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BECAME_PARTY_TIME")
	private Date becamePartyTime;
	
	// 党籍状态
	@Column(name = "PARTY_STATE", length = 32)
	private String partyState;
	
	// 是否为失联党员
	@Column(name = "MISSING_PARTY", length = 2)
	private String missingParty;
	
	// 失联日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MISSING_DATE")
	private Date missingDate;
	
	// 是否为流动党员
	@Column(name = "FLOW_PARTY", length = 2)
	private String flowParty;
	
	// 外出流向
	@Column(name = "FLOW_DIRECTION", length = 512)
	private String flowDirection;
	
	// 家庭住址
	@Column(name = "HOME_ADDRESS", length = 512)
	private String homeAddress;
	
	
	@Column(name="PEOPLE_STATUS",length=32)
	private String peopleStatus;
}
