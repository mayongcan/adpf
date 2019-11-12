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
 * TnpmFamilyInfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tnpm_family_info")
public class TnpmFamilyInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 成员ID
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TnpmFamilyInfoIdGenerator")
	@TableGenerator(name = "TnpmFamilyInfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "TNPM_FAMILY_INFO_PK", allocationSize = 1)
	@Column(name = "FAMILY_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long familyId;
	
	// 成员姓名
	@Column(name = "FAMILY_NAME", length = 32)
	private String familyName;
	
	// 与本人关系
	@Column(name = "FAMILY_RELATION", length = 32)
	private String familyRelation;
	
	// 性别
	@Column(name = "SEX", length = 3)
	private String sex;
	
	// 证件号码
	@Column(name = "ID_CARD", length = 20)
	private String idCard;
	
	// 民族
	@Column(name = "NATION", length = 16)
	private String nation;
	
	// 政治面貌
	@Column(name = "POLITICS_STATUS", length = 32)
	private String politicsStatus;
	
	// 联系电话
	@Column(name = "PHONE", length = 11)
	private String phone;
	
	// 工作单位及职务
	@Column(name = "JOB_PLACE", length = 128)
	private String jobPlace;
	
	// 人员ID
	@Column(name = "PEOPLE_ID", precision = 10, scale = 0)
	private Long peopleId;
	
}
