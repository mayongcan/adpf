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
 * TnpmFamilyInfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tnpm_family_info")
public class TnpmApplicantFamilyInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TnpmFamilyInfoIdGenerator")
	@TableGenerator(name = "TnpmFamilyInfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "TNPM_FAMILY_INFO_PK", allocationSize = 1)
	@Column(name = "family_id", unique = true, nullable = false)
	private Long familyId;
	
	// 
	@Column(name = "family_name", length = 32)
	private String familyName;
	
	// 
	@Column(name = "family_relation", length = 32)
	private String familyRelation;
	
	// 
	@Column(name = "id_card", length = 20)
	private String idCard;
	
	// 
	@Column(name = "job_place", length = 128)
	private String jobPlace;
	
	// 
	@Column(name = "nation", length = 16)
	private String nation;
	
	// 
	@Column(name = "people_id")
	private Long peopleId;
	
	// 
	@Column(name = "phone", length = 11)
	private String phone;
	
	// 
	@Column(name = "politics_status", length = 32)
	private String politicsStatus;
	
	// 
	@Column(name = "sex", length = 3)
	private String sex;
	
}
