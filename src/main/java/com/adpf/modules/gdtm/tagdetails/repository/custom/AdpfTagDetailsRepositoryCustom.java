/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.tagdetails.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.gdtm.tagdetails.entity.AdpfTagDetails;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfTagDetailsRepositoryCustom {

	/**
	 * 获取AdpfTagDetails列表
	 * @param adpfTagDetails
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfTagDetails adpfTagDetails, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfTagDetails列表总数
	 * @param adpfTagDetails
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfTagDetails adpfTagDetails, Map<String, Object> params);
}