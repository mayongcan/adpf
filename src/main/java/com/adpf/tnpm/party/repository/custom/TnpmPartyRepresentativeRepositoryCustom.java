/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tnpm.party.entity.TnpmPartyRepresentative;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TnpmPartyRepresentativeRepositoryCustom {

	/**
	 * 获取TnpmPartyRepresentative列表
	 * @param tnpmPartyRepresentative
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TnpmPartyRepresentative tnpmPartyRepresentative, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TnpmPartyRepresentative列表总数
	 * @param tnpmPartyRepresentative
	 * @param params
	 * @return
	 */
	public int getListCount(TnpmPartyRepresentative tnpmPartyRepresentative, Map<String, Object> params);
}