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
@Table(name = "monitoring_statistics")
public class monitoringStatistics implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "monitoringStatisticsIdGenerator")
	@TableGenerator(name = "monitoringStatisticsIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "MONITORING_STATISTICS_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	@Column(name = "prov", length = 50)
	private String prov;
	
	@Column(name = "curlid", length = 250)
	private String curlid;
	
	@Column(name = "statistics_id", length = 250)
	private String statisticsId;
	
	@Column(name = "uv", length = 250)
	private String uv;
	
	@Column(name = "pv", length = 250)
	private String pv;
	
	@Column(name = "media_name", length = 250)
	private String mediaName;
	
	@Column(name = "video_name", length = 250)
	private String videoName;
	
	@Column(name = "type", length = 250)
	private String type;
	
	@Column(name = "data_date", length = 250)
	private String dataDate;
	
}
