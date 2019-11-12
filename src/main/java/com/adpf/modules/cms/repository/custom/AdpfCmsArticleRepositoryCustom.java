/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.cms.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.cms.entity.AdpfCmsArticle;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfCmsArticleRepositoryCustom {

	/**
	 * 获取AdpfCmsArticle列表
	 * @param adpfCmsArticle
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfCmsArticle adpfCmsArticle, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfCmsArticle已发布的列表
	 * @param adpfCmsArticle
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getWebList(AdpfCmsArticle adpfCmsArticle, Map<String, Object> params, int pageIndex, int pageSize);
	
	public List<Map<String, Object>> getWebIdList(AdpfCmsArticle adpfCmsArticle,Map<String, Object> params);

	
	/**
	 * 获取AdpfCmsArticle列表总数
	 * @param adpfCmsArticle
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfCmsArticle adpfCmsArticle, Map<String, Object> params);
	
	/**
	 * 获取AdpfCmsArticle已发布列表总数
	 * @param adpfCmsArticle
	 * @param params
	 * @return
	 */
	public int getWebListCount(AdpfCmsArticle adpfCmsArticle, Map<String, Object> params);
}