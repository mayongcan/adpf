/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.entity;

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
 * TAppRegister数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_app_register")
public class TAppRegister implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TAppRegisterIdGenerator")
	@TableGenerator(name = "TAppRegisterIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "T_APP_REGISTER_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	// 发生时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "when1")
	private Date when1;
	
	// 接入key
	@Column(name = "app_key", length = 100)
	private String appKey;
	
	// 产品id
	@Column(name = "app_id")
	private String appId;
	
	// 平台类型
	@Column(name = "app_type", length = 100)
	private String appType;
	
	// 安卓id
	@Column(name = "android_id", length = 100)
	private String androidId;
	
	// IOS IDFA
	@Column(name = "idfa", length = 100)
	private String idfa;
	
	// 设备IP
	@Column(name = "ip", length = 100)
	private String ip;
	
	// 设备ua
	@Column(name = "ua", length = 100)
	private String ua;
	
	// 设备imei
	@Column(name = "imei", length = 100)
	private String imei;
	
	// 设备mac
	@Column(name = "mac", length = 100)
	private String mac;
	
	// 用户账号
	@Column(name = "account_id", length = 100)
	private String accountId;
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;
	
}
