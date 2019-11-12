/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.ads.entity;

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
 * AdpfAdvertisementVersion数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_advertisement_version")
public class AdpfAdvertisementVersion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdpfAdvertisementVersionIdGenerator")
	@TableGenerator(name = "AdpfAdvertisementVersionIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADPF_ADVERTISEMENT_VERSION_PK", allocationSize = 1)
	@Column(name = "VERSION_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long versionId;
	
	// PC/Mobile平台
	@Column(name = "PC_OR_MOBILE", precision = 2, scale = 0)
	private Integer pcOrMobile;
	
	// 广告版位
	@Column(name = "VERSION_NAME", length = 512)
	private String versionName;
	
	// 创意方式
	@Column(name = "ORIGINALITY_STATE", precision = 2, scale = 0)
	private Integer originalityState;
	
	// 描述
	@Column(name = "ADPF_DESCRIBE", length = 512)
	private String adpfDescribe;
	
	// 曝光量
	@Column(name = "ADPF_EXPOSURE", precision = 10, scale = 0)
	private Long adpfExposure;
	
}
