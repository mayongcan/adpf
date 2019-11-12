/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.cms.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * AdpfCmsCategory数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_cms_category")
public class AdpfCmsCategory implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 文章标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdpfCmsCategoryIdGenerator")
	@TableGenerator(name = "AdpfCmsCategoryIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADPF_CMS_CATEGORY_PK", allocationSize = 1)
	@Column(name = "CATEGORY_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long categoryId;
	
	// 分类名称
	@Column(name = "CATEGORY_NAME", nullable = false, length = 10)
	private String categoryName;
	
	// 分类描述
	@Column(name = "CATEGORY_DESC", length = 20)
	private String categoryDesc;
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CATEGORY_TIME")
	private Date categoryTime;
	
	// 用户标识
	@Column(name = "USER_ID", precision = 10, scale = 0)
	private Long userId;
	
	// 更新时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CATEGORY_MTIME")
	private Date categoryMtime;
	
}
