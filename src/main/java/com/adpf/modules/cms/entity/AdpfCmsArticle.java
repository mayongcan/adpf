/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.cms.entity;

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
 * AdpfCmsArticle数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_cms_article")
public class AdpfCmsArticle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 文章标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdpfCmsArticleIdGenerator")
	@TableGenerator(name = "AdpfCmsArticleIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADPF_CMS_ARTICLE_PK", allocationSize = 1)
	@Column(name = "ARTICLE_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long articleId;
	
	// 文章标题
	@Column(name = "ARTICLE_TITLE", nullable = false, length = 50)
	private String articleTitle;
	
	// 文章封面
	@Column(name = "ARTICLE_IMAGE", length = 512)
	private String articleImage;
	
	// 发布时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ARTICLE_TIME")
	private Date articleTime;
	
	// 发布状态
	@Column(name = "ARTICLE_STATUS", precision = 10, scale = 0)
	private Long articleStatus;
	
	// 点击量
	@Column(name = "ARTICLE_CLICK", precision = 10, scale = 0)
	private Long articleClick;
	
	// 文章内容
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "ARTICLE_CONTENT")
	private String articleContent;
	
	// 分类
	@Column(name = "CATEGORY_ID", precision = 10, scale = 0)
	private Long categoryId;
	
	// 用户标识
	@Column(name = "USER_ID", precision = 10, scale = 0)
	private Long userId;
	
	// 更新时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ARTICLE_MTIME")
	private Date articleMtime;
	
}
