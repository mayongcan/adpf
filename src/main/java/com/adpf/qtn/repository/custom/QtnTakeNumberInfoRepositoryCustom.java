/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.qtn.entity.QtnTakeNumberInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface QtnTakeNumberInfoRepositoryCustom {

	/**
	 * 获取QtnQrcodeInfo列表
	 * @param qtnQrcodeInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(QtnTakeNumberInfo qtnQrcodeInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取QtnQrcodeInfo列表总数
	 * @param qtnQrcodeInfo
	 * @param params
	 * @return
	 */
	public int getListCount(QtnTakeNumberInfo qtnQrcodeInfo, Map<String, Object> params);
	
	
	public List<Map<String,Object>> getOrgList(Map<String,Object> params,int pageIndex,int pageSize);
}