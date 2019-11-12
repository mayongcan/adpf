/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.wbook.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gimplatform.core.annotation.CustomerDateAndTimeDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * WasteBook数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "waste_book")
public class WasteBook implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 主键
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "WasteBookIdGenerator")
	@TableGenerator(name = "WasteBookIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "WASTE_BOOK_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 企业id
	@Column(name = "adv_id", precision = 5, scale = 0)
	private Long advId;
	
	// 操作人
	@Column(name = "operator", length = 20)
	private String operator;
	
	// 操作时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "operation_time")
	private Date operationTime;
	
	// 收入
	@Column(name = "income", length = 10)
	private String income;
	
	// 支出
	@Column(name = "disbursement", length = 10)
	private String disbursement;

	// 备注
	@Column(name = "remark", length = 20)
	private String remark;
	
}
