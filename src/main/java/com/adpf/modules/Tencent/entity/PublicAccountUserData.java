/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.Tencent.entity;

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
 * PublicAccountUserData数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "public_account_user_data")
public class PublicAccountUserData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PublicAccountUserDataIdGenerator")
	@TableGenerator(name = "PublicAccountUserDataIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "PUBLIC_ACCOUNT_USER_DATA_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 地域
	@Column(name = "REGIONAL", length = 1024)
	private String regional;
	
	// 投放类型范围
	@Column(name = "DATATYPE", length = 1024)
	private String datatype;
	
	// 标签访问次数（日）
	@Column(name = "NUMBER_LABEL_VISITS", precision = 10, scale = 0)
	private Long numberLabelVisits;
	
	// 标签在线时间（日）
	@Column(name = "LABEL_ONLINE_TIME", precision = 10, scale = 0)
	private Long labelOnlineTime;
	
	// 标签在线流量（日）
	@Column(name = "LABEL_ONLINE_TRAFFIC", precision = 10, scale = 1)
	private Double labelOnlineTraffic;
	
	// 标签访问次数（累计多天）
	@Column(name = "LABEL_VISITTIME_SCUMULATIVE", precision = 10, scale = 0)
	private Long labelVisittimeScumulative;
	
	// 标签在线时间（累计多天）
	@Column(name = "LABEL_ONLINE_TIMEA_CCUMULATED", precision = 10, scale = 1)
	private Double labelOnlineTimeaCcumulated;
	
	// 标签在线流量（累计多天）
	@Column(name = "LABEL_ONLINE_TRAFFI_CCUMULATIVE", precision = 10, scale = 1)
	private Double labelOnlineTraffiCcumulative;
	
	// 访问时间段
	@Column(name = "ACCESS_TIME", length = 250)
	private String accessTime;
	
	// 操作系统（安卓，苹果）
	@Column(name = "OPERATING_SYSTEM", length = 250)
	private String operatingSystem;
	
	// 创建时间
	@Column(name = "CREATE_TIME", length = 120)
	private String createTime;
	
	// 手机号
	@Column(name = "TEL", length = 100)
	private String tel;
	
	// 登录人ID
	@Column(name = "PEOPLE_ID", precision = 10, scale = 0)
	private Long peopleId;
	
	// 自定义APP
	@Column(name = "CUSTOM_APP", length = 520)
	private String customApp;
	
	//联系人
	@Column(name="LINKMAN",length=20)
	private String linkman;
	
	//公司名称
	@Column(name="COMPANY",length=250)
	private String company;
}
