/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.oem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * AdvertisingOem数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "advertising_oem")
public class AdvertisingOem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 主键
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdvertisingOemIdGenerator")
	@TableGenerator(name = "AdvertisingOemIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADVERTISING_OEM_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false, precision = 20, scale = 0)
	private Long id;
	
	// 名称
	@Column(name = "oem_name", length = 100)
	private String oemName;
	
	// 联系人
	@Column(name = "oem_linkman", length = 100)
	private String oemLinkman;
	
	// 联系电话
	@Column(name = "oem_phone", length = 200)
	private String oemPhone;
	
	// 登录账号
	@Column(name = "oem_loginid", nullable = false, length = 200)
	private String oemLoginid;
	
	// 登录密码
	@Column(name = "oem_password", nullable = false, length = 200)
	private String oemPassword;
	
	// 状态
	@Column(name = "oem_state", nullable = false, length = 20)
	private String oemState;
	
	// 数据进货价
	@Column(name = "oem_cost", length = 50)
	private String oemCost;
	
	// 角色
	@Column(name = "oem_role", length = 20)
	private String oemRole;

	//创建人
	@Column(name = "orm_creator", length = 50)
	private String creator;

	
}
