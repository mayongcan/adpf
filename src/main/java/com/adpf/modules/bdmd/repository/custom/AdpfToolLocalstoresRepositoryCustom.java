/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.bdmd.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.bdmd.entity.AdpfToolLocalstores;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfToolLocalstoresRepositoryCustom {

	/**
	 * 获取AdpfToolLocalstores列表
	 * @param adpfToolLocalstores
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfToolLocalstores adpfToolLocalstores, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfToolLocalstores列表总数
	 * @param adpfToolLocalstores
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfToolLocalstores adpfToolLocalstores, Map<String, Object> params);
}