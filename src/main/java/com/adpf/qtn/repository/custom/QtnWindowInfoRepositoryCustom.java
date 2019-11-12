/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.qtn.entity.QtnWindowInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface QtnWindowInfoRepositoryCustom {

	/**
	 * 获取QtnWindowInfo列表
	 * @param qtnWindowInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(QtnWindowInfo qtnWindowInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取QtnWindowInfo列表总数
	 * @param qtnWindowInfo
	 * @param params
	 * @return
	 */
	public int getListCount(QtnWindowInfo qtnWindowInfo, Map<String, Object> params);
	
	
	public List<Map<String,Object>> getListByOrganizerId (Map<String,Object> params);
}