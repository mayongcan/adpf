/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.Tencent.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DirectionalInformation数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "directional_information")
public class DirectionalInformation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "DirectionalInformationIdGenerator")
	@TableGenerator(name = "DirectionalInformationIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "DIRECTIONAL_INFORMATIONA_PK", allocationSize = 1)
	@Column(name = "DIRECTIONAL_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long directionalId;
	
	// 编码
	@Column(name = "ID_CODE", nullable = false, length = 250)
	private String idCode;
	
	// 名字
	@Column(name = "DIRECTIONAL_NAME", nullable = false, length = 250)
	private String directionalName;
	
	// 父级ID
	@Column(name = "FATHER_ID", nullable = false, length = 250)
	private String fatherId;
	
	// GRADE
	@Column(name = "GRADE", nullable = false, length = 250)
	private String grade;
	
	// 是否显示
	@Column(name = "STATE", nullable = false, length = 10)
	private String state;
	
}
