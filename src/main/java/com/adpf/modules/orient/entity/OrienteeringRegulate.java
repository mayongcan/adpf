/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.orient.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrienteeringRegulate数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orienteering_regulate")
public class OrienteeringRegulate implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "OrienteeringRegulateIdGenerator")
	@TableGenerator(name = "OrienteeringRegulateIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ORIENTEERING_REGULATE_PK", allocationSize = 1)
	@Column(name = "ORIENTEER_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long orienteerId;
	
	// 定向名称
	@Column(name = "ORIENTEER_NAME", length = 250)
	private String orienteerName;
	
	// 定向描述
	@Column(name = "ORIENTEER_DESCRIBE", length = 250)
	private String orienteerDescribe;
	
	// 地域
	@Column(name = "TERRITORY", length = 2048)
	private String territory;
	
	// 年龄
	@Column(name = "AGEBAND", length = 250)
	private String ageband;
	
	// 性别
	@Column(name = "GENDER", length = 250)
	private String gender;
	
	// 学历
	@Column(name = "EDUCATION", length = 250)
	private String education;
	
	// 婚姻状态
	@Column(name = "MRITL_STTUS", length = 250)
	private String mritlSttus;
	
	// 工作状态
	@Column(name = "WORKING_POSITION", length = 250)
	private String workingPosition;
	
	// 商业兴趣
	@Column(name = "BUSINESS_INTERESTS", length = 1024)
	private String businessInterests;
	
	// 关键词
	@Column(name = "TAG", length = 250)
	private String tag;
	
	// APP行为
	@Column(name = "APP_BEHAVIOR", length = 1024)
	private String appBehavior;
	
	// APP安装
	@Column(name = "APP_INSTALL", length = 250)
	private String appInstall;
	
	// 自定义人群ID
	@Column(name = "CROWD_ID", length = 250)
	private String crowdId;
	
	// 付费用户
	@Column(name = "PAYING_USER", length = 250)
	private String payingUser;
	
	// 消费状态
	@Column(name = "CONSUMPTION_STATUS", length = 250)
	private String consumptionStatus;
	
	// 居住社区价格
	@Column(name = "RESIDENTIAL_COMMUNITY_PRICE", length = 250)
	private String residentialCommunityPrice;
	
	// 上网场景
	@Column(name = "INTERNET_SCENARIO", length = 250)
	private String internetScenario;
	
	// 操作系统
	@Column(name = "KERNEL", length = 250)
	private String kernel;
	
	// 联网方式
	@Column(name = "NETWORKING_WAY", length = 250)
	private String networkingWay;
	
	// 移动运营商
	@Column(name = "MOBILE_OPERATOR", length = 250)
	private String mobileOperator;
	
	// 设备价格
	@Column(name = "EQUIPMENT_PRICE", length = 250)
	private String equipmentPrice;
	
	// 媒体类型
	@Column(name = "MEDIUM_TYPE", length = 512)
	private String mediumType;
	
	// 微信公众号类型
	@Column(name = "PUBLIC_TYPE", length = 512)
	private String publicType;
	
	// 温度
	@Column(name = "TEMPERATURE", length = 250)
	private String temperature;
	
	// 紫外线
	@Column(name = "ULTRAVIOLET", length = 250)
	private String ultraviolet;
	
	// 穿衣指数
	@Column(name = "DRESS_INDEX", length = 250)
	private String dressIndex;
	
	// 化妆指数
	@Column(name = "MAKEUP_INDEX", length = 250)
	private String makeupIndex;
	
	// 气象
	@Column(name = "METEOROLOGY", length = 250)
	private String meteorology;
	
	// 关键词文件
	@Column(name = "KEYWORD_FILE", length = 250)
	private String keywordFile;
	
	// 广告ID
	@Column(name = "PLANID", precision = 10, scale = 0)
	private Long planid;
}
