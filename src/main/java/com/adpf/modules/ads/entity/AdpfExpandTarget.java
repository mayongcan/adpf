/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.ads.entity;

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
 * AdpfExpandTarget数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_expand_target")
public class AdpfExpandTarget implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 推广目标id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdpfExpandTargetIdGenerator")
	@TableGenerator(name = "AdpfExpandTargetIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADPF_EXPAND_TARGET_PK", allocationSize = 1)
	@Column(name = "TARGET_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long targetId;
	
	// 目标名称
	@Column(name = "TARGET_NAME", nullable = false, length = 32)
	private String targetName;
	
	// 目标描述
	@Column(name = "TARGET_DETAILS", nullable = false, length = 64)
	private String targetDetails;
	
	// 目标图标
	@Column(name = "TARGET_LOGO", length = 64)
	private String targetLogo;
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@Column(name="PARENT_ID",precision = 10, scale = 0)
	private Long parentId ;
	
}
