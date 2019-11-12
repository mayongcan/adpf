package com.adpf.modules.gdtm.service.hive;

import java.io.IOException;
import java.sql.SQLException;

import com.alibaba.fastjson.JSONObject;

public interface HiveService {
	
	// 创建数据库
	public void createDatabase(String base);
	
	// 查询所有数据库
	public void showDatabases();
	
	// 创建表
	public void createTable(String field,String tableName,String Hbase_tableName,String Hbase_field);
	
	//创建自定义表
	public void createHiveTable(String tableType,String field,String hiveTableName,String division) throws SQLException;
	
	// 创建表
	public void createUserTable(String hiveTableName) throws SQLException;
	
	// 查询所有表
	public JSONObject showTables() throws SQLException;
	
	// 查询表信息
	public JSONObject descTable(String tableName) throws SQLException;
	
	// 加载数据
	public void loadData(String filePath,String Hive_TableName) throws Exception;
	
	// 查询数据
	public void selectData(String tableName);
	 
	// 统计查询
	public JSONObject countData(String hiveTableName,String month,String day) throws Exception;
	 
	 
	// 删除数据库
	public void dropDatabase(String base);
	 
	// 删除数据库表
	public JSONObject deopTable(String HiveTableName) throws SQLException;
	 
	// 释放资源
	public void destory() throws SQLException;
	
	public JSONObject selectData();

	//统计
	public JSONObject countData1(String hiveTableName,String month,String day) throws Exception;

	//省份统计
	public JSONObject countData2(String hiveTableName, String hBaseTableName,String date) throws Exception;
	
	//创建分区表
	public void createPartition(String hiveTableName) throws SQLException;


}
