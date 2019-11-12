/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.Tencent.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.Tencent.entity.PublicAccountUserData;
import com.adpf.modules.orient.entity.DirectionalInformation;
import com.adpf.modules.orient.entity.District;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface PublicAccountUserDataRepositoryCustom {

	/**
	 * 获取PublicAccountUserData列表
	 * @param publicAccountUserData
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(PublicAccountUserData publicAccountUserData, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取PublicAccountUserData列表总数
	 * @param publicAccountUserData
	 * @param params
	 * @return
	 */
	public int getListCount(PublicAccountUserData publicAccountUserData, Map<String, Object> params);
	/**
	 * 返回资料
	 * @param directionalInformation
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getListDatum(DirectionalInformation directionalInformation, Map<String, Object> params);
	/**
	 * 返回资料
	 * @param district
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getTerritory(District district, Map<String, Object> params);
	
	/**
	 * 根据用户ID查询列表
	 * @param
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getListByID(PublicAccountUserData publicAccountUserData,Map<String, Object> params);
}