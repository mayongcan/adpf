/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tnpm.party.entity.TnpmPartyMembership;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TnpmPartyMembershipRepositoryCustom {

	/**
	 * 获取TnpmPartyMembership列表
	 * @param tnpmPartyMembership
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TnpmPartyMembership tnpmPartyMembership, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TnpmPartyMembership列表总数
	 * @param tnpmPartyMembership
	 * @param params
	 * @return
	 */
	public int getListCount(TnpmPartyMembership tnpmPartyMembership, Map<String, Object> params);
}