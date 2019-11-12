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
 * QtnWechatInfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "qtn_wechat_info")
public class QtnWechatInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 唯一id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "QtnWechatInfoIdGenerator")
	@TableGenerator(name = "QtnWechatInfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "QTN_WECHAT_INFO_PK", allocationSize = 1)
	@Column(name = "WECHAT_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long wechatId;
	
	// 微信唯一标识
	@Column(name = "OPEN_ID", length = 64)
	private String openId;
	
	@Column(name = "NAME", length = 64)
	private String name;
	
	@Column(name="SEX",length= 3)
	private String sex;
	
	// 省份
	@Column(name = "PROVINCE", length = 16)
	private String province;
	
	// 城市
	@Column(name = "CITY", length = 16)
	private String city;
	
	// 国家
	@Column(name = "COUNTRY", length = 16)
	private String country;
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
}
