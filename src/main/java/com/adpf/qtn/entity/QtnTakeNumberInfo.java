/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.entity;

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
 * QtnQrcodeInfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "qtn_take_number_info")
public class QtnTakeNumberInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 唯一id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "QtnTakeNumberInfoIdGenerator")
	@TableGenerator(name = "QtnTakeNumberInfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "QTN_TAKE_NUMBER_INFO_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 二维码存放路径
	@Column(name = "QRCODE_PATH", length = 128)
	private String qrcodePath;
	
	// 机构id
	@Column(name = "ORGANIZER_ID", precision = 10, scale = 0)
	private Long organizerId;
	
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	
	@Column(name="NUMBER",length = 16)
	private String number;
	
	@Column(name="VIP_NUMBER",length=8)
	private String vipNumber;
	
}
