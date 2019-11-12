/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.tagLibrary.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.gdtm.tagLibrary.entity.AdpfTagLibrary;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfTagLibraryRepositoryCustom {

	/**
	 * 获取AdpfTagLibrary列表
	 * @param adpfTagLibrary
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfTagLibrary adpfTagLibrary, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfTagLibrary列表总数
	 * @param adpfTagLibrary
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfTagLibrary adpfTagLibrary, Map<String, Object> params);
	
	/**
	 * 获取AdpfTagLibrary未绑定列表
	 * @param adpfTagLibrary
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getUnboundList(AdpfTagLibrary adpfTagLibrary, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfTagLibrary未绑定列表总数
	 * @param adpfTagLibrary
	 * @param params
	 * @return
	 */
	public int getUnboundListCount(AdpfTagLibrary adpfTagLibrary, Map<String, Object> params);
	
	/**
	 * 获取标签Url
	 * @param adpfTagLibrary
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getTagURL(AdpfTagLibrary adpfTagLibrary, Map<String, Object> params,int pageIndex, int pageSize);
	
}