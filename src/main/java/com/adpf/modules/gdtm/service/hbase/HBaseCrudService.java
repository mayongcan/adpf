package com.adpf.modules.gdtm.service.hbase;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.Scan;

import com.alibaba.fastjson.JSONObject;

public interface HBaseCrudService {
	
	//创建表
	public void createTable(String tableName, String... familyName) throws IOException;
	
	//查询
//	public List searchAll(final String tableName,final Scan scan);
	
	//插入
	public Boolean put(String tableName,String rowKey,String familyName,String qualifier,String value);
	
	//删除
	public JSONObject deleteTable(String tableName) throws IOException;
	
	//获取表列表
	public JSONObject getTableList();
	
	//遍历文件夹 插入
	public void filePut(String filePath,String tableName,String familyName,String qualifier);

	//表详情
	public JSONObject getTableDetails(String tableName);
	
	//获取前几条数据
	public JSONObject getTableData(String tableName,String rowKey, String familyName, Integer limit) throws IOException ;
	
	//删除一行数据
	public void del(Map<String, Object> params);
	
	public Long count(String tableName,String column);
	
	public JSONObject getTableDataLong(String tableName, String rowKey, Integer limit) throws IOException;

}
