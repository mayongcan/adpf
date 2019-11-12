package com.adpf.modules.gdtm.service.dpiinf;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;

import com.adpf.modules.gdtm.service.hbase.test.HBaseConnectionUtils;
import com.adpf.modules.gdtm.service.hbase.test.impl.HBaseCrudServiceImplTest;
import com.adpf.modules.gdtm.util.MD5Util;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestHbase {

	public static void main(String arg[]) {

		try {

			InputStream in2 = new BufferedInputStream(new FileInputStream("/app/conversion/data.properties"));
			Properties urlsProp = new Properties();
			urlsProp.load(in2);
			String day = urlsProp.getProperty("day");
			String tableName = urlsProp.getProperty("tableName");
			String rowNum = urlsProp.getProperty("rowNum");
			String filePath = "/app/data/" + day;

			File f = new File("/app/conversion/" + day + "/" + day + "yx.txt");
			if (!f.exists()) {
				f.createNewFile();
			}
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
			BufferedWriter writer = new BufferedWriter(write);
			HBaseCrudServiceImplTest test = new HBaseCrudServiceImplTest();
			Connection connection = null;
			connection = HBaseConnectionUtils.getConnection();
			Table table = connection.getTable(TableName.valueOf(tableName));

			File folder = new File(filePath);
			String file = "";
			// 创建表
			// createTable(tableName, "cf1", "cf2");
			// 文件夹路径 遍历
			if (folder.isDirectory()) {
				System.out.println("路径是文件夹");
				String[] filelist = folder.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filePath + "/" + filelist[i]);
					if (!readfile.isDirectory()) {
						file = readfile.getPath();
					}
					FileInputStream fis1 = new FileInputStream(file);
					InputStreamReader isr1 = new InputStreamReader(fis1);
					BufferedReader br1 = new BufferedReader(isr1);
					String readoneline = null;

//					while ((readoneline = br1.readLine()) != null) {
//						String t[] = readoneline.split(",");
//						// test.getTableList();
//
//						String rowKey = t[Integer.valueOf(rowNum)];
//						JSONObject json = test.getOne(table, rowKey);
//						if (json != null) {
//							JSONArray rows = json.getJSONArray("rows");
//							JSONObject jtmp = (JSONObject) rows.get(0);
//							System.out.println(jtmp.get("ColumnValue"));
//							String md = (String) jtmp.get("ColumnValue");
//							writer.write(md + "," + MD5Util.MD5(md) + "," + readoneline + "\r\n"); // \r\n即为换行
//							writer.flush(); // 把缓存区内容压入文件
//						}
//					}
				}
			}
			if (table != null) {
				table.close();
			}
			writer.close(); // 最后记得关闭文件
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

	}

}
