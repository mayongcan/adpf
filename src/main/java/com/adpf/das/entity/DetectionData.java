/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.das.entity;

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
 * DetectionData数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detection_data")
public class DetectionData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 主键
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "DetectionDataIdGenerator")
	@TableGenerator(name = "DetectionDataIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "DETECTION_DATA_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 数据标识
	@Column(name = "curlid", length = 50)
	private String curlid;
	
	// 视频id
	@Column(name = "video_id", length = 50)
	private String videoId;
	
	// 集数id
	@Column(name = "album_id", length = 50)
	private String albumId;
	
	// 集数
	@Column(name = "period", length = 50)
	private String period;
	
	// 观看平台id
	@Column(name = "TVID", length = 50)
	private String tvid;
	
	// 视频名称
	@Column(name = "tv_name", length = 50)
	private String tvName;
	
	// tv的类型
	@Column(name = "tv_type", length = 50)
	private String tvType;
	
	// 观看视频的名称
	@Column(name = "video_name", length = 50)
	private String videoName;
	
	// 观看平台的名称
	@Column(name = "media_name", length = 50)
	private String mediaName;
	
	// 视频网址
	@Column(name = "complete_url", length = 250)
	private String completeUrl;
	
}
