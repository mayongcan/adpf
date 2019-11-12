/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.callwbook.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gimplatform.core.annotation.CustomerDateAndTimeDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * CallWbook数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "call_wbook")
public class CallWbook implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 主键
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CallWbookIdGenerator")
	@TableGenerator(name = "CallWbookIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "CALL_WBOOK_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 企业id
	@Column(name = "adv_id", length = 20)
	private String advId;
	
	// 坐席号
	@Column(name = "seat_id", length = 20)
	private String seatId;
	
	// 外显号
	@Column(name = "call_id", length = 20)
	private String callId;
	
	// 用时
	@Column(name = "call_time", length = 20)
	private String callTime;
	
	// 日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_time")
	private Date dateTime;
	
	//价格
	@Column(name = "call_price", length = 50)
	private String callPrice;

}
