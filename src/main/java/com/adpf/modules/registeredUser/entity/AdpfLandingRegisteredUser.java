/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.registeredUser.entity;

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
 * AdpfLandingRegisteredUser数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_landing_registered_user")
public class AdpfLandingRegisteredUser implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdpfLandingRegisteredUserIdGenerator")
	@TableGenerator(name = "AdpfLandingRegisteredUserIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADPF_LANDING_REGISTERED_USER_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 落地页ID
	@Column(name = "LANDING_ID", length = 20)
	private String landingId;
	
	// 用户数据
	@Column(name = "REGISTERED_DATA", length = 500)
	private String registeredData;
	
	// 用户手机型号
	@Column(name = "PHONE_MODEL", length = 250)
	private String phoneModel;
	
	// 操作系统
	@Column(name = "OPERATING_SYSTEM", length = 250)
	private String operatingSystem;
	
	// 客户端IP地址
	@Column(name = "IP_ADDRESSING", length = 250)
	private String ipAddressing;
	
}
