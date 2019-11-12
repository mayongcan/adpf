/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.issue.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gimplatform.core.annotation.CustomerDateAndTimeDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * AdvIssue数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adv_issue")
public class AdvIssue implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// id
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdvIssueIdGenerator")
	@TableGenerator(name = "AdvIssueIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "ADV_ISSUE_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 企业id
	@Column(name = "adv_id", length = 10)
	private String advId;
	
	// 数量
	@Column(name = "amount", precision = 10, scale = 0)
	private Long amount;
	
	// 创建时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creat_time")
	private Date creatTime;
	
	// 创建人
	@Column(name = "creator", length = 50)
	private String creator;

	//备注
	@Column(name = "remark", length = 200)
	private String remark;
}
