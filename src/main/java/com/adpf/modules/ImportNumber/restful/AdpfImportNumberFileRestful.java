/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.ImportNumber.restful;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import com.mysql.jdbc.Connection;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.adpf.modules.ImportNumber.entity.AdpfImportNumberFile;
import com.adpf.modules.ImportNumber.service.AdpfImportNumberFileService;
import com.adpf.modules.ImportNumber.utils.ReadExcelUtliImport;
import com.adpf.modules.gdtm.service.hbase.impl.HBaseCrudServiceImpl;
import com.adpf.modules.gdtm.service.hbase.util.ReadExcelUtli;
import com.adpf.modules.gdtm.service.hive.HiveJDBC;
import com.adpf.modules.gdtm.util.MD5Util;

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/adpf/release/ImportNumber")
public class AdpfImportNumberFileRestful {

	protected static final Logger logger = LogManager.getLogger(AdpfImportNumberFileRestful.class);
    
    @Autowired
    private AdpfImportNumberFileService adpfImportNumberFileService;
    
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
				AdpfImportNumberFile adpfImportNumberFile = (AdpfImportNumberFile)BeanUtils.mapToBean(params, AdpfImportNumberFile.class);				
				json = adpfImportNumberFileService.getList(pageable, adpfImportNumberFile, params);
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
				json = adpfImportNumberFileService.add(params, userInfo);
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
				json = adpfImportNumberFileService.edit(params, userInfo);
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
				json = adpfImportNumberFileService.del(idsList, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 获取文件路径
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	/*
	@RequestMapping(value="/getFilePath",method=RequestMethod.POST)
	public JSONObject getFilePath(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			String strPtname=(String) params.get("FilePath");
			System.out.println(strPtname);
			System.out.println(params);
				           
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}*/
		@RequestMapping(value="/getFilePath",method=RequestMethod.POST)
	    public JSONObject upload (@RequestParam("file") MultipartFile file, @RequestParam Map<String, Object> params,HttpServletRequest request,Connection conn) throws Exception {
	        JSONObject json = new JSONObject();
	        if(!file.isEmpty()){
	        	 String filePath = "C:\\导入号码文件路径\\";
	        	 File dir = new File(filePath);//在项目目录创建upload目录
	             if(! dir.exists()) {
	                 dir.mkdir();
	             }
	        	 String name=file.getOriginalFilename();
	        	 //System.out.println("name"+name);
	             String path = filePath +name;
	             File tempFile = null;
	             tempFile =  new File(path);
              try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
				ReadExcelUtliImport ReadExcel=new ReadExcelUtliImport();
				ReadExcel.readExcelWithTitle(request,path);

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("出错了!!!!!!!!!!!!!!!!!!!");
			}    
	        }
	        return json;
	    }
}
