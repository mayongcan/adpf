/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.entity;

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
 * TPromoCurr数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_promo_curr")
public class TPromoCurr implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 详情id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TPromoCurrIdGenerator")
	@TableGenerator(name = "TPromoCurrIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "T_PROMO_CURR_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	// 统计时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ods_time", length = 1000)
	private Date odsTime;
	
	// 推广活动id
	@Column(name = "promo_id")
	private Integer promoId;
	
	// 渠道id
	@Column(name = "channel_id")
	private Long channelId;
	
	// 代理商id
	@Column(name = "agent_id")
	private Long agentId;
	
	// 产品id
	@Column(name = "app_id")
	private Long appId;
	
	// 点击数
	@Column(name = "click", length = 1000)
	private String click;
	
	// 有效点击数
	@Column(name = "click_valid", length = 1000)
	private String clickValid;
	
	// 排重点击数
	@Column(name = "click_distinct", length = 1000)
	private String clickDistinct;
	
	// 按天点击排重点击数
	@Column(name = "click_distinct_day", length = 1000)
	private String clickDistinctDay;
	
	// 排重激活设备数
	@Column(name = "active_distinct", length = 1000)
	private String activeDistinct;
	
	// 活跃设备数
	@Column(name = "dau", length = 1000)
	private String dau;
	
	// 当天激活且注册设备数
	@Column(name = "active_register", length = 1000)
	private String activeRegister;
	
	// 排重注册设备数
	@Column(name = "register_distinct", length = 1000)
	private String registerDistinct;
	
	// 按天排重注册设备数
	@Column(name = "register_distinct_day", length = 1000)
	private String registerDistinctDay;
	
	// 按天排重登录设备数
	@Column(name = "login_day", length = 1000)
	private String loginDay;
	
	// 区间付费
	@Column(name = "range_fee", length = 1000)
	private String rangeFee;
	
	// 新增付费
	@Column(name = "new_fee", length = 1000)
	private String newFee;
	
	// 新增付费设备数
	@Column(name = "pay_device", length = 100)
	private String payDevice;
	
	// 总付费
	@Column(name = "total_fee", length = 100)
	private String totalFee;
	
	// 总付费设备数
	@Column(name = "total_pay_device", length = 100)
	private String totalPayDevice;
	
}
