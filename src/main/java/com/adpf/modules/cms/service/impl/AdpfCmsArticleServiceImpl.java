/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.cms.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.cms.service.AdpfCmsArticleService;
import com.adpf.modules.cms.entity.AdpfCmsArticle;
import com.adpf.modules.cms.repository.AdpfCmsArticleRepository;

@Service
public class AdpfCmsArticleServiceImpl implements AdpfCmsArticleService {
	
    @Autowired
    private AdpfCmsArticleRepository adpfCmsArticleRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfCmsArticle adpfCmsArticle, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfCmsArticleRepository.getList(adpfCmsArticle, params, page.getPageNumber(), page.getPageSize());
		int count = adpfCmsArticleRepository.getListCount(adpfCmsArticle, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}
	
	@Override
	public JSONObject getWebList(Pageable page, AdpfCmsArticle adpfCmsArticle, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfCmsArticleRepository.getWebList(adpfCmsArticle, params, page.getPageNumber(), page.getPageSize());
		int count = adpfCmsArticleRepository.getWebListCount(adpfCmsArticle, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}
	
	@Override
	public JSONObject getWebIdList(AdpfCmsArticle adpfCmsArticle,Map<String, Object> params) {
		List<Map<String, Object>> list = adpfCmsArticleRepository.getWebIdList(adpfCmsArticle,params);
		return RestfulRetUtils.getRetSuccess(list);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
		params.put("articleStatus", 0);
		params.put("articleClick", 0);
		params.put("articleTime", new Date());
		params.put("userId", userInfo.getUserId());
	    AdpfCmsArticle adpfCmsArticle = (AdpfCmsArticle) BeanUtils.mapToBean(params, AdpfCmsArticle.class);
		adpfCmsArticleRepository.save(adpfCmsArticle);
		return RestfulRetUtils.getRetSuccess();
	}
	
	@Override
	public JSONObject addClicks(Map<String, Object> params) {
        AdpfCmsArticle adpfCmsArticle = (AdpfCmsArticle) BeanUtils.mapToBean(params, AdpfCmsArticle.class);
		AdpfCmsArticle adpfCmsArticleInDb = adpfCmsArticleRepository.findOne(adpfCmsArticle.getArticleId());
		if(adpfCmsArticleInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfCmsArticle, adpfCmsArticleInDb);
		adpfCmsArticleRepository.save(adpfCmsArticleInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
		params.put("articleMtime", new Date());
        AdpfCmsArticle adpfCmsArticle = (AdpfCmsArticle) BeanUtils.mapToBean(params, AdpfCmsArticle.class);
		AdpfCmsArticle adpfCmsArticleInDb = adpfCmsArticleRepository.findOne(adpfCmsArticle.getArticleId());
		if(adpfCmsArticleInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfCmsArticle, adpfCmsArticleInDb);
		adpfCmsArticleRepository.save(adpfCmsArticleInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfCmsArticleRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject send(Map<String, Object> params, UserInfo userInfo) {
        AdpfCmsArticle adpfCmsArticle = (AdpfCmsArticle) BeanUtils.mapToBean(params, AdpfCmsArticle.class);
		AdpfCmsArticle adpfCmsArticleInDb = adpfCmsArticleRepository.findOne(adpfCmsArticle.getArticleId());
		if(adpfCmsArticleInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前发布的文章不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfCmsArticle, adpfCmsArticleInDb);
		adpfCmsArticleRepository.save(adpfCmsArticleInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject getDetails(Map<String, Object> params, UserInfo userInfo) {
		AdpfCmsArticle adpfCmsArticle =adpfCmsArticleRepository.getOne(Long.valueOf((String)params.get("articleId")));
		return RestfulRetUtils.getRetSuccess(adpfCmsArticle);
	}
}
