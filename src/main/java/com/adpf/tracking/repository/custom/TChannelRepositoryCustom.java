/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tracking.entity.TChannel;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TChannelRepositoryCustom {

	/**
	 * 获取TChannel列表
	 * @param tChannel
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TChannel tChannel, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TChannel列表总数
	 * @param tChannel
	 * @param params
	 * @return
	 */
	public int getListCount(TChannel tChannel, Map<String, Object> params);
}