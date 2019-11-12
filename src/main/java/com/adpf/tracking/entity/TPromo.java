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

import org.apache.hadoop.classification.InterfaceAudience.Private;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gimplatform.core.annotation.CustomerDateAndTimeDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TPromo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_promo")
public class TPromo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 推广活动id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TPromoIdGenerator")
	@TableGenerator(name = "TPromoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "T_PROMO_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	// 推广活动名称
	@Column(name = "name", length = 0)
	private String name;
	
	// 渠道id
	@Column(name = "channel_id")
	private Long channelId;
	
	// 代理商id
	@Column(name = "anent_id")
	private Long anentId;
	
	// 产品id
	@Column(name = "app_id")
	private Long appId;
	
	
	@Column(name = "channel_name",length = 50)
	private String channelName;
	
	@Column(name = "anent_name",length = 50)
	private String anentName;
	
	@Column(name = "app_name",length = 50)
	private String appName;
	
	// 点击监控短链
	@Column(name = "click_url", length = 0)
	private String clickUrl;
	
	// 展示监控短链
	@Column(name = "show_url", length = 0)
	private String showUrl;
	
	@Column(name ="landing_url",length = 200)
	private String landingUrl;
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;
	
}
