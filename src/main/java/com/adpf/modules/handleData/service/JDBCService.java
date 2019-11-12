package com.adpf.modules.handleData.service;

import com.alibaba.fastjson.JSONObject;


public interface JDBCService {
	
	/**
	 * 创建表
	 * @param test
	 * @return
	 */
	public void createTbale(String table);
	/**
	 * 插入数据
	 * @param test
	 * @return
	 */
	public String insertData(String table,String file);
	/**
	 * 移动数据到旧表
	 * @param test
	 * @return
	 */
	public JSONObject moveToOldTable(String table,String table_out);
	/**
	 * 去重
	 * @return
	 */
	public JSONObject ToRepeat(String table,String tableA,String tableB);
	/**
	 * 自定义表
	 * @return
	 */
	public JSONObject Custom();
	/**
	 * 归属地
	 * @return
	 */
	public JSONObject PlaceOfBelonging(String table,String table_out);
}
