/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.cms.restful;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adpf.modules.cms.entity.AdpfCmsArticle;
import com.adpf.modules.cms.entity.AdpfCmsCategory;
import com.adpf.modules.cms.service.AdpfCmsArticleService;
import com.adpf.modules.cms.service.AdpfCmsCategoryService;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.SessionUtils;

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/adpf/web")
public class AdpfCmsWebRestful {
	
    @Autowired
    private AdpfCmsArticleService adpfCmsArticleService;
    @Autowired
    private AdpfCmsCategoryService AdpfCmsCategoryService;
    
    
	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getList",method=RequestMethod.GET)
	public JSONObject getList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
		AdpfCmsArticle adpfCmsArticle = (AdpfCmsArticle)BeanUtils.mapToBean(params, AdpfCmsArticle.class);				
		json = adpfCmsArticleService.getWebList(pageable, adpfCmsArticle, params);
		return json;
	}
    
	@RequestMapping(value="/getIdList",method=RequestMethod.GET)
	public JSONObject getIdList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();			
		AdpfCmsArticle adpfCmsArticle = (AdpfCmsArticle)BeanUtils.mapToBean(params, AdpfCmsArticle.class);	
		json = adpfCmsArticleService.getWebIdList(adpfCmsArticle,params);
		return json;
	}
    /**
	 * 新增信息
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/addClicks",method=RequestMethod.POST)
	public JSONObject add(HttpServletRequest request, @RequestBody Map<String, Object> params){
		System.out.println(params);
		JSONObject json = new JSONObject();
		json = adpfCmsArticleService.addClicks(params);
		return json;
	}
	
	
	/**
	 * 獲取分類標識
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/getSimList",method=RequestMethod.GET)
	public JSONObject getSimList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request)); 
		AdpfCmsCategory adpfCmsCategory=(AdpfCmsCategory)BeanUtils.mapToBean(params,AdpfCmsCategory.class);
		json=AdpfCmsCategoryService.getSimList(pageable, adpfCmsCategory, params);
		return json;
	}
	
	
	
	
}
