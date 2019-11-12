package com.adpf.modules.gdtm.service.hbase.test.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Service;

import com.adpf.modules.gdtm.service.hbase.impl.HBaseCrudServiceImpl;
import com.adpf.modules.gdtm.service.hbase.test.HBaseConnectionUtils;
import com.adpf.modules.gdtm.service.hbase.test.HBaseCrudServiceTest;
import com.adpf.modules.gdtm.util.MD5Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gimplatform.core.utils.RestfulRetUtils;


/**
 * http://hbase.apache.org/1.2/apidocs/index.html
 *
 * @author Ricky Fung
 */
@Service
public class HBaseCrudServiceImplTest implements HBaseCrudServiceTest {
	protected static final Logger logger = LogManager.getLogger(HBaseCrudServiceImplTest.class);

	public static void main(String[] args) throws Exception {
		
	}

	// 扫描整表
	public JSONObject scanTable(String tableName) throws IOException {
		Connection connection = null;
		connection = HBaseConnectionUtils.getConnection();
		Table table = connection.getTable(TableName.valueOf(tableName));
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			ResultScanner rs = null;
			try {
				// Scan scan = new Scan(Bytes.toBytes("u120000"), Bytes.toBytes("u200000"));
				rs = table.getScanner(new Scan());
				for (Result r : rs) {
					NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> navigableMap = r.getMap();
					for (Map.Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> entry : navigableMap
							.entrySet()) {
						// logger.info("rowKey:{} columnFamily:{}", Bytes.toString(r.getRow()),
						// Bytes.toString(entry.getKey()));
						json.put("tableName", tableName);
						json.put("rowKey", Bytes.toString(r.getRow()));
						json.put("columnFamily", Bytes.toString(entry.getKey()));
						NavigableMap<byte[], NavigableMap<Long, byte[]>> map = entry.getValue();
						for (Map.Entry<byte[], NavigableMap<Long, byte[]>> en : map.entrySet()) {
							json.put("Column", Bytes.toString(en.getKey()));
							NavigableMap<Long, byte[]> ma = en.getValue();
							for (Map.Entry<Long, byte[]> e : ma.entrySet()) {
								json.put("ColumnKey", e.getKey());
								json.put("ColumnValue", Bytes.toString(e.getValue()));
								System.out.println(json);
							}
							array.add(JSON.parse(JSON.toJSONString(json)));
						}
					}
				}
				System.out.println(array);
			} finally {
				if (rs != null) {
					rs.close();
				}
			}
		} finally {
			if (table != null) {
				table.close();
			}
		}
		return RestfulRetUtils.getRetSuccessWithPage(array, 1);
	}

	// 获取表列表
	public JSONObject getTableList() {
		Connection connection = null;
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			connection = HBaseConnectionUtils.getConnection();
			HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
			HTableDescriptor[] tableDescriptor = admin.listTables();
			for (int i = 0; i < tableDescriptor.length; i++) {
				// System.out.println(tableDescriptor[i].getNameAsString());
				json.put("tableName", tableDescriptor[i].getNameAsString());
				HTableDescriptor tableDescriptors = admin
						.getTableDescriptor(TableName.valueOf(tableDescriptor[i].getNameAsString()));
				json.put("describe", tableDescriptors.toStringCustomizedValues());
				array.add(JSON.parse(JSON.toJSONString(json)));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(array);
		return RestfulRetUtils.getRetSuccessWithPage(array, 1);
	}

	// 表详情
	public JSONObject getTableDetails(String tableName) {
		JSONObject json = new JSONObject();
		Connection connection = null;
		try {
			connection = HBaseConnectionUtils.getConnection();
			HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
			HTableDescriptor tableDescriptors = admin.getTableDescriptor(TableName.valueOf(tableName));
			System.out.println(tableDescriptors.toStringCustomizedValues());
			System.out.println(tableDescriptors.toStringCustomizedValues());
			json.put("details", tableDescriptors.toString());
			System.out.println(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	// 根据文件批量插入
	public void putTextList(String filePath, String tableName, String columnFamily, String column, String column2)
			throws IOException {
		Connection connection = null;
		connection = HBaseConnectionUtils.getConnection();
		Table table = connection.getTable(TableName.valueOf(tableName));
		List<Put> putList = new ArrayList<Put>();
		try {
			File folder = new File(filePath);
			String file = "";
			// 创建表
			// createTable(tableName, "cf1", "cf2");
			String rowKey = "";
			String imei = "";
			String md5 = "";
			// 文件夹路径 遍历
			if (folder.isDirectory()) {
				System.out.println("路径是文件夹");
				String[] filelist = folder.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filePath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
						file = readfile.getPath();
					}
					FileInputStream fis1 = new FileInputStream(file);
					InputStreamReader isr1 = new InputStreamReader(fis1);
					BufferedReader br1 = new BufferedReader(isr1);
					String readoneline = null;

					for (int fileCount = 0; fileCount < 5000; fileCount++) {
						readoneline = br1.readLine();
						if (readoneline == null)
							break;
						String[] nums = readoneline.split(",");
						rowKey = nums[0];
						md5 = rowKey;
						imei = nums[1];
						Put put = new Put(Bytes.toBytes(rowKey));
						put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(imei));
						put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column2), Bytes.toBytes(md5));
						putList.add(put);
					}
					// 扫描
					// scanTable(tableName);
				}
				table.put(putList);
				System.out.println("插入成功");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (table != null) {
				table.close();
			}
		}
	}

	// 循环
	public void putListTest() throws IOException {
		MD5Util md5Util = new MD5Util();
		String str1 = "";
		String md5 = "";
		String imei = "";
		String checkCode = "";
		String filePath = "D:\\imei\\11";
		HBaseCrudServiceImpl h = new HBaseCrudServiceImpl();
		try {
			File folder = new File(filePath);
			String file = "";
			// 文件夹路径 遍历
			if (folder.isDirectory()) {
				System.out.println("路径是文件夹");
				String[] filelist = folder.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filePath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
						file = readfile.getPath();
					}
					FileInputStream fis1 = new FileInputStream(file);
					InputStreamReader isr1 = new InputStreamReader(fis1);
					BufferedReader br1 = new BufferedReader(isr1);
					String readoneline = null;

					for (int fileCount = 0; fileCount < 700000; fileCount++) {
						readoneline = br1.readLine();
						if (readoneline == null)
							break;
						String str = "";
						for (int i1 = 0; i1 < 10000; i1++) {
							str = String.format("%04d", i1);
							str1 = readoneline + str;
							// checkCode = cs.setCheckCode(str1);
							imei = readoneline + str;
							md5 = md5Util.MD5(imei);
							// System.out.println(md5);
							System.out.println(md5 + "," + imei);
		//					h.put("bd_base_phone_contrast", md5, "cf1", "phone", imei);
						}
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 根据row key获取表中的该行数据
	public JSONObject get(String tableName, String rowKey) throws IOException {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		Connection connection = null;
		connection = HBaseConnectionUtils.getConnection();
		Table table = connection.getTable(TableName.valueOf(tableName));
		try {
			Get get = new Get(Bytes.toBytes(rowKey));
			Result result = table.get(get);
			NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> navigableMap = result.getMap();
			for (Map.Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> entry : navigableMap.entrySet()) {
				logger.info("columnFamily:{}", Bytes.toString(entry.getKey()));
				json.put("rowKey", rowKey);
				json.put("columnFamily", Bytes.toString(entry.getKey()));
				NavigableMap<byte[], NavigableMap<Long, byte[]>> map = entry.getValue();
				for (Map.Entry<byte[], NavigableMap<Long, byte[]>> en : map.entrySet()) {
					// System.out.print(Bytes.toString(en.getKey()) + "##");
					json.put("Column", Bytes.toString(en.getKey()));
					NavigableMap<Long, byte[]> nm = en.getValue();
					for (Map.Entry<Long, byte[]> me : nm.entrySet()) {
						logger.info("column key:{}, value:{}", me.getKey(), new String(me.getValue()));
						json.put("ColumnKey", me.getKey());
						json.put("ColumnValue", new String(me.getValue()));
						array.add(JSON.parse(JSON.toJSONString(json)));
					}
				}
			}
		} finally {
			if (table != null) {
				table.close();
			}
		}
		return RestfulRetUtils.getRetSuccessWithPage(array, 1);
	}

	/** 批量插入可以使用 Table.put(List<Put> list) **/
	public void put(String tableName, String rowKey, String columnFamily, String column, String data)
			throws IOException {
		Connection connection = null;
		connection = HBaseConnectionUtils.getConnection();
		Table table = connection.getTable(TableName.valueOf(tableName));
		try {
			Put put = new Put(Bytes.toBytes(rowKey));
			put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(data));
			table.put(put);
			System.out.println("插入成功");
		} finally {
			if (table != null) {
				table.close();
			}
		}
	}

	// 创建表
	public void createTable(String tableName, String... columnFamilies) throws IOException {
		Connection connection = null;
		connection = HBaseConnectionUtils.getConnection();
		HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
		try {
			logger.info("columnFamily:{}", "createTablecreateTable");
			logger.info("columnFamily:{}", "adminadmin");
			if (admin.tableExists(tableName)) {
				logger.warn("table:{} exists!", tableName);
			} else {
				logger.info("columnFamily:{}", "4343343535");
				// HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
				HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
				for (String columnFamily : columnFamilies) {
					System.out.println(columnFamily);
					tableDescriptor.addFamily(new HColumnDescriptor(columnFamily));
				}
				logger.info("columnFamily:{}", "334354546575");
				admin.createTable(tableDescriptor);
				logger.info("create table:{} success!", tableName);
				System.out.println("创建成功");
			}
		} catch (Exception e) {
			// logger.info("columnFamily:{}", "222222222");
			// logger.info("columnFamily:{}", e);
			System.out.println(e.getMessage());
			System.out.println(e.fillInStackTrace().getMessage());
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String strs = sw.toString();
			System.out.println("--------------------------美丽的分割线----------------------");
			System.out.println(strs);
		} finally {
			if (admin != null) {
				admin.close();
			}
		}
	}

	// 删除表中的数据
	public void deleteTable(String tableName) throws IOException {
		Connection connection = null;
		connection = HBaseConnectionUtils.getConnection();
		HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
		try {
			if (admin.tableExists(tableName)) {
				// 必须先disable, 再delete
				admin.disableTable(tableName);
				admin.deleteTable(tableName);
			}
			System.out.println("删除成功");
		} finally {
			if (admin != null) {
				admin.close();
			}
		}
	}

	public void disableTable(Connection connection, TableName tableName) throws IOException {
		Admin admin = null;
		try {
			admin = connection.getAdmin();
			if (admin.tableExists(tableName)) {
				admin.disableTable(tableName);
			}
		} finally {
			if (admin != null) {
				admin.close();
			}
		}
	}
}
