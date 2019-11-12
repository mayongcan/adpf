package com.adpf.modules.gdtm.service.dpiinf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.NavigableMap;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.adpf.modules.gdtm.service.hbase.test.HBaseConnectionUtils;
import com.adpf.modules.gdtm.util.MD5Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;

public class TransMobile {

	public static void main(String[] args) {
		try {

			Connection connection = null;
			Table table = null;

			connection = HBaseConnectionUtils.getConnection();
			table = connection.getTable(TableName.valueOf("mobile_guangdong"));
			File f = new File(
					"/Users/zhijianzhang/工作目录/4.22017项目工程/3.大数据精准营销/gtja-1.csv");
			if (!f.exists()) {
				f.createNewFile();
			}
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f, true), "utf-8");
			BufferedWriter writer = new BufferedWriter(write);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(
					"/Users/zhijianzhang/工作目录/4.22017项目工程/3.大数据精准营销/gtja.csv")));
			String line = null;
			while ((line = br.readLine()) != null) {

				String a[] = StringUtils.split(line, ",");
				System.out.println(a[11]);
				JSONObject json = getOne(table, a[11]);
				String md = "";
				if (json != null) {
					JSONArray rows = json.getJSONArray("rows");
					JSONObject jtmp = (JSONObject) rows.get(0);
					md = (String) jtmp.get("ColumnValue");
					writer.write(a[12] + "," + md + "," + a[3] );
					writer.write("\r\n");// 换行
				}

			}

			writer.flush(); // 把缓存区内容压入文件
			writer.close(); // 最后记得关闭文件

			if (table != null) {
				table.close();
			}
			connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 根据row key获取表中的该行数据
	public static JSONObject getOne(Table table, String rowKey) throws IOException {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();

		try {
			Get get = new Get(Bytes.toBytes(rowKey));
			Result result = table.get(get);
			NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> navigableMap = result.getMap();
			if (navigableMap == null) {
				return null;
			}
			for (Map.Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> entry : navigableMap.entrySet()) {
//					System.out.println("columnFamily:{}" + Bytes.toString(entry.getKey()));
				json.put("rowKey", rowKey);
				json.put("columnFamily", Bytes.toString(entry.getKey()));
				NavigableMap<byte[], NavigableMap<Long, byte[]>> map = entry.getValue();
				for (Map.Entry<byte[], NavigableMap<Long, byte[]>> en : map.entrySet()) {
					// System.out.print(Bytes.toString(en.getKey()) + "##");
					json.put("Column", Bytes.toString(en.getKey()));
					NavigableMap<Long, byte[]> nm = en.getValue();
					for (Map.Entry<Long, byte[]> me : nm.entrySet()) {
							System.out.println("column key:{}, value:{}" + me.getKey() + " " + new String(me.getValue()));
						json.put("ColumnKey", me.getKey());
						json.put("ColumnValue", new String(me.getValue()));
						array.add(JSON.parse(JSON.toJSONString(json)));
					}
				}
			}
		} finally {

		}
		return RestfulRetUtils.getRetSuccessWithPage(array, 1);
	}
}
