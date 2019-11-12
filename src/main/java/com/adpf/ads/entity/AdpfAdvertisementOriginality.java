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
 * AdpfAdvertisementOriginality数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_advertisement_originality")
public class AdpfAdvertisementOriginality implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdpfAdvertisementOriginalityIdGenerator")
	@TableGenerator(name = "AdpfAdvertisementOriginalityIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADPF_ADVERTISEMENT_ORIGINALITY_PK", allocationSize = 1)
	@Column(name = "ORIGINALITY_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long originalityId;
	
	// 创意形式
	@Column(name = "ORIGINALITY_STATE", precision = 2, scale = 0)
	private Integer originalityState;
	
	// 内容
	@Column(name = "CENTENT", length = 512)
	private String centent;
	
	// 图片url
	@Column(name = "IMG_URL", length = 800)
	private String imgUrl;
	
	// 视频URL
	@Column(name = "MV_URL", length = 512)
	private String mvUrl;
	
	// 宽度
	@Column(name = "WIDTH", precision = 10, scale = 0)
	private Long width;
	
	// 高度
	@Column(name = "HEIGHT", precision = 10, scale = 0)
	private Long height;
	
	// 创意名称
	@Column(name = "ORGINALITY_NAME", length = 512)
	private String orginalityName;
	
}
