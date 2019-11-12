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
 * TnpmOrganizationalActivities数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tnpm_organizational_activities")
public class TnpmOrganizationalActivities implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TnpmOrganizationalActivitiesIdGenerator")
	@TableGenerator(name = "TnpmOrganizationalActivitiesIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "TNPM_ORGANIZATIONAL_ACTIVITIES_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 参加组织活动日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_ORGANIZATION")
	private Date dateOrganization;
	
	// 参加组织活动地点
	@Column(name = "PLACE_ORGANIZATION", length = 250)
	private String placeOrganization;
	
	// 参加组织活动类别
	@Column(name = "TYPES_ORGANIZATION", length = 250)
	private String typesOrganization;
	
	// 组织活动详细记录
	@Column(name = "ACTIVITIES_ORGANIZATIONA", length = 500)
	private String activitiesOrganizationa;
	
	// 人员ID
	@Column(name = "PEOPLE_ID", precision = 10, scale = 0)
	private Long peopleId;
	
}
