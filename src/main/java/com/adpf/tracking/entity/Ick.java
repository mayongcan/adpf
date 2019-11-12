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
 * Ick数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_click")
public class Ick implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 点击id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "IckIdGenerator")
	@TableGenerator(name = "IckIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "T_CLICK_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	// 推广活动id
	@Column(name = "promo_id")
	private Long promoId;
	
	// 渠道id
	@Column(name = "channel_id")
	private Long channelId;
	
	// 代理商id
	@Column(name = "agent_id")
	private Long agentId;
	
	// 产品id
	@Column(name = "app_id")
	private Long appId;
	
	// 平台类型
	@Column(name = "app_type", length = 0)
	private String appType;
	
	// 安卓id
	@Column(name = "android_id", length = 0)
	private String androidid;
	
	// IOS IDFA
	@Column(name = "idfa", length = 0)
	private String idfa;
	
	// 设备IP
	@Column(name = "ip", length = 0)
	private String ip;
	
	// 设备ua
	@Column(name = "ua", length = 0)
	private String ua;
	
	// 设备imei
	@Column(name = "imei", length = 0)
	private String imei;
	
	// 设备mac
	@Column(name = "mac", length = 0)
	private String mac;
	
	// 回调地址
	@Column(name = "callback_url", length = 0)
	private String callback;
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;
	
	// 安卓id的md5值
	@Column(name = "android_id_md5", length = 15)
	private String androidid_md5;
	
	// 设备imei的MD5
	@Column(name = "imei_md5", length = 15)
	private String imei_md5;
	
}
