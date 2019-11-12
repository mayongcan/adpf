/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.crowd.entity;

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
 * DmpCrowdInfo数据库映射实体类
 * 
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dmp_crowd_info")
public class DmpCrowdInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	// 人群id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "DmpCrowdInfoIdGenerator")
	@TableGenerator(name = "DmpCrowdInfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "DMP_CROWD_INFO_PK", allocationSize = 1)
	@Column(name = "CROWD_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long crowdId;

	// 人群名称
	@Column(name = "CROWD_NAME", nullable = false, length = 64)
	private String crowdName;

	// 状态
	@Column(name = "STATUS", length = 4)
	private String status;

	// 可用人数
	@Column(name = "USABLE", precision = 10, scale = 0)
	private Long usable;

	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", nullable = false)
	private Date createDate;

	// 人群描述
	@Column(name = "CROWD_DETAILS", length = 512)
	private String crowdDetails;

	@Column(name = "TXT_TYPE", length = 6)
	private String txtType;

	@Column(name = "TXT_PATH", length = 128)
	private String txtPath;

	@Column(name = "IS_JOIN", length = 2)
	private String isJoin;

	@Column(name = "TXT_SIZE", length = 32)
	private String txtSize;

	@Column(name = "TXT_SUM", precision = 10, scale = 0)
	private Long txtSum;

}
