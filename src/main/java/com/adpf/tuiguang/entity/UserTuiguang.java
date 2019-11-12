/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tuiguang.entity;

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
 * UserTuiguang数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_tuiguang")
public class UserTuiguang implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "UserTuiguangIdGenerator")
	@TableGenerator(name = "UserTuiguangIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "USER_TUIGUANG_PK", allocationSize = 1)
	@Column(name = "tuiguang_id", unique = true, nullable = false)
	private Long tuiguangId;
	
	// 名称
	@Column(name = "tuiguang_name", nullable = false, length = 100)
	private String tuiguangName;
	
	// 链接地址
	@Column(name = "tuiguang_lianjiedizhi", length = 300)
	private String tuiguangLianjiedizhi;
	
	// 第三方点击检测
	@Column(name = "tuiguang_disanfangdianji", length = 300)
	private String tuiguangDisanfangdianji;
	
	// 第三方点击曝光
	@Column(name = "tuiguang_disanfangbaoguang", length = 300)
	private String tuiguangDisanfangbaoguang;
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tuiguang_chuangjianshijian")
	private Date tuiguangChuangjianshijian;
	
	// 更新时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tuiguang_gengxinshijian")
	private Date tuiguangGengxinshijian;
	
}
