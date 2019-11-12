/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.entity;

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
 * TnpmDuesPay数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tnpm_dues_pay")
public class TnpmDuesPay implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TnpmDuesPayIdGenerator")
	@TableGenerator(name = "TnpmDuesPayIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "TNPM_DUES_PAY_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 缴纳党费标准
	@Column(name = "PARTY_PAYMENT_STANDARDS", length = 250)
	private String partyPaymentStandards;
	
	// 缴纳党费基数
	@Column(name = "PAY_PARTY_BASE", length = 250)
	private String payPartyBase;
	
	// 党费计算比例
	@Column(name = "PARTY_CALCULATED_PROPORTION", precision = 10, scale = 2)
	private Double partyCalculatedProportion;
	
	// 每月应缴纳党费
	@Column(name = "PARTY_PAID_MONTHLY", precision = 10, scale = 2)
	private Double partyPaidMonthly;
	
	// 本次缴纳党费类型
	@Column(name = "PAYMENT_PARTY_TYPE", length = 250)
	private String paymentPartyType;
	
	// 本次缴纳党费月份
	@Column(name = "PAYMENT_PARTY_MONTH", precision = 10, scale = 0)
	private Long paymentPartyMonth;
	
	// 本次缴纳金额
	@Column(name = "AMOUNT_PAID", precision = 10, scale = 2)
	private Double amountPaid;
	
	// 大额党费缴纳日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PARTY_PAYMENT_DATE")
	private Date partyPaymentDate;
	
	// 大额党费缴纳金额
	@Column(name = "LARGE_AMOUNT_PARTY", precision = 10, scale = 2)
	private Double largeAmountParty;
	
	// 人员ID
	@Column(name = "PEOPLE_ID", precision = 10, scale = 0)
	private Long peopleId;
	
}
