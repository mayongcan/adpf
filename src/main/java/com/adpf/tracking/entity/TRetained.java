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
 * TRetained数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_retained")
public class TRetained implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	//@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRetainedIdGenerator")
	//@TableGenerator(name = "TRetainedIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "T_RETAINED_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	// 产品id
	@Column(name = "app_id")
	private Long appId;
	
	// 平台类型
	@Column(name = "app_type", length = 100)
	private String appType;
	
	//设备ip
	@Column(name = "ip", length = 100)
	private String ip;
	
	// 设备imei
	@Column(name = "imei", length = 100)
	private String imei;
	
	// IOS IDFA
	@Column(name = "idfa", length = 100)
	private String idfa;
	
	// 注册时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;
	
	// 登录天数
	@Column(name = "login_count", length = 50)
	private int loginCount;
	
	
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login_time")
	private Date lastLoginTime;
	
}
