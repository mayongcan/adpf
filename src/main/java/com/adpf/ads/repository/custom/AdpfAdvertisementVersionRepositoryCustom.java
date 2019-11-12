/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.ads.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.ads.entity.AdpfAdvertisementVersion;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfAdvertisementVersionRepositoryCustom {

	/**
	 * 获取AdpfAdvertisementVersion列表
	 * @param adpfAdvertisementVersion
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfAdvertisementVersion adpfAdvertisementVersion, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfAdvertisementVersion列表总数
	 * @param adpfAdvertisementVersion
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfAdvertisementVersion adpfAdvertisementVersion, Map<String, Object> params);
}