/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.cdnion.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.cdnion.entity.PptCondition;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface PptConditionRepositoryCustom {

	/**
	 * 获取PptCondition列表
	 * @param pptCondition
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(PptCondition pptCondition, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取PptCondition列表总数
	 * @param pptCondition
	 * @param params
	 * @return
	 */
	public int getListCount(PptCondition pptCondition, Map<String, Object> params);


	List<Map<String, Object>> getListByid(String vid);
}