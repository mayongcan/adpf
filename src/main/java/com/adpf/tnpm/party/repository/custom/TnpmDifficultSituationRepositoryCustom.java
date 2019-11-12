/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tnpm.party.entity.TnpmDifficultSituation;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TnpmDifficultSituationRepositoryCustom {

	/**
	 * 获取TnpmDifficultSituation列表
	 * @param tnpmDifficultSituation
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TnpmDifficultSituation tnpmDifficultSituation, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TnpmDifficultSituation列表总数
	 * @param tnpmDifficultSituation
	 * @param params
	 * @return
	 */
	public int getListCount(TnpmDifficultSituation tnpmDifficultSituation, Map<String, Object> params);
}