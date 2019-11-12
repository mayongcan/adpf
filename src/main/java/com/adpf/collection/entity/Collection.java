/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.collection.entity;

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
 * Collection数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "collection")
public class Collection implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CollectionIdGenerator")
	@TableGenerator(name = "CollectionIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "COLLECTION_PK", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	// 包名
	@Column(name = "APKNAME", length = 50)
	private String apkname;
	
	// 名称
	@Column(name = "NAME", length = 50)
	private String name;
	
	@Column(name = "IMGPATH",length = 100)
	private String imgpath;
	
	@Column(name = "APKSIZE",length = 10)
	private String apksize;
	
	@Column(name = "INSTALLURL",length = 100)
	private String installurl;
	
	@Column(name = "CATEID",length = 10)
	private String cateid;
	
	
	
}
