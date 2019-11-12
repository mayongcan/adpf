/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.count.entity;

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
 * AdpfLabelCount数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_label_count")
public class AdpfLabelCount implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdpfLabelCountIdGenerator")
	@TableGenerator(name = "AdpfLabelCountIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADPF_LABEL_COUNT_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	private Date date;
	
	// 名称
	@Column(name = "label", length = 25)
	private String label;
	
	// 个数
	@Column(name = "size", precision = 10, scale = 0)
	private Long size;
	
	// 权重
	@Column(name = "weight", precision = 10, scale = 0)
	private Long weight;
	
}
