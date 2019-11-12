/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.atm.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;

import com.adpf.modules.atm.entity.AdpfAtmApplink;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfAtmApplinkRepositoryCustom {

	/**
	 * 获取AdpfAtmApplink列表
	 * @param adpfAtmApplink
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfAtmApplink adpfAtmApplink, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfAtmApplink列表总数
	 * @param adpfAtmApplink
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfAtmApplink adpfAtmApplink, Map<String, Object> params);
}