/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.ioDemo.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.ioDemo.entity.IoDemoUser;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface IoDemoUserRepositoryCustom {

	/**
	 * 获取IoDemoUser列表
	 * @param ioDemoUser
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(IoDemoUser ioDemoUser, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取IoDemoUser列表总数
	 * @param ioDemoUser
	 * @param params
	 * @return
	 */
	public int getListCount(IoDemoUser ioDemoUser, Map<String, Object> params);
}