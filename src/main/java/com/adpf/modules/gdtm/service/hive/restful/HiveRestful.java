package com.adpf.modules.gdtm.service.hive.restful;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adpf.modules.gdtm.service.hive.HiveService;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;


@RestController
@RequestMapping("/api/adpf/hive")
public class HiveRestful {
	
	@Autowired
	HiveService hiveService;

	/**
	 * 用于记录打开日志
	 * @param request
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public JSONObject index(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}
	
	/**
	 * 查询列表
	 * 
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public JSONObject getList(HttpServletRequest request) throws SQLException{
		JSONObject json = new JSONObject();
		System.out.println(1);
		json = hiveService.showTables();
		return json;
	}
	
	/**
	 * 删除表
	 * 
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public JSONObject del(HttpServletRequest request,@RequestParam String hiveTableName) throws SQLException{
		JSONObject json = new JSONObject();
		System.out.println(hiveTableName);
		hiveService.deopTable(hiveTableName);
		return RestfulRetUtils.getRetSuccess();
	}
	
	/**
	 * 查询表结构
	 * 
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/detailsHive", method = RequestMethod.GET)
	public JSONObject detailsHive(HttpServletRequest request, @RequestParam String tableName) throws SQLException{
		JSONObject json = new JSONObject();
		System.out.println(tableName);
		json = hiveService.descTable(tableName);
		return json;
	}
	
	/**
	 * 统计..
	 *
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public JSONObject countData(HttpServletRequest request,@RequestParam String hiveTableName) throws Exception{
		JSONObject json = new JSONObject();
		System.out.println(hiveTableName);
		hiveService.countData(hiveTableName,"","");
		return RestfulRetUtils.getRetSuccess();
	}
	
	/**
	 * 统计..
	 *
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/countT", method = RequestMethod.GET)
	public JSONObject countData1(HttpServletRequest request) throws Exception{

			hiveService.countData("adpf_label","201808","08");
			hiveService.countData1("adpf_label","201808","08");
		
		return RestfulRetUtils.getRetSuccess();
	}
	
	/**
	 * 缓存到hbase
	 * @return
	 */
	@RequestMapping(value = "/cacheHbase", method = RequestMethod.GET)
	public JSONObject test(HttpServletRequest request,@RequestParam String HiveTableName,@RequestParam String HBaseTableName, @RequestParam String date) throws Exception{
		System.out.println(HiveTableName);
		System.out.println(HBaseTableName);
		System.out.println(date);
		hiveService.countData2(HiveTableName,HBaseTableName,date);
		return RestfulRetUtils.getRetSuccess();
	}
	
	/**
	 * 创建自定义表
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public JSONObject create(HttpServletRequest request, @RequestParam Map<String,Object> parms) throws SQLException{
		
		String tableType = (String) parms.get("tableType");
		String field = (String) parms.get("field");
		String hiveTableName = (String) parms.get("hiveTableName");
		String division = (String) parms.get("division");
		
		System.out.println(parms.get("tableType"));
		hiveService.createHiveTable(tableType,field,hiveTableName,division);
		return RestfulRetUtils.getRetSuccess();
	}
	
	/**
	 * 创建分区表
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/createUser", method = RequestMethod.GET)
	public JSONObject createUserInfo(HttpServletRequest request, @RequestParam String hiveTableName) throws SQLException{
		
		System.out.println(hiveTableName);
//		hiveService.createUserTable(HiveTableName);
		//创建分区表
		hiveService.createPartition(hiveTableName);
		return RestfulRetUtils.getRetSuccess();
	}
	
	/**
	 * 装载数据
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/loadData", method = RequestMethod.GET)
	public JSONObject loadData(HttpServletRequest request,@RequestParam String Hive_TableName,@RequestParam String filePath) throws Exception{
		System.out.println(filePath);
		System.out.println(Hive_TableName);
		hiveService.loadData(filePath, Hive_TableName);
		return RestfulRetUtils.getRetSuccess();
	}
}
