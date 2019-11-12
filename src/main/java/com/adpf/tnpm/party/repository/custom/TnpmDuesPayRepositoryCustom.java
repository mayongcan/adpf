/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tnpm.party.entity.TnpmDuesPay;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TnpmDuesPayRepositoryCustom {

	/**
	 * 获取TnpmDuesPay列表
	 * @param tnpmDuesPay
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TnpmDuesPay tnpmDuesPay, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TnpmDuesPay列表总数
	 * @param tnpmDuesPay
	 * @param params
	 * @return
	 */
	public int getListCount(TnpmDuesPay tnpmDuesPay, Map<String, Object> params);
}