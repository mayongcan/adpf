/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.customer.entity;

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
 * Customer数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 客户id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CustomerIdGenerator")
	@TableGenerator(name = "CustomerIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "CUSTOMER_PK", allocationSize = 1)
	@Column(name = "customer_id", unique = true, nullable = false)
	private Long customerId;
	
	// 广告主名称
	@Column(name = "customer_name", length = 50)
	private String customerName;
	
	// 广告主资质
	@Column(name = "customer_guanggaozhuzizhi", length = 30)
	private String customerGuanggaozhuzizhi;
	
	// 所属行业
	@Column(name = "customer_suoshuhangye", length = 30)
	private String customerSuoshuhangye;
	
	// 联系人
	@Column(name = "customer_lianxiren", nullable = false, length = 30)
	private String customerLianxiren;
	
	// 电话号码
	@Column(name = "customer_iphone")
	private Integer customerIphone;
	
	// 通信地址
	@Column(name = "customer_address", length = 40)
	private String customerAddress;
	
	// 上传图片
	@Column(name = "customer_logo", length = 30)
	private String customerLogo;
	
	// 营业执照图片
	@Column(name = "customer_yyzz", length = 20)
	private String customerYyzz;
	
	// ICP备案
	@Column(name = "customer_beian", length = 30)
	private String customerBeian;
	
	// 账号状态
	@Column(name = "customer_zhuangtai")
	private Integer customerZhuangtai;
	
}
