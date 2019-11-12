/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.cdnion.entity;

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
 * PptCondition数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ppt_condition")
public class PptCondition implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 主键
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PptConditionIdGenerator")
	@TableGenerator(name = "PptConditionIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "PPT_CONDITION_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 预设名称
	@Column(name = "cdn_name", length = 50)
	private String cdnName;
	
	// 预设平台
	@Column(name = "media_name", length = 50)
	private String mediaName;
	
	// 预设时间
	@Column(name = "data_data", length = 50)
	private String dataData;
	
	// 预设图表
	@Column(name = "chart_url", length = 220)
	private String chartUrl;
	
	// 电视剧名
	@Column(name = "video_name", length = 220)
	private String videoName;
	
	// 创建人
	@Column(name = "user_name", length = 50)
	private String userName;
	
	// 创建时间
	@Column(name = "create_time", length = 50)
	private String createTime;
	
}
