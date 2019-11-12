/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.ImportNumber.entity;

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
 * AdpfImportNumberFile数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_import_number_file")
public class AdpfImportNumberFile implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdpfImportNumberFileIdGenerator")
	@TableGenerator(name = "AdpfImportNumberFileIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADPF_IMPORT_NUMBER_FILE_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 运营商
	@Column(name = "OPERATOR", length = 20)
	private String operator;
	
	// 省份
	@Column(name = "PROVINCE", length = 250)
	private String province;
	
	// 城市
	@Column(name = "CITY", length = 250)
	private String city;
	
	// 区号
	@Column(name = "AREA_CODE", length = 20)
	private String areaCode;
	
	// 号码
	@Column(name = "PHONE", length = 20)
	private String phone;
	
	// 路径
	@Column(name = "FILE_PATH", length = 250)
	private String filePath;
	
	// 序号
	@Column(name = "SERIAL_NUMBER", length = 250)
	private String serialNumber;
	
}
