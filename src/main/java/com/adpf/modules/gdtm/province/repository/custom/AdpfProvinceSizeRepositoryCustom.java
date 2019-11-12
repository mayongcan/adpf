/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.province.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.gdtm.province.entity.AdpfProvinceSize;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfProvinceSizeRepositoryCustom {

	/**
	 * 获取AdpfProvinceSize列表
	 * @param adpfProvinceSize
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfProvinceSize adpfProvinceSize, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfProvinceSize列表总数
	 * @param adpfProvinceSize
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfProvinceSize adpfProvinceSize, Map<String, Object> params);
	
	
	public List<Map<String, Object>> getDate(AdpfProvinceSize adpfProvinceSize, Map<String, Object> params);
}