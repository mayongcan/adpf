/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tracking.entity.TAppLogin;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TAppLoginRepositoryCustom {

	/**
	 * 获取TAppLogin列表
	 * @param tAppLogin
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TAppLogin tAppLogin, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TAppLogin列表总数
	 * @param tAppLogin
	 * @param params
	 * @return
	 */
	public int getListCount(TAppLogin tAppLogin, Map<String, Object> params);
}