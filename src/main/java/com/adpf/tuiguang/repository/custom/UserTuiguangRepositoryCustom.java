/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tuiguang.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tuiguang.entity.UserTuiguang;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface UserTuiguangRepositoryCustom {

	/**
	 * 获取UserTuiguang列表
	 * @param userTuiguang
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(UserTuiguang userTuiguang, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取UserTuiguang列表总数
	 * @param userTuiguang
	 * @param params
	 * @return
	 */
	public int getListCount(UserTuiguang userTuiguang, Map<String, Object> params);
}