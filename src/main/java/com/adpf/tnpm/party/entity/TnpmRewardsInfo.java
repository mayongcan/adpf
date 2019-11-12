/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.entity;

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
 * TnpmRewardsInfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tnpm_rewards_info")
public class TnpmRewardsInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 奖惩Id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TnpmRewardsInfoIdGenerator")
	@TableGenerator(name = "TnpmRewardsInfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "TNPM_REWARDS_INFO_PK", allocationSize = 1)
	@Column(name = "REWARDS_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long rewardsId;
	
	// 奖惩名称
	@Column(name = "REWARDS_NAME", length = 32)
	private String rewardsName;
	
	// 奖惩决定原始文件
	@Column(name = "REWARDS_FILE", length = 512)
	private String rewardsFile;
	
	// 奖惩原因
	@Column(name = "REWARDS_REASON", length = 512)
	private String rewardsReason;
	
	// 奖惩批准机关
	@Column(name = "REWARDS_RATIFY_OFFICE", length = 256)
	private String rewardsRatifyOffice;
	
	// 奖惩批准级别
	@Column(name = "REWARDS_LEVEL", length = 10)
	private String rewardsLevel;
	
	// 奖惩批准日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RATIFY_DATE")
	private Date ratifyDate;
	
	// 奖惩撤销日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CANCEL_DATE")
	private Date cancelDate;
	
	// 奖惩说明
	@Column(name = "REWARDS_DETAILS", length = 2000)
	private String rewardsDetails;
	
	// 人员ID
	@Column(name = "PEOPLE_ID", precision = 10, scale = 0)
	private Long peopleId;
	
}
