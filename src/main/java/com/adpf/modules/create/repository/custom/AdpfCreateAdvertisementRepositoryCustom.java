/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.create.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.create.entity.AdpfCreateAdvertisement;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfCreateAdvertisementRepositoryCustom {

	/**
	 * 获取AdpfCreateAdvertisement列表
	 * @param adpfCreateAdvertisement
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfCreateAdvertisement adpfCreateAdvertisement, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfCreateAdvertisement列表总数
	 * @param adpfCreateAdvertisement
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfCreateAdvertisement adpfCreateAdvertisement, Map<String, Object> params);
}