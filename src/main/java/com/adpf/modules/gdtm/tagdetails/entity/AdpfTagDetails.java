/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.tagdetails.entity;

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
 * AdpfTagDetails数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_tag_details")
public class AdpfTagDetails implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 主键标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdpfTagDetailsIdGenerator")
	@TableGenerator(name = "AdpfTagDetailsIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADPF_TAG_DETAILS_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 省份
	@Column(name = "PROVIENCES", precision = 10, scale = 0)
	private Long proviences;
	
	// 设备码
	@Column(name = "EQUIPMENT_CODE", length = 20)
	private String equipmentCode;
	
	// 手机号码
	@Column(name = "PHONE_NUM", length = 20)
	private String phoneNum;
	
	// 访问次数
	@Column(name = "VISITS")
	private Integer visits;
	
	// 应用标识
	@Column(name = "APP_ID", length=20)
	private String appId;
	
	// 应用名称
	@Column(name = "APP_NAME", length = 30)
	private String appName;
	
}

