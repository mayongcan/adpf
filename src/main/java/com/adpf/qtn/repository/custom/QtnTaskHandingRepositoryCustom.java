/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.qtn.entity.QtnTaskHanding;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface QtnTaskHandingRepositoryCustom {

	/**
	 * 获取QtnTaskHanding列表
	 * @param qtnTaskHanding
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(QtnTaskHanding qtnTaskHanding, Map<String, Object> params, int pageIndex, int pageSize);
	
	
	/**
	 * 获取全屏中等待的列表
	 * @param qtnTaskHanding
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getWaitList(QtnTaskHanding qtnTaskHanding, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取QtnTaskHanding列表总数
	 * @param qtnTaskHanding
	 * @param params
	 * @return
	 */
	public int getListCount(QtnTaskHanding qtnTaskHanding, Map<String, Object> params);
}