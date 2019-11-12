/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.atm.entity;

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
 * AdpfAtmAppdefine数据库映射实体类
 * 
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_atm_appdefine")
public class AdpfAtmAppdefine implements Serializable {

	private static final long serialVersionUID = 1L;

	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdpfAtmAppdefineIdGenerator")
	@TableGenerator(name = "AdpfAtmAppdefineIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADPF_ATM_APPDEFINE_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;

	// 应用名称
	@Column(name = "NAME", length = 50)
	private String name;

	// 编码
	@Column(name = "CODE", length = 50)
	private String code;

	// 总数量
	@Column(name = "COUNT", precision = 10, scale = 0)
	private Long count;

	// 创建人
	@Column(name = "CREATE_BY", precision = 10, scale = 0)
	private Long createBy;

	// 创建日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date createDate;

	// 是否有效
	@Column(name = "IS_VALID", length = 2)
	private String isValid;

	// 应用分类
	@Column(name = "TYPE", length = 2)
	private String type;

}
