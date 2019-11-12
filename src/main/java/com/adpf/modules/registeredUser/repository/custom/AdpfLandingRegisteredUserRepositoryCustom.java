/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.registeredUser.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.registeredUser.entity.AdpfLandingRegisteredUser;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfLandingRegisteredUserRepositoryCustom {

	/**
	 * 获取AdpfLandingRegisteredUser列表
	 * @param adpfLandingRegisteredUser
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfLandingRegisteredUser adpfLandingRegisteredUser, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfLandingRegisteredUser列表总数
	 * @param adpfLandingRegisteredUser
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfLandingRegisteredUser adpfLandingRegisteredUser, Map<String, Object> params);
}