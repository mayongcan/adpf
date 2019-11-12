package com.adpf.modules.gdtm.service.hive.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.adpf.modules.gdtm.service.hive.MatchedDataService;
import com.adpf.modules.gdtm.service.hive.util.HiveJDBCConnectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class MatchedDataServiceImpl implements MatchedDataService{
	
	private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    protected static final Logger logger = LogManager.getLogger(HiveServiceImpl.class);
    FileWriter writer;
    BufferedWriter bw;
    String path3;
    
	@Override
	public String MatchedData(String path){
		 try {
			 //得到文件名
			 String [] filenName=path.split("\\");
			 String [] file1=filenName[filenName.length-1].split(".");
			 String pathN=file1[0];
			 System.out.println(pathN);
			 
			 //创建表
			 HiveJDBCConnectionUtils h = new HiveJDBCConnectionUtils();
	        	stmt = h.Connection();
	        	String sql = "create table "+ pathN +"(\n" +
	        			"data string,\n" +
	                     ")\n" +
	                  "row format delimited fields terminated by ','";
	        	 System.out.println("Running1: " + sql);
				stmt.execute(sql);
				
		//加载文件数据到表
		String sql1 = "load data inpath '" + path + "' into table "+pathN;
        System.out.println("Running2: " + sql1);
		stmt.execute(sql1);
			
		JSONObject json = new JSONObject();
 	     JSONArray array = new JSONArray();
 	     //查询数据
		 String sql2 = "select tel,imei from pathN a join pathNA b on a.label = b.key";
		 rs = stmt.executeQuery(sql2);
		 
		 //创建文件
		 /*String path3=path;
		 path3.replace("文件返回数据文件.", ".");*/
		 path3="C:\\"+pathN+"文件返回数据文件.txt";
		 File newfile = new File(path3);
         if (!newfile.exists()) {
             newfile.createNewFile();
         }else{
        	 return "文件已存在！";
         }
         //写入文件
		  writer = new FileWriter(path3);
          bw = new BufferedWriter(writer);
			while (rs.next()) {
				bw.write(rs.getString("tel")+","+rs.getString("imei")+"\r\n");
			 }
			
			//删除表
			/*HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();
			stmt = connection.Connection();	*/
        	String sql4 = "drop table if exists "+pathN;
			stmt.execute(sql4);
		 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			 try {
			   	bw.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "数据返回成功！路径："+path3;
	}
}
