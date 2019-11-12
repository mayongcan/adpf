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
@Table(name = "monitoring_data")
public class monitoringData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "monitoringDataIdGenerator")
	@TableGenerator(name = "monitoringDataIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "MONITORING_DATA_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	@Column(name = "curlid", length = 50)
	private String curlid;
	
	@Column(name = "video_id", length = 250)
	private String videoid;
	
	@Column(name = "album_id", length = 250)
	private String albumid;
	
	@Column(name = "period", length = 250)
	private String period;
	
	@Column(name = "TVID", length = 250)
	private String TVID;
	
	@Column(name = "tv_name", length = 250)
	private String tvname;
	
	@Column(name = "tv_type", length = 250)
	private String TvType;
	
	@Column(name = "video_name", length = 250)
	private String videoname;
	
	@Column(name = "media_name", length = 250)
	private String mediaName;
	
	@Column(name = "complete_url", length = 250)
	private String completeurl;
	
}
