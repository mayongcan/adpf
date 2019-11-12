/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.tagdetails.restful;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;


import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.adpf.modules.gdtm.tagdetails.entity.AdpfTagDetails;
import com.adpf.modules.gdtm.tagdetails.service.AdpfTagDetailsService;

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/adpf/tagdetail")
public class AdpfTagDetailsRestful {

	protected static final Logger logger = LogManager.getLogger(AdpfTagDetailsRestful.class);
    
    @Autowired
    private AdpfTagDetailsService adpfTagDetailsService;
    
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
				AdpfTagDetails adpfTagDetails = (AdpfTagDetails)BeanUtils.mapToBean(params, AdpfTagDetails.class);				
				json = adpfTagDetailsService.getList(pageable, adpfTagDetails, params);
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
				json = adpfTagDetailsService.add(params, userInfo);
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
				json = adpfTagDetailsService.edit(params, userInfo);
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
				json = adpfTagDetailsService.del(idsList, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	
	
	 @RequestMapping("/upload")
	    @ResponseBody
	    public JSONObject upload (@RequestParam("file") MultipartFile file, @RequestParam Map<String, Object> params,HttpServletRequest request) throws IOException {
	        JSONObject json = new JSONObject();
	       
	     
	        if(!file.isEmpty()){
	        	
	        	 String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
	        	 System.out.println(filePath);        	
	        	 File dir = new File(filePath);//在项目目录创建upload目录
	             if(! dir.exists()) {
	                 dir.mkdir();
	             }
	             String path = filePath +file.getOriginalFilename();
	             File tempFile = null;
	             tempFile =  new File(path);
                 FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
                
                 //读取上传缓存到本地的文件
                 FileInputStream f=new FileInputStream(path);
                 InputStreamReader ins=new InputStreamReader(f);
                 BufferedReader br = new BufferedReader(ins);
                 
                 String str = "";
                
                //StringBuffer sb = new StringBuffer();
     			while ((str = br.readLine()) != null) {
     				    int i=1;
     					String[] aa=str.split(","); 
     					
     				Map<String , Object>map=new HashMap<>();
     				map.put("id",i++);
     				map.put("proviences", aa[2]);
     				map.put("equipmentCode", aa[6]);    				
     				map.put("phoneNum", aa[7]);   
     				map.put("visits", aa[14]);    				
     				map.put("appId", aa[3]);				
     				json=add(request, map);
    		
     			}
     				
     			/*File ff=new File(tempFile.toURI());
     			Boolean flage=ff.delete();
     			System.out.println("删除文件"+flage);*/
     			f.close();
     			ins.close();
	        }
      
	        return json;
	        
	        
	        
	        
	        
	    }
}
