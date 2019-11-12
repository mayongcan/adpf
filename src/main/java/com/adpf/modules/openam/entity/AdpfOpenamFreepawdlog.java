/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.openam.entity;

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
 * AdpfOpenamFreepawdlog数据库映射实体类
 * 
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_openam_freepawdlog")
public class AdpfOpenamFreepawdlog implements Serializable {

	private static final long serialVersionUID = 1L;

	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdpfOpenamFreepawdlogIdGenerator")
	@TableGenerator(name = "AdpfOpenamFreepawdlogIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADPF_OPENAM_FREEPAWDLOG_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;

	// 机构标识
	@Column(name = "ORGANIZER_ID", precision = 10, scale = 0)
	private Long organizerId;

	// 版本号
	@Column(name = "VERSION", length = 20)
	private String version;

	// 客户端ip地址
	@Column(name = "CLIENT_IP", length = 64)
	private String clientIp;

	// 客户端类型
	@Column(name = "CLIENT_TYPE", length = 10)
	private String clientType;

	// 设备码
	@Column(name = "IMEI", length = 128)
	private String imei;

	// IDFA
	@Column(name = "IDFA", length = 128)
	private String idfa;

	// 缓存key
	@Column(name = "AES_CACHE_KEY", length = 256)
	private String aesCacheKey;

	// 获取手机编码
	@Column(name = "CODE", length = 1024)
	private String code;

	// 状态
	@Column(name = "STATUS", length = 10)
	private String status;

	// 手机号码
	@Column(name = "MOBILE", length = 32)
	private String mobile;

	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date createDate;

	// 获取手机号码时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GETMOBILE_DATE")
	private Date getmobileDate;

	// 昵称
	@Column(name = "NICK_NAME", length = 128)
	private String nickName;

	// 运营商
	@Column(name = "OWNER", length = 128)
	private String owner;

	// 统一标识
	@Column(name = "OPEN_ID", length = 64)
	private String openId;

	// 用户头像
	@Column(name = "USER_ICON_URL", length = 1024)
	private String userIconUrl;

	// 机型
	@Column(name = "MODEL", length = 32)
	private String model;

	// 机型
	@Column(name = "OPERATING", length = 32)
	private String operating;

	// 机型
	@Column(name = "RESULT", length = 32)
	private String result;

	// 网络类型
	@Column(name = "NETWORK_TYPE", length = 128)
	private String networkType;

}
