/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tnpm.party.entity.TnpmRewardsInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TnpmRewardsInfoRepositoryCustom {

	/**
	 * 获取TnpmRewardsInfo列表
	 * @param tnpmRewardsInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TnpmRewardsInfo tnpmRewardsInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TnpmRewardsInfo列表总数
	 * @param tnpmRewardsInfo
	 * @param params
	 * @return
	 */
	public int getListCount(TnpmRewardsInfo tnpmRewardsInfo, Map<String, Object> params);
}