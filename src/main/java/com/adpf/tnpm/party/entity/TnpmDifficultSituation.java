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
 * TnpmDifficultSituation数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tnpm_difficult_situation")
public class TnpmDifficultSituation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TnpmDifficultSituationIdGenerator")
	@TableGenerator(name = "TnpmDifficultSituationIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "TNPM_DIFFICULT_SITUATION_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 生活困难类型
	@Column(name = "DIFFICULT_TYPE", length = 250)
	private String difficultType;
	
	// 是否享受低保
	@Column(name = "ENJOY_LOW", length = 250)
	private String enjoyLow;
	
	// 健康状况
	@Column(name = "HEALTH_CONDITION", length = 250)
	private String healthCondition;
	
	// 是否享受补助
	@Column(name = "ENJOY_BENEFITS", length = 250)
	private String enjoyBenefits;
	
	// 建国前入党没有工作老党员
	@Column(name = "A_WHOLE", length = 250)
	private String aWhole;
	
	// 生活困难情况补充
	@Column(name = "LIFE_HARD", length = 250)
	private String lifeHard;
	
	// 人员ID
	@Column(name = "PEOPLE_ID", precision = 10, scale = 0)
	private Long peopleId;
	
}
