/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.landingPage.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.landingPage.entity.AdpfLandingPage;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfLandingPageRepositoryCustom {

	/**
	 * 获取AdpfLandingPage列表
	 * @param adpfLandingPage
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfLandingPage adpfLandingPage, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfLandingPage列表总数
	 * @param adpfLandingPage
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfLandingPage adpfLandingPage, Map<String, Object> params);
}