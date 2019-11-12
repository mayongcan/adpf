/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.customer.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.customer.entity.Customer;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface CustomerRepositoryCustom {

	/**
	 * 获取Customer列表
	 * @param customer
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(Customer customer, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取Customer列表总数
	 * @param customer
	 * @param params
	 * @return
	 */
	public int getListCount(Customer customer, Map<String, Object> params);
}