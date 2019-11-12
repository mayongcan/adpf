/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.advertiser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Advertisers数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "advertisers")
public class Advertisers implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdvertisersIdGenerator")
	@TableGenerator(name = "AdvertisersIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADVERTISERS_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	// 广告主名称
	@Column(name = "advertisers_name", length = 50)
	private String advertisersName;
	
	// 联系人
	@Column(name = "advertisers_contacts", length = 50)
	private String advertisersContacts;
	
	// 联系电话
	@Column(name = "advertisers_iphone", length = 20)
	private String advertisersIphone;
	
	// 账号
	@Column(name = "adv_loginid", length = 50)
	private String advLoginid;
	
	// 密码
	@Column(name = "advertisers_password", length = 50)
	private String advertisersPassword;
	
	// 数据进货价
	@Column(name = "advertisers_dataprice")
	private Integer advertisersDataprice;
	
	// 状态
	@Column(name = "advertisers_state", length = 10)
	private String advertisersState;
	
	// 权限id
	@Column(name = "advertisers_roleid")
	private Integer advertisersRoleid;

	//名下广告id
	@Column(name = "differenceid", length = 20)
	private String differenceid;

	// 数据费用
	@Column(name = "avd_costdata", precision = 10, scale = 0)
	private String avdCostdata;

	// 外呼通话费用
	@Column(name = "adv_costcall", precision = 10, scale = 0)
	private String advCostcall;

	// 坐席费用
	@Column(name = "adv_costseat", precision = 10, scale = 0)
	private String advCostseat;

	// 余额
	@Column(name = "adv_balance", precision = 10, scale = 0)
	private BigDecimal advBalance;


}
