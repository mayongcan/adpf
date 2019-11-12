/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.Tencent.weChatLogin.entity;

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
 * WechatLoginUserinfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wechat_login_userinfo")
public class WechatLoginUserinfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "WechatLoginUserinfoIdGenerator")
	@TableGenerator(name = "WechatLoginUserinfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "WECHAT_LOGIN_USERINFO_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 用户标识
	@Column(name = "OPENID", length = 255)
	private String openid;
	
	// 昵称
	@Column(name = "NICKNAME", length = 255)
	private String nickname;
	
	// 性别
	@Column(name = "SEX", precision = 1, scale = 0)
	private Boolean sex;
	
	// 国家
	@Column(name = "COUNTRY", length = 255)
	private String country;
	
	// 省份
	@Column(name = "PROVINCE", length = 255)
	private String province;
	
	// 城市
	@Column(name = "CITY", length = 255)
	private String city;
	
	// 头像链接
	@Column(name = "HEADIMGURL", length = 255)
	private String headimgurl;
	
	// 手机号码
	@Column(name = "TEL", precision = 11, scale = 0)
	private Long tel;
	
}
