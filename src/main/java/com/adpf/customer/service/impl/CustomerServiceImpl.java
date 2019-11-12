/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.customer.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.customer.service.CustomerService;
import com.adpf.customer.entity.Customer;
import com.adpf.customer.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
    @Autowired
    private CustomerRepository customerRepository;

	@Override
	public JSONObject getList(Pageable page, Customer customer, Map<String, Object> params) {
		List<Map<String, Object>> list = customerRepository.getList(customer, params, page.getPageNumber(), page.getPageSize());
		int count = customerRepository.getListCount(customer, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    Customer customer = (Customer) BeanUtils.mapToBean(params, Customer.class);
		customerRepository.save(customer);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        Customer customer = (Customer) BeanUtils.mapToBean(params, Customer.class);
		Customer customerInDb = customerRepository.findOne(customer.getCustomerId());
		if(customerInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(customer, customerInDb);
		customerRepository.save(customerInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			customerRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
