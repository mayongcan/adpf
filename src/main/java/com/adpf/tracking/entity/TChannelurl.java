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
 * TChannelurl数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_channelurl")
public class TChannelurl implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TChannelurlIdGenerator")
	@TableGenerator(name = "TChannelurlIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "T_CHANNELURL_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	
	// 渠道名称
	@Column(name = "NAME", length = 50)
	private String name;
	
	// 渠道类型
	@Column(name = "CAHNNEL_TYPE", length = 50)
	private String cahnnelType;
	
	// 监测链接
	@Column(name = "CHANNEL_URL", length = 100)
	private String channelUrl;
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
}
