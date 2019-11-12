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
 * AdpfScheduling数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_scheduling")
public class AdpfScheduling implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 排期与出价
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdpfSchedulingIdGenerator")
	@TableGenerator(name = "AdpfSchedulingIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADPF_SCHEDULING_PK", allocationSize = 1)
	@Column(name = "SCHEDULING_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long schedulingId;
	
	// 开始日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PUT_BEGIN_DATE")
	private Date putBeginDate;
	
	// 结束日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PUT_END_DATE")
	private Date putEndDate;
	
	// 开始时间
	@Column(name = "PUT_BEGIN_HOUR", length = 215)
	private String putBeginHour;
	
	// 结束时间
	@Column(name = "PUT_END_HOUR", length = 215)
	private String putEndHour;
	
	// 出价方式
	@Column(name = "OFFER_STATE", precision = 2, scale = 0)
	private Integer offerState;
	
	// 出价
	@Column(name = "OFFER", precision = 10, scale = 2)
	private Double offer;
	
	// 广告ID
	@Column(name = "ADVERTISEMENT_NAME",  length = 215)
	private String advertisementName;
	
}
