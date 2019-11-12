package com.adpf.modules.gdtm.service.hbase.test;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;

import com.alibaba.fastjson.JSONObject;

public interface HBaseCrudServiceTest {
	
	public JSONObject getTableList();
	
	public JSONObject scanTable(String tableName) throws IOException;
	
	public  void putTextList(String filePath,String tableName,String columnFamily,String column,String column2) throws IOException;
	
	public JSONObject get(String tableName, String rowKey) throws IOException;
	
	public void put(String tableName, String rowKey, String columnFamily, String column,String data) throws IOException;
	
	public void createTable(String tableName, String... columnFamilies) throws IOException;
	
	public void deleteTable(String tableName) throws IOException;
	
	public void disableTable(Connection connection, TableName tableName) throws IOException;
	
	public JSONObject getTableDetails(String tableName) throws IOException;
}
