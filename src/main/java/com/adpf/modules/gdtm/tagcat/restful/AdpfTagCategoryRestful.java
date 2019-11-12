/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.tagcat.restful;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import com.gimplatform.core.utils.StringUtils;

import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.adpf.modules.gdtm.tagcat.entity.AdpfTagCategory;
import com.adpf.modules.gdtm.tagcat.service.AdpfTagCategoryService;

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/adpf/tagCategory")
public class AdpfTagCategoryRestful {

	protected static final Logger logger = LogManager.getLogger(AdpfTagCategoryRestful.class);
    
    @Autowired
    private AdpfTagCategoryService adpfTagCategoryService;
    
	/**
	 * 用于记录打开日志
	 * @param request
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public JSONObject index(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getList",method=RequestMethod.GET)
	public JSONObject getList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
				AdpfTagCategory adpfTagCategory = (AdpfTagCategory)BeanUtils.mapToBean(params, AdpfTagCategory.class);				
				json = adpfTagCategoryService.getList(pageable, adpfTagCategory, params);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 获取月份统计列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getMonth",method=RequestMethod.GET)
	public JSONObject getListMonth(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = adpfTagCategoryService.getMonth(params);
				System.out.println(json);				
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	@RequestMapping(value="/getTreeList",method=RequestMethod.GET)
	public JSONObject getTreeList(HttpServletRequest request){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = adpfTagCategoryService.getTreeList();
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取树列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 新增信息
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public JSONObject add(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = adpfTagCategoryService.add(params, userInfo);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51002","新增信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 编辑信息
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public JSONObject edit(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = adpfTagCategoryService.edit(params, userInfo);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51003","编辑信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 删除信息
	 * @param request
	 * @param idsList
	 * @return
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public JSONObject del(HttpServletRequest request,@RequestBody String idsList){
		JSONObject json = new JSONObject();
		try {
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = adpfTagCategoryService.del(idsList, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	@RequestMapping(value="/getListTag",method=RequestMethod.GET)
	public JSONObject getListTag(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
				AdpfTagCategory adpfTagCategory = (AdpfTagCategory)BeanUtils.mapToBean(params, AdpfTagCategory.class);				
				json = adpfTagCategoryService.getListTag(pageable, adpfTagCategory, params);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	@RequestMapping(value = "/addBinding", method = RequestMethod.POST)
    public JSONObject addBindings(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            UserInfo loginUser = SessionUtils.getUserInfo();
            if (loginUser == null)
                json = RestfulRetUtils.getErrorNoUser();
            else {
                Long catId = MapUtils.getLong(map, "catId");
                String tagIds = MapUtils.getString(map, "tagIds");
                if (catId == null || StringUtils.isBlank(tagIds)) {
                    json = RestfulRetUtils.getErrorParams();
                } else {
                    List<Long> tagIdList = new ArrayList<Long>();
                    Long tmpId = null;
                    if (!"".equals(tagIds)) {
                        String[] ids = tagIds.split(",");
                        for (String id : ids) {
                            tmpId = StringUtils.toLong(id, -1L);
                            if (tmpId != null && !tmpId.equals(-1L))
                            	tagIdList.add(tmpId);
                        }
                    }
                    System.out.println(tagIdList);
                    System.out.println(catId);
                    json = adpfTagCategoryService.addBindings(loginUser, catId, tagIdList);
                }
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("250010", "绑定失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
	
	/**
     * 删除角色用户
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value = "/delBinding", method = RequestMethod.POST)
    public JSONObject delBindings(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            UserInfo loginUser = SessionUtils.getUserInfo();
            if (loginUser == null)
                json = RestfulRetUtils.getErrorNoUser();
            else {
                Long catId = MapUtils.getLong(map, "catId");
                Long tagId = MapUtils.getLong(map, "tagId");
                if (catId == null || catId == null) {
                    json = RestfulRetUtils.getErrorParams();
                } else {
                    System.out.println(catId);
                    System.out.println(tagId);
                    json = adpfTagCategoryService.delBindings(loginUser, catId, tagId);
                }
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("250011", "删除角色用户失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
}
