/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.entity;

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
 * QtnTaskHanding数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "qtn_task_handing")
public class QtnTaskHanding implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 唯一id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "QtnTaskHandingIdGenerator")
	@TableGenerator(name = "QtnTaskHandingIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "QTN_TASK_HANDING_PK", allocationSize = 1)
	@Column(name = "TASK_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long taskId;
	
	// 微信用户Id
	@Column(name = "OPEN_ID", length = 64)
	private String openId;
	
	// 状态 0 待处理 1 处理完成
	@Column(name = "STATUS", length = 2)
	private String status;
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	// 用户类型 0、微信  1、PC
	@Column(name = "USER_TYPE", length = 2)
	private String userType;
	
	// 用户ID
	@Column(name = "USER_ID", precision = 10, scale = 0)
	private Long userId;
	
	@Column(name="FORMAT_NUMBER",length=4)
	private String formatNumber;
	
//	@Column(name = "NUMBER" ,precision=10, scale=0)
//	private Long number;
	
	
	@Column(name = "ORGANIZER_ID", precision = 10, scale = 0)
	private Long organizerId;
	
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_MANAGER_TIME")
	private Date startManagerTime;
	
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_MANAGER_TIME")
	private Date endManagerTime;
	
	@Column(name="IS_VIP",length=2)
	private String isVip;
	
}
