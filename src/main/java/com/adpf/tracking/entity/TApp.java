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
 * TApp数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_app")
public class TApp implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 产品id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TAppIdGenerator")
	@TableGenerator(name = "TAppIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "T_APP_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	// 产品名称
	@Column(name = "name", length = 0)
	private String name;
	
	// 接入key
	@Column(name = "app_key", length = 0)
	private String appKey;
	
	// 平台类型
	@Column(name = "app_type", length = 0)
	private String appType;
	
	// 落地页
	@Column(name = "landing_url", length = 0)
	private String landingUrl;
	
	// 状态
	@Column(name = "staue", length = 0)
	private String staue;
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;
	
}
