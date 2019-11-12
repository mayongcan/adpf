/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tnpm.party.entity.PartyMembers;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface PartyMembersRepositoryCustom {

	/**
	 * 获取TnpmJoinPartyPeopleInfo列表
	 * @param tnpmJoinPartyPeopleInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(PartyMembers tnpmJoinPartyPeopleInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TnpmJoinPartyPeopleInfo列表总数
	 * @param tnpmJoinPartyPeopleInfo
	 * @param params
	 * @return
	 */
	public int getListCount(PartyMembers tnpmJoinPartyPeopleInfo, Map<String, Object> params);
	
}