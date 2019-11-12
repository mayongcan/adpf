package com.adpf.restful;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adpf.modules.gdtm.service.hbase.HBaseCrudService;
import com.adpf.modules.gdtm.service.hbase.util.BasicLabel;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;

@RestController
@RequestMapping("/api/adpf/hbase")
public class HBaseRestful {
	
	@Autowired
	private HBaseCrudService hbaseCrudService;
	
	@Autowired
	private BasicLabel basicLabel;
	
	
	/**
	 * 用于记录打开日志
	 * @param request
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public JSONObject index(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}
	
	/**
	 * 表结构列表
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public JSONObject getList(HttpServletRequest request, @RequestParam Map<String,Object> parms) throws IOException{
		JSONObject json = new JSONObject();
		String tableName = (String) parms.get("tableName");
		String rowKey = (String) parms.get("rowKey");
		String familyName = (String) parms.get("familyName");
		String limitStr = (String) parms.get("limit");
		Integer limit = null;
		if(limitStr!=""){
			limit = Integer.valueOf(limitStr);
		}
		if(tableName ==""){
			json = hbaseCrudService.getTableList();
		}else {
			if(limit == null) {				
				limit = 1;
			}
			json = hbaseCrudService.getTableData(tableName, rowKey, familyName, limit);
		}	
		return json;
	}
	
	@RequestMapping(value = "/getListLong", method = RequestMethod.GET)
	public JSONObject getListLong(HttpServletRequest request, @RequestParam Map<String,Object> parms) throws IOException{
		JSONObject json = new JSONObject();
		String tableName = (String) parms.get("tableName");
		String rowKey = (String) parms.get("rowKey");
		Integer limit = null;
		
		if(rowKey!="") {
			System.out.println(1);
			limit = 1;
		}
		if(rowKey=="") {
			System.out.println(2);
			limit = 15;
		}
		json = hbaseCrudService.getTableDataLong(tableName, rowKey, limit);
		return json;
	}
	
	/**
	 * 表详情
	 * 
	 * @param tableName
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public JSONObject getDetails(HttpServletRequest request,@RequestParam String tableName){
		JSONObject json = new JSONObject();
		json = hbaseCrudService.getTableDetails(tableName);
		return json;
	}
	
	/**
	 * 创建表
	 * 
	 * @param tableName
	 * @param familyName
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public JSONObject create(HttpServletRequest request, @RequestParam String tableName, @RequestParam String... familyName) throws IOException{
		JSONObject json = new JSONObject();
		hbaseCrudService.createTable(tableName, familyName);
		json = RestfulRetUtils.getRetSuccess();
		return json;
	}
	
	/**
	 * 删除表
	 * 
	 * @param tableName
	 * @param familyName
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JSONObject delete(HttpServletRequest request, @RequestParam String tableName){
		JSONObject json = new JSONObject();
		try {
			hbaseCrudService.deleteTable(tableName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		json = RestfulRetUtils.getRetSuccess();
		return json;
	}
	
	/**
	 * 插入
	 * 
	 * @param tableName
	 * @param familyName
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/put", method = RequestMethod.POST)
	public JSONObject put(HttpServletRequest request, @RequestParam Map<String,Object> parms){
		JSONObject json = new JSONObject();
		String tableName = (String) parms.get("tableName");
		String rowKey = (String) parms.get("rowKey");
		String familyName = (String) parms.get("familyName");
		String qualifier = (String) parms.get("qualifier");
		String value = (String) parms.get("value");
		String filePath = (String) parms.get("filePath");
		if(filePath == "" || filePath == null) {
			//单条插入
			System.out.println(1);
			hbaseCrudService.put(tableName,rowKey,familyName,qualifier,value);
		}else {
			//文件插入
			System.out.println(2);
			hbaseCrudService.filePut(filePath,tableName,familyName,qualifier);
		}
		json = RestfulRetUtils.getRetSuccess();
		return json;
	}
	

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public JSONObject test(HttpServletRequest request, @RequestParam Map<String,Object> parms){
		JSONObject json = new JSONObject();
		String filePath = (String) parms.get("filePath");
		String errfilePath = (String) parms.get("errfilePath");
		String hbaseTableName = (String) parms.get("hbaseTableName");
		String familyName = (String) parms.get("familyName");
		System.out.println(filePath);
		System.out.println(errfilePath);
		System.out.println(hbaseTableName);
		System.out.println(familyName);
		try {
			basicLabel.LabelPut(filePath, errfilePath, hbaseTableName, familyName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		json = RestfulRetUtils.getRetSuccess();
		return json;
	}
}
