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
 * TChannel数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_channel")
public class TChannel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 渠道ID
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TChannelIdGenerator")
	@TableGenerator(name = "TChannelIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "T_CHANNEL_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	// 渠道名称
	@Column(name = "name", length = 100)
	private String name;
	
	// 设备类型
	@Column(name = "device_type", length = 30)
	private String deviceType;
	
	// 点击URL宏定义
	@Column(name = "click_macro", length = 50)
	private String clickMacro;
	
	// 是否需要回调
	@Column(name = "is_callback", length = 10)
	private String isCallback;
	
	// 回调事件
	@Column(name = "callback_event", length = 50)
	private String callbackEvent;
	
	// 回调URL宏定义
	@Column(name = "callback_macro", length = 100)
	private String callbackMacro;
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;
	
}
