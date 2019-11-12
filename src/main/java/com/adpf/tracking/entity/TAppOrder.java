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
 * TAppOrder数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_app_order")
public class TAppOrder implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 日志id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TAppOrderIdGenerator")
	@TableGenerator(name = "TAppOrderIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "T_APP_ORDER_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	// 发生时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "when1")
	private Date when1;
	
	// 产品id
	@Column(name = "app_id")
	private String appId;
	
	// 接入key
	@Column(name = "app_key")
	private Long appKey;
	
	// 平台类型
	@Column(name = "app_type", length = 100)
	private String appType;
	
	// 安卓id
	@Column(name = "android_id", length = 100)
	private String androidId;
	
	// IOS IDFA
	@Column(name = "idfa", length = 100)
	private String idfa;
	
	// 设备ip
	@Column(name = "ip", length = 100)
	private String ip;
	
	// 设备imei
	@Column(name = "imei", length = 100)
	private String imei;
	
	// 设备mac
	@Column(name = "mac", length = 100)
	private String mac;
	
	// 用户账号
	@Column(name = "account_id", length = 100)
	private String accountId;
	
	// 交易流水号
	@Column(name = "order_id", length = 100)
	private String orderId;
	
	// 支付类型
	@Column(name = "pay_type", length = 100)
	private String payType;
	
	// 金额
	@Column(name = "fee", length = 100)
	private String fee;
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;
	
}
