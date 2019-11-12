/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.collection.restful;

import java.io.File;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.adpf.collection.entity.Collection;
import com.adpf.collection.repository.CollectionRepository;
import com.adpf.collection.service.CollectionService;

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/adpf/collection")
public class CollectionRestful {

	protected static final Logger logger = LogManager.getLogger(CollectionRestful.class);
    
    @Autowired
    private CollectionService collectionService;
    
    @Autowired
    private CollectionRepository cc;
    
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
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), 5000);  
				Collection collection = (Collection)BeanUtils.mapToBean(params, Collection.class);				
				json = collectionService.getList(pageable, collection, params);
//				 File file = new File("c://line//");
//				 File[] fileList = file.listFiles();
//				 for(int i = 0;i < fileList.length; i++){
//					 if(fileList[i].isFile()){
//						 System.out.println(fileList[i]);
//						 Document doc = Jsoup.parse(fileList[i],"UTF-8","");
//						 //System.out.println(doc);
//						// Elements tag = doc.select("div.app-info-desc > a:eq(0)");
//						   Elements sElements = doc.select("div.app-info");
//						 //System.out.println(aa.attr("href"));
//						 Map<String, String> map = new HashMap<String, String>();
//						 for(Element param:sElements){
//							 String imgpath = param.select("img").attr("src");
//							 String apkname = param.select("div.app-info-desc > a:eq(0)").attr("href").split("=")[1];
//							 String name = param.select("div.app-info-desc > a:eq(0)").text();
//							 String apksize = param.select("span.size").text();
//							 String installurl = param.select("a.com-install-btn").attr("ex_url");
//							 String cateid = doc.select("div.category-wrapper").attr("cateid");
//							 //System.out.println(imgpath+" "+apkname+" "+apkname+" "+apkname+" "+installurl+" "+cateid);
//							 map.put("imgpath", imgpath);
//							 map.put("apkname", apkname);
//							 map.put("name", name);
//							 map.put("apksize", apksize);
//							 map.put("installurl", installurl);
//							 map.put("cateid", cateid);
//							 //Collection collection1 = (Collection) BeanUtils.mapToBean(map, Collection.class);
//							// cc.save(collection1);							 							 
//							 
//						 }
//					 }
//				 }
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
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
				json = collectionService.add(params, userInfo);
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
				json = collectionService.edit(params, userInfo);
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
				json = collectionService.del(idsList, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
}
