/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tracking.entity.TAppPay;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TAppPayRepositoryCustom {

	/**
	 * 获取TAppPay列表
	 * @param tAppPay
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TAppPay tAppPay, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TAppPay列表总数
	 * @param tAppPay
	 * @param params
	 * @return
	 */
	public int getListCount(TAppPay tAppPay, Map<String, Object> params);
}