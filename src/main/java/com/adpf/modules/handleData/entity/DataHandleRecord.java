/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.handleData.entity;

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
 * DataHandleRecord数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "data_handle_record")
public class DataHandleRecord implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "DataHandleRecordIdGenerator")
	@TableGenerator(name = "DataHandleRecordIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "DATA_HANDLE_RECORD_PK", allocationSize = 1)
	@Column(name = "DATA_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long dataId;
	
	// 表名称
	@Column(name = "TBALE_NAME", length = 255)
	private String tableName;
	
	// 文件名称
	@Column(name = "FILE_NAME", length = 255)
	private String fileName;
	
	// 是否创建表
	@Column(name = "CREATE_TABLE", precision = 1, scale = 0)
	private Boolean createTable;
	
	// 数据状态
	@Column(name = "DATA_TYPE", precision = 1, scale = 0)
	private Boolean dataType;
	
	// 日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE")
	private Date date;
	
}
