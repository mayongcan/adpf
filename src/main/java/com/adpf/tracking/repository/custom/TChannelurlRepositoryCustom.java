/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tracking.entity.TChannelurl;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TChannelurlRepositoryCustom {

	/**
	 * 获取TChannelurl列表
	 * @param tChannelurl
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TChannelurl tChannelurl, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TChannelurl列表总数
	 * @param tChannelurl
	 * @param params
	 * @return
	 */
	public int getListCount(TChannelurl tChannelurl, Map<String, Object> params);
}