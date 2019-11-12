/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.dataservicelogin.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.dataservicelogin.entity.DataServiceLogin;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface DataServiceLoginRepositoryCustom {

	/**
	 * 获取DataServiceLogin列表
	 * @param dataServiceLogin
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(DataServiceLogin dataServiceLogin, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取DataServiceLogin列表总数
	 * @param dataServiceLogin
	 * @param params
	 * @return
	 */
	public int getListCount(DataServiceLogin dataServiceLogin, Map<String, Object> params);
	
	/**
	 * 获取DataServiceLogin列表
	 * @param dataServiceLogin
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getUserInfo(String username);
	
}