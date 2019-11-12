/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.ioDemo.entity;

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
 * IoDemoUser数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "io_demo_user")
public class IoDemoUser implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "IoDemoUserIdGenerator")
	@TableGenerator(name = "IoDemoUserIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "IO_DEMO_USER_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 名称
	@Column(name = "mingcheng", length = 250)
	private String mingcheng;
	
	// 视屏
	@Column(name = "shiping", length = 250)
	private String shiping;
	
	// 实名
	@Column(name = "shiming", length = 250)
	private String shiming;
	
	// 用户ID
	@Column(name = "yonghu_id", length = 250)
	private String yonghuId;
	
	// 诸葛id
	@Column(name = "shuge_id", length = 250)
	private String shugeId;
	
	// 首次访问时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	private Date startTime;
	
	// 最后访问时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	private Date endTime;
	
	// 近30天访问次数
	@Column(name = "fangwen_data", precision = 10, scale = 0)
	private Long fangwenData;
	
	// 近30天访问时长
	@Column(name = "fangwen_date", length = 250)
	private String fangwenDate;
	
	// 国家
	@Column(name = "guojia", length = 250)
	private String guojia;
	
	// 省份
	@Column(name = "shengfen", length = 250)
	private String shengfen;
	
	// 城市
	@Column(name = "chegnshi", length = 250)
	private String chegnshi;
	
	// 类型
	@Column(name = "leixing", length = 250)
	private String leixing;
	
	// 礼包
	@Column(name = "libao", length = 250)
	private String libao;
	
	// 性别
	@Column(name = "xingbie", length = 250)
	private String xingbie;
	
	// 微信昵称
	@Column(name = "nicheng", length = 250)
	private String nicheng;
	
	// 微信-省
	@Column(name = "weixin_sheng", length = 250)
	private String weixinSheng;
	
	// 年龄
	@Column(name = "nianling", precision = 10, scale = 0)
	private Long nianling;
	
	// openid
	@Column(name = "openid", length = 250)
	private String openid;
	
	// 版本
	@Column(name = "banben", length = 250)
	private String banben;
	
	// 渠道
	@Column(name = "qudao", length = 250)
	private String qudao;
	
	// 设备品牌
	@Column(name = "shebeipinpai", length = 250)
	private String shebeipinpai;
	
	// 设备型号
	@Column(name = "shebeixinghao", length = 250)
	private String shebeixinghao;
	
	// 运营商
	@Column(name = "yunyingshang", length = 250)
	private String yunyingshang;
	
	// 操作系统
	@Column(name = "caozuxitong", length = 250)
	private String caozuxitong;
	
	// 系统版本
	@Column(name = "xitongbanben", length = 250)
	private String xitongbanben;
	
}
