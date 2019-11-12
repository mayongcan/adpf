/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tnpm.party.entity.TnpmFamilyInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TnpmFamilyInfoRepositoryCustom {

	/**
	 * 获取TnpmFamilyInfo列表
	 * @param tnpmFamilyInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TnpmFamilyInfo tnpmFamilyInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TnpmFamilyInfo列表总数
	 * @param tnpmFamilyInfo
	 * @param params
	 * @return
	 */
	public int getListCount(TnpmFamilyInfo tnpmFamilyInfo, Map<String, Object> params);
}