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
 * TChannelMacro数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_channel_macro")
public class TChannelMacro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 唯一标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TChannelMacroIdGenerator")
	@TableGenerator(name = "TChannelMacroIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "T_CHANNEL_MACRO_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	// 渠道id
	@Column(name = "channel_id")
	private Integer channelId;
	
	// url类型
	@Column(name = "url_type", length = 0)
	private String urlType;
	
	// 字段宏
	@Column(name = "macro", length = 0)
	private String macro;
	
	// 我方字段
	@Column(name = "myparam", length = 0)
	private String myparam;
	
	// 状态
	@Column(name = "staue", length = 0)
	private String staue;
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;
	
}
