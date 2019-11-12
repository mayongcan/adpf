/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.bdmd.entity;

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
 * AdpfToolLocalstores数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_tool_localstores")
public class AdpfToolLocalstores implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdpfToolLocalstoresIdGenerator")
	@TableGenerator(name = "AdpfToolLocalstoresIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADPF_TOOL_LOCALSTORES_PK", allocationSize = 1)
	@Column(name = "STORE_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long storeId;
	
	// 门店名称
	@Column(name = "STORE_NAME", length = 512)
	private String storeName;
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	// 门店地址
	@Column(name = "STORE_ADDRESS", length = 800)
	private String storeAddress;
	
	// 门店电话
	@Column(name = "STORE_PHONE", length = 250)
	private String storePhone;
	
	// 门店行业
	@Column(name = "STORE_INDUSTRY", length = 512)
	private String storeIndustry;
	
	// 商园
	@Column(name = "STORE_GARDEN", length = 512)
	private String storeGarden;
	
	// 营业时间
	@Column(name = "BUSINESSTIME", length = 215)
	private String businesstime;
	
	// 商品单价
	@Column(name = "STOREPRICE")
	private Integer storeprice;
	
	// 门店特色
	@Column(name = "STOREFEATURES", length = 215)
	private String storefeatures;
	
}
