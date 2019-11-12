/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tnpm.party.entity.TnpmadministrationDutyInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TnpmadministrationDutyInfoRepositoryCustom {

	/**
	 * 获取TnpmadministrationDutyInfo列表
	 * @param tnpmadministrationDutyInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TnpmadministrationDutyInfo tnpmadministrationDutyInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TnpmadministrationDutyInfo列表总数
	 * @param tnpmadministrationDutyInfo
	 * @param params
	 * @return
	 */
	public int getListCount(TnpmadministrationDutyInfo tnpmadministrationDutyInfo, Map<String, Object> params);
}