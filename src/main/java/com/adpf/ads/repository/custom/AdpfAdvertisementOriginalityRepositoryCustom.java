/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.ads.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.ads.entity.AdpfAdvertisementOriginality;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfAdvertisementOriginalityRepositoryCustom {

	/**
	 * 获取AdpfAdvertisementOriginality列表
	 * @param adpfAdvertisementOriginality
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfAdvertisementOriginality adpfAdvertisementOriginality, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfAdvertisementOriginality列表总数
	 * @param adpfAdvertisementOriginality
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfAdvertisementOriginality adpfAdvertisementOriginality, Map<String, Object> params);
}