/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.create.entity;

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
 * AdpfCreateAdvertisement数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_create_advertisement")
public class AdpfCreateAdvertisement implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdpfCreateAdvertisementIdGenerator")
	@TableGenerator(name = "AdpfCreateAdvertisementIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADPF_CREATE_ADVERTISEMENT_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 广告名
	@Column(name = "ADVERTISEMNT_NAME", length = 100)
	private String advertisemntName;
	
	// 定向ID
	@Column(name = "ORIENTEER_ID", precision = 10, scale = 0)
	private Long orienteerId;
	
	// 推广ID
	@Column(name = "PLAN_ID", precision = 10, scale = 0)
	private Long planId;
	
	// 广告版位ID
	@Column(name = "SCHEDULING_ID", precision = 10, scale = 0)
	private Long schedulingId;
	
	// 排期与出价ID
	@Column(name = "VERSION_ID", precision = 10, scale = 0)
	private Long versionId;
	
	// 创意ID
	@Column(name = "ORIGINALITY_ID", precision = 10, scale = 0)
	private Long originalityId;
	
	// 财务表id
	@Column(name = "FINANCE_ID", precision = 10, scale = 0)
	private Long financeId;
	
	// 统计表id
	@Column(name = "COUNT_ID", precision = 10, scale = 0)
	private Long countId;
	
	// 点击量
	@Column(name = "HISTDATA", precision = 10, scale = 0)
	private Long histdata;
	
	// 花费
	@Column(name = "EXPENDITURE", precision = 30, scale = 2)
	private Double expenditure;
	
	// 曝光量
	@Column(name = "CPC", precision = 10, scale = 2)
	private Double cpc;
	
	// 状态
	@Column(name = "STATE", length = 10)
	private String state;
	
	// 落地页ID
	@Column(name = "PAGEID", precision = 10, scale = 0)
	private Long pageid;
	
}
