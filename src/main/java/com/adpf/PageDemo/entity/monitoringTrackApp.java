/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.PageDemo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MonitoringData数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "monitoring_track_app")
public class monitoringTrackApp implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "monitoringTrackAppIdGenerator")
	@TableGenerator(name = "monitoringTrackAppIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "MONITORING_TRACK_APP_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	@Column(name = "uid", length = 50)
	private String uid;
	
	@Column(name = "label", length = 250)
	private String label;
	
	@Column(name = "mobile_type", length = 250)
	private String mobileType;
	
	@Column(name = "province_label", length = 250)
	private String provinceLabel;
	
	@Column(name = "uv", length = 250)
	private String uv;
	
}
