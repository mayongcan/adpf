/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.landingPage.entity;

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
 * AdpfLandingPage数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_landing_page")
public class AdpfLandingPage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 落地页id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdpfLandingPageIdGenerator")
	@TableGenerator(name = "AdpfLandingPageIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADPF_LANDING_PAGE_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 页面路径
	@Column(name = "PAGEPATH", length = 250)
	private String pagepath;
	
	// 名称
	@Column(name = "NAME", length = 250)
	private String name;
	
	// 类型
	@Column(name = "TYPE", length = 250)
	private String type;
}
