/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.seats.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * AdvidSeatsid数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "advid_seatsid")
public class AdvidSeatsid implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 主键
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdvidSeatsidIdGenerator")
	@TableGenerator(name = "AdvidSeatsidIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADVID_SEATSID_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 企业id
	@Column(name = "adv_id", nullable = false, precision = 10, scale = 0)
	private String advId;
	
	// 坐席id
	@Column(name = "seats_id", nullable = false, precision = 20, scale = 0)
	private String seatsId;
	
}
