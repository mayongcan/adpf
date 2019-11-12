package com.adpf.modules.gdtm.service.hbase.impl;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.ResultsExtractor;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//import com.adpf.modules.gdtm.count.entity.AdpfLabelCount;
//import com.adpf.modules.gdtm.count.repository.AdpfLabelCountRepository;
import com.adpf.modules.gdtm.service.hbase.HBaseCrudService;
import com.adpf.modules.gdtm.service.hbase.test.HBaseConnectionUtils;
import com.adpf.modules.gdtm.tagLibrary.entity.AdpfTagLibrary;
import com.adpf.modules.gdtm.tagLibrary.repository.AdpfTagLibraryRepository;
import com.adpf.modules.gdtm.util.MD5Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RedisUtils;
import com.gimplatform.core.utils.RestfulRetUtils;

import redis.clients.jedis.Jedis;

@Service
public class HBaseCrudServiceImpl implements HBaseCrudService {
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring-hbase.xml" });

	BeanFactory factory = (BeanFactory) context;
	HbaseTemplate hbaseTemplate = (HbaseTemplate) factory.getBean("htemplate");
	private static boolean zz = true;
	Connection connection = null;
	Table table = null;

	public static void main(String[] args) throws IOException {
		HBaseCrudServiceImpl h = new HBaseCrudServiceImpl();

	}

	@Autowired
	private AdpfTagLibraryRepository adpfTagLibraryRepository;

	// 创建表
	@Override
	public void createTable(String tableName, String... familyName) throws IOException {
		Connection connection = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration());
		HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
		try {
			if (admin.tableExists(tableName)) {
				System.out.println("表已存在");
			} else {
				HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
				for (String columnFamily : familyName) {
					System.out.println(columnFamily);
					tableDescriptor.addFamily(new HColumnDescriptor(columnFamily));
				}
				admin.createTable(tableDescriptor);
				System.out.println("创建成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (admin != null) {
				admin.close();
			}
		}
	}

	// 插入数据
	@Override
	public Boolean put(final String tableName, final String rowKey, final String familyName, final String qualifier,
			final String value) {
		return hbaseTemplate.execute(tableName, new TableCallback<Boolean>() {

			@Override
			public Boolean doInTable(HTableInterface table) throws Throwable {
				boolean flag = false;
				try {
					table.setAutoFlushTo(false);
					Put put = new Put(Bytes.toBytes(rowKey));
					put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(qualifier), Bytes.toBytes(value));
					table.put(put);
					table.flushCommits();

					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					table.close();
				}
				System.out.println("插入成功");
				return flag;
			}

		});
	}

	// 遍历文件夹
	public void filePut(String filePath, final String tableName, String familyName, final String qualifier) {
		// String filePath = "D:\\imei";
		File folder = new File(filePath);
		String file = "";
		String rowKey = "";
		String value = "";
		// 文件夹路径 遍历
		if (folder.isDirectory()) {
			System.out.println("路径正确");
			String[] filelist = folder.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filePath + "\\" + filelist[i]);
				if (!readfile.isDirectory()) {
					file = readfile.getPath();
				}
				try {
					FileInputStream fis1 = new FileInputStream(file);
					InputStreamReader isr1 = new InputStreamReader(fis1);
					BufferedReader br1 = new BufferedReader(isr1);
					String readoneline = null;
					for (int fileCount = 0; fileCount < 5000; fileCount++) {
						try {
							readoneline = br1.readLine();
							if (readoneline == null)
								break;
							String[] nums = readoneline.split(",");
							rowKey = nums[0];
							value = nums[1];
							System.out.println(value);
							put(tableName, rowKey, familyName, qualifier, value);
						} catch (Exception e) {
							// 写入异常imei
							File file2 = new File("D://imei1/error.txt");
							FileOutputStream outFile = new FileOutputStream(file2, true);
							outFile.write((readoneline + "\r\n").getBytes());
							outFile.flush();
							outFile.close();
						}
					}
					br1.close();
					isr1.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 删除表
	@Override
	public JSONObject deleteTable(String tableName) throws IOException {
		// HBaseAdmin admin = new HBaseAdmin(hbaseTemplate.getConfiguration());
		Connection connection = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration());
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
		return null;
	}

	// 获取表列表
	public JSONObject getTableList() {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			Connection connection = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration());
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
		try {
			Connection connection = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration());
			HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
			HTableDescriptor tableDescriptors = admin.getTableDescriptor(TableName.valueOf(tableName));
			// System.out.println(tableDescriptors.toStringCustomizedValues());
			System.out.println(tableDescriptors.toStringCustomizedValues());
			json.put("details", tableDescriptors.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	// 获取表的limit条数据
	public JSONObject getTableData(String tableName, String rowKey, String familyName, Integer limit)
			throws IOException {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		System.out.println(tableName);
		Connection connection = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration());
		Table table = connection.getTable(TableName.valueOf(tableName));
		Scan scan = new Scan();
		if (rowKey != "") {
			scan.setStartRow(rowKey.getBytes());
		}
		if (familyName != "") {
			String[] cfsArr = familyName.split(",", -1);
			for (String cf : cfsArr) {
				scan.addFamily(cf.getBytes());
			}
		}
		ResultScanner scanner = table.getScanner(scan);
		Result[] rows = scanner.next(limit);
		for (Result row : rows) {
			// Cell[] cells = row.rawCells();
			// json.put("data", cells[0]);
			for (KeyValue kv : row.raw()) {
				json.put("tableName", tableName);
				json.put("rowKey", Bytes.toString(row.getRow()));
				json.put("columnFamily", Bytes.toString(kv.getFamily()));
				json.put("Column", Bytes.toString(kv.getQualifier()));
				json.put("ColumnValue", Bytes.toString(kv.getValue()));
				json.put("Timestamp", kv.getTimestamp());
				array.add(JSON.parse(JSON.toJSONString(json)));
			}
		}
		scanner.close();
		table.close();
		System.out.println(array);
		return RestfulRetUtils.getRetSuccessWithPage(array, limit);
	}

	// 获取表的limit条数据(long 类型)
	public JSONObject getTableDataLong(String tableName, String rowKey, Integer limit) throws IOException {
		RedisUtils ru = new RedisUtils();
		if (zz == true) {
			List<AdpfTagLibrary> list = adpfTagLibraryRepository.findAll();
			for (int i = 0; i < list.size(); i++) {
				ru.set(list.get(i).getTagId(), list.get(i).getLabel(), 0);
			}
			zz = false;
		}
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		System.out.println(tableName);
		Connection connection = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration());
		Table table = connection.getTable(TableName.valueOf(tableName));
		Scan scan = new Scan();
		if (rowKey != "") {
			scan.setStartRow(rowKey.getBytes());
		}
		ResultScanner scanner = table.getScanner(scan);
		Result[] rows = scanner.next(limit);
		for (Result row : rows) {
			for (KeyValue kv : row.raw()) {
				String col = Bytes.toString(kv.getQualifier());
				if (!col.equals("tel")) {
					String value = "";
					if (ru.get(Bytes.toString(kv.getQualifier())) == null) {
						value = Bytes.toString(kv.getQualifier());
					} else {
						value = ru.get(Bytes.toString(kv.getQualifier()));
					}
					json.put("tableName", tableName);
					json.put("rowKey", Bytes.toString(row.getRow()));
//					json.put("columnFamily",Bytes.toString(kv.getFamily()));
//					json.put("ColumnValue",Bytes.toLong(kv.getValue()));
					json.put("Timestamp", kv.getTimestamp());
					json.put("Column", value);
					json.put("ColumnValue", Bytes.toLong(kv.getValue()));
					array.add(JSON.parse(JSON.toJSONString(json)));
				} else {
//					json.put("Column",Bytes.toString(kv.getQualifier()));
//					json.put("ColumnValue",Bytes.toString(kv.getValue()));
				}
			}
		}
		scanner.close();
		table.close();
		System.out.println(array);
		return RestfulRetUtils.getRetSuccessWithPage(array, limit);
	}

	// 测试
	public JSONObject getTableData1() throws IOException {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		Connection connection = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration());
		Table table = connection.getTable(TableName.valueOf("adpf_user_label"));
		Scan scan = new Scan();

		scan.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("tel"));

		ResultScanner scanner = table.getScanner(scan);
		long rowCount = 0;
		for (Result row : scanner) {
			rowCount += row.size();
			for (KeyValue kv : row.raw()) {
				// json.put("rowKey",Bytes.toString(row.getRow()));
				// json.put("columnFamily",Bytes.toString(kv.getFamily()));
				// json.put("Column",Bytes.toString(kv.getQualifier()));
				json.put("ColumnValue", Bytes.toString(kv.getValue()));
				// json.put("Timestamp",kv.getTimestamp());
				array.add(JSON.parse(JSON.toJSONString(json)));
				// System.out.println(Bytes.toLong(kv.getValue()));
			}
		}
		System.out.println(rowCount);
		scanner.close();
		table.close();
		System.out.println(array);
		return RestfulRetUtils.getRetSuccessWithPage(array, 1);
	}

	public JSONObject getTableData11(String rowKey) throws IOException {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		Connection connection = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration());
		Table table = connection.getTable(TableName.valueOf("adpf_user_label"));
		Scan scan = new Scan();

		scan.setStartRow(rowKey.getBytes());
		scan.addFamily(Bytes.toBytes("cf1"));

		ResultScanner scanner = table.getScanner(scan);
		Jedis ru = new Jedis("localhost");
		FileOutputStream outFile = null;
		File file2 = new File("E:\\zzz\\sss.txt");
		for (Result row : scanner) {
			// row.size();
			for (KeyValue kv : row.raw()) {
				String col = Bytes.toString(kv.getQualifier());
				if (!col.equals("tel")) {
					outFile = new FileOutputStream(file2, true);
					String value = "";
					if (ru.get(Bytes.toString(kv.getQualifier())) == null) {
						value = Bytes.toString(kv.getQualifier());
					} else {
						value = ru.get(Bytes.toString(kv.getQualifier()));
					}
					json.put("rowKey", Bytes.toString(row.getRow()));
					json.put("Column", value);
					json.put("ColumnValue", Bytes.toLong(kv.getValue()));
					System.out.println(Bytes.toString(row.getRow()) + value);
					outFile.write(
							(Bytes.toString(row.getRow()) + "," + value + "," + Bytes.toLong(kv.getValue()) + "\r\n")
									.getBytes());
					outFile.flush();
				}
			}

		}
		outFile.close();
		scanner.close();
		table.close();
		return RestfulRetUtils.getRetSuccessWithPage(array, 1);
	}

	// 根据表名 rowkey 列族 列 取值
	public String get(String tableName, String rowName, String familyName, String qualifier) {
		return hbaseTemplate.get(tableName, rowName, familyName, qualifier, new RowMapper<String>() {
			public String mapRow(Result result, int rowNum) throws Exception {
				String value = "";
				List<Cell> ceList = result.listCells();
				if (ceList != null && ceList.size() > 0) {
					for (Cell cell : ceList) {
						String res = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
						// 获取值
						value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
						// 获取列簇
						String family = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(),
								cell.getFamilyLength());
						// 获取列
						String quali = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
								cell.getQualifierLength());
					}
				}
				return value;
			}
		});
	}

	// 弃用 hbase某列数据插入mysql
	public Long count(String tableName, final String column) {
		// 统计表中行数
		final Map<String, Object> params = new HashMap<>();
		Scan scan = new Scan();
		// 通过列扫描
		scan.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes(column));
		// Filter查询数据为空的话，可能会降低性能
		// scan.setFilter(new FirstKeyOnlyFilter());
		Long count = hbaseTemplate.find(tableName, scan, new ResultsExtractor<Long>() {
			public Long extractData(ResultScanner results) throws Exception {
				// 列的个数
				long rowCount = 0;
				// 某标签的总权重
				long weight = 0;
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String newDate = sdf.format(date.getTime());
				for (Result result : results) {
					rowCount += result.size();
					for (KeyValue kv : result.raw()) {
						// 权重累加
						weight += Long.parseLong(Bytes.toString(kv.getValue()));
						params.put("name", Bytes.toString(kv.getQualifier()));
						System.out.println(Bytes.toString(kv.getValue()));
						System.out.println(Bytes.toString(kv.getQualifier()));
					}
				}
				System.out.println(params);
				// 判断hbase是否有数据
				if (params.size() > 0) {
					params.put("date", newDate);
					params.put("size", rowCount);
					params.put("weight", weight);
				}
				return rowCount;
			}
		});
		return count;
	}

	// 删除一行
	public void del(Map<String, Object> params) {
		String tableName = (String) params.get("tableName");
		String rowName = (String) params.get("rowName");
		String familyName = (String) params.get("familyName");
		String qualifier = (String) params.get("qualifier");
		hbaseTemplate.delete(tableName, rowName, familyName, qualifier);
		System.out.println("删除" + rowName + "成功");
	}

	public Long count1(String tableName) throws IOException {
		String filePath = "E:\\test";
		File folder = new File(filePath);
		String file = "";

		// 文件夹路径 遍历
		if (folder.isDirectory()) {
			String[] filelist = folder.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filePath + "\\" + filelist[i]);
				if (!readfile.isDirectory()) {
					file = readfile.getPath();
				}
				System.out.println(readfile);

				FileInputStream fis1;
				String str = "";
				fis1 = new FileInputStream(file);
				InputStreamReader isr1 = new InputStreamReader(fis1);
				BufferedReader br1 = new BufferedReader(isr1);
				try {
					while ((str = br1.readLine()) != null) {
						Scan scan = new Scan();
						// 通过列扫描
						System.out.println(str);
						scan.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes(str));
						scan.setFilter(new FirstKeyOnlyFilter());
						Long count = hbaseTemplate.find(tableName, scan, new ResultsExtractor<Long>() {
							public Long extractData(ResultScanner results) throws Exception {
								// 列的个数
								long rowCount = 0;
								FileOutputStream outFile = null;
								for (Result result : results) {
									rowCount += result.size();
									for (KeyValue kv : result.raw()) {
										System.out.println(Bytes.toString(result.getRow()));
										File file2 = new File("E:\\zzz\\user.txt");
										if (!file2.exists()) {
											file2.mkdirs();
										}
										outFile = new FileOutputStream(file2, true);
										outFile.write((Bytes.toString(result.getRow()) + "\r\n").getBytes());
										outFile.flush();
									}
								}
								if (outFile != null) {
									outFile.close();
								}
								System.out.println(rowCount);
								return rowCount;
							}
						});
					}
				} catch (Exception e) {
					// 写入异常
				} finally {
					if (br1 != null) {
						br1.close();
					}
					if (isr1 != null) {
						isr1.close();
					}

				}
			}
		}
		return null;
	}

	// 查询...
	public String getList(String tableName, String rowName) {
		return hbaseTemplate.get(tableName, rowName, new RowMapper<String>() {

			public String mapRow(Result result, int rowNum) throws Exception {
				List<Cell> ceList = result.listCells();
				String row = "";
				StringBuffer sb = new StringBuffer();
				FileOutputStream outFile = null;
				String row1 = "";
				Jedis ru = new Jedis("localhost");
				int i = 0;
				if (ceList != null && ceList.size() > 0) {
					for (Cell cell : ceList) {
						i++;
						row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());

						// 获取值
						String quali = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
								cell.getQualifierLength());
						// 获取列
						if (!"tel".equals(quali)) {
							long value = Bytes.toLong(cell.getValueArray(), cell.getValueOffset(),
									cell.getValueLength());

							// 获取列簇
							String family = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(),
									cell.getFamilyLength());
							String data = "";
							if (quali.length() > 8) {
								data = quali.substring(0, 8);
							} else {
								data = quali;
							}
							data = ru.get(data);
							if (data == null) {
								data = quali;
							}
							if (i == 1) {
								sb.append(row + "," + data + "," + value);
							} else {
								sb.append("," + data + "," + value);
							}
						}
					}
					System.out.println(sb);
					File file2 = new File("E:\\zzz\\ssss.txt");
					if (!file2.exists()) {
						file2.mkdirs();
					}
					outFile = new FileOutputStream(file2, true);
					if (sb.toString() != null && !"".equals(sb.toString())) {
						outFile.write((sb.toString() + "\r\n").getBytes("UTF-8"));
						outFile.flush();
					}
				}
				return row;
			}
		});
	}

	public String getListTest(String tableName, String str) {
		String zz[] = str.split(",");
		String rowName = zz[1];
		return hbaseTemplate.get(tableName, rowName, new RowMapper<String>() {
			public String mapRow(Result result, int rowNum) throws Exception {
				List<Cell> ceList = result.listCells();
				String row = "";
				StringBuffer sb = new StringBuffer();
				FileOutputStream outFile = null;
				String row1 = "";
				Jedis ru = new Jedis("localhost");
				int i = 0;
				if (ceList != null && ceList.size() > 0) {
					for (Cell cell : ceList) {
						i++;
						row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());

						// 获取值
						String quali = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
								cell.getQualifierLength());
						// 获取列
						if (!"tel".equals(quali)) {
							long value = Bytes.toLong(cell.getValueArray(), cell.getValueOffset(),
									cell.getValueLength());

							// 获取列簇
							String family = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(),
									cell.getFamilyLength());
						}
					}
					System.out.println(sb);
					File file2 = new File("E:\\zzz\\ssss.txt");
					outFile = new FileOutputStream(file2, true);
					outFile.write((sb.toString() + "\r\n").getBytes("UTF-8"));
					outFile.flush();
				}
				return row;
			}
		});
	}

	public void qurryTableTestBatch(List<String> rowkeyList) throws IOException {
		List<Get> getList = new ArrayList();
		Connection connection = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration());
		Table table = connection.getTable(TableName.valueOf("adpf_user_label"));
		for (String rowkey : rowkeyList) {// 把rowkey加到get里，再把get装到list中
			Get get = new Get(Bytes.toBytes(rowkey));
			getList.add(get);
		}
		String row = "";
		String quali = "";
		FileOutputStream outFile = null;
		String row1 = "";
		List<String> list1 = new ArrayList();
		Jedis ru = new Jedis("localhost");
		String data = "";
		long value = 0;

		Result[] results = table.get(getList);// 重点在这，直接查getList<Get>
		for (Result result : results) {// 对返回的结果集进行操作
			StringBuffer sb = new StringBuffer();
			boolean zzz = true;
			for (Cell cell : result.rawCells()) {
				row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());

				quali = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
				if (!"tel".equals(quali)) {
					value = Bytes.toLong(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

					// 获取列簇
					String family = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(),
							cell.getFamilyLength());

					if (quali.length() > 8) {
						data = quali.substring(0, 8);
					} else {
						data = quali;
					}
					data = ru.get(data);
					if (data == null) {
						data = quali;
					}
					if (zzz == true) {
						sb.append(row + "," + data + "," + value);
						zzz = false;
					} else {
						sb.append("," + data + "," + value);
					}
				}
			}
			if (!"".equals(sb.toString())) {
				System.out.println(sb);
				File file2 = new File("E:\\zzz\\ssss.txt");
				if (!file2.exists()) {
					file2.mkdirs();
				}
				outFile = new FileOutputStream(file2, true);
				if (sb.toString() != null && !"".equals(sb.toString())) {
					outFile.write((sb.toString() + "\r\n").getBytes("UTF-8"));
					outFile.flush();
				}
			}
		}
	}

	public void qurryTableTestBatchtest(List<String> rowkeyList, Map map) throws IOException {
		List<Get> getList = new ArrayList();
		if (zz == true) {
			connection = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration());
			table = connection.getTable(TableName.valueOf("testimei3"));
			zz = false;
		}
		FileOutputStream outFile = null;
		for (String rowkey : rowkeyList) {// 把rowkey加到get里，再把get装到list中
			Get get = new Get(Bytes.toBytes(rowkey));
			getList.add(get);
		}
		String row = "";
		String quali = "";
		String value = "";
		String family = "";
		String md51 = "";
		String zzz = "";
		Result[] results = table.get(getList);// 重点在这，直接查getList<Get>
		for (Result result : results) {// 对返回的结果集进行操作
			for (Cell cell : result.rawCells()) {
				row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());

				quali = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
				value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

				// 获取列簇
				family = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
				System.out.println(row + "," + value);
				zzz = (String) map.get(row);

				MD5Util md5 = new MD5Util();
				md51 = md5.MD5(value);

			}
			File file2 = new File("E:\\zzz\\111.txt");
			if (!file2.exists()) {
				file2.mkdirs();
			}
			if (!"".equals(row)) {
				outFile = new FileOutputStream(file2, true);
				outFile.write((zzz + "," + value + "," + md51 + "\r\n").getBytes("UTF-8"));
				outFile.flush();
			}
		}

	}

	public void qurryTableTestBatchtest2(List<String> getList1) throws IOException {
		List<Get> getList = new ArrayList<Get>();
		connection = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration());
		table = connection.getTable(TableName.valueOf("adpf_tel"));
		FileOutputStream outFile = null;
		System.out.println("把rowkey加到get里，再把get装到list中........");
		for (String rowkey : getList1) {// 把rowkey加到get里，再把get装到list中
			Get get = new Get(Bytes.toBytes(rowkey));
			getList.add(get);
		}

		System.out.println("查询中................");
		String row = "";
		String value = "";
		// 判断是否为空
//      table.exists(get);
		Result[] results = table.get(getList);
		for (Result result : results) {// 对返回的结果集进行操作
			for (Cell cell : result.rawCells()) {
				row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
				value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
				System.out.println(row);
				System.out.println(value);
			}
			outFile = new FileOutputStream(new File("F:\\ss\\s\\20181123.txt"), true);
			if (row != null && !"".equals(row)) {
				outFile.write((row + "," + value + "\r\n").getBytes("UTF-8"));
				outFile.flush();
			}
		}
	}

	public void qurryTableTel(String tel) throws IOException {
		if (zz == true) {
			connection = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration());
			table = connection.getTable(TableName.valueOf("adpf_tel"));
			zz = false;
		}
		String str[] = tel.split("\\|");
		String value1[] = tel.split("\\*\\|");
		FileOutputStream outFile = null;
		Get get = new Get(Bytes.toBytes(str[1]));
		Result result = table.get(get);
		List<Cell> ceList = result.listCells();
		if (ceList != null && ceList.size() > 0) {
			for (Cell cell : ceList) {
				String row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());

				String quali = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
						cell.getQualifierLength());
				String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

				// 获取列簇
				String family = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());

				File file2 = new File("F:\\ss\\s\\tel1.txt");
				outFile = new FileOutputStream(file2, true);
				if (row != null && !"".equals(row)) {
					outFile.write((value + value1[1] + "\r\n").getBytes("UTF-8"));
					outFile.flush();
				}
			}
		}
	}

	// tel+tel转md5
	public void qurryTableTel2(String tel, String hbaseTable, String pathFile) throws IOException {
		if (zz == true) {
			connection = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration());
			table = connection.getTable(TableName.valueOf(hbaseTable));
			zz = false;
		}
		String[] telData = tel.split(",", -1);
		Get get = new Get(Bytes.toBytes(telData[1]));
		Result result = table.get(get);
		List<Cell> ceList = result.listCells();
		File file2 = new File(pathFile);
		FileOutputStream outFile = new FileOutputStream(file2, true);
		if (ceList != null && ceList.size() > 0) {
			System.out.println("============================starts");
			for (Cell cell : ceList) {
				String row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());

				System.out.println(row);

				String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

				if (row != null && !"".equals(row)) {
					outFile.write((value + "," + tel + "\r\n").getBytes("UTF-8"));

				}
			}
			System.out.println("============================end");
		}
		outFile.flush();
		outFile.close();
	}

	public String getList1(String tableName, String rowName) {
		return hbaseTemplate.get(tableName, rowName, new RowMapper<String>() {
			public String mapRow(Result result, int rowNum) throws Exception {
				List<Cell> ceList = result.listCells();

				StringBuffer sb = new StringBuffer();
				FileOutputStream outFile = null;

				if (ceList != null && ceList.size() > 0) {
					for (Cell cell : ceList) {

						String row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());

						// 获取值
						String quali = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
								cell.getQualifierLength());
						// 获取列
						String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
								cell.getValueLength());

						// 获取列簇
						String family = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(),
								cell.getFamilyLength());

						System.out.println(row + " " + value);
						File file2 = new File("F:\\844\\1\\844_tel.txt");

						outFile = new FileOutputStream(file2, true);
						if (row != null && !"".equals(row)) {
							outFile.write((row + "," + value + "\r\n").getBytes("UTF-8"));
							outFile.flush();
						}
					}

				}
				return null;
			}
		});
	}

	public Long countTest(String tableName) {
		// 统计表中行数
		final Map<String, Object> params = new HashMap<>();
		Scan scan = new Scan();
		// 通过列扫描
		// Filter查询数据为空的话，可能会降低性能
		// scan.setFilter(new FirstKeyOnlyFilter());
		Long count = hbaseTemplate.find(tableName, scan, new ResultsExtractor<Long>() {
			public Long extractData(ResultScanner results) throws Exception {
				// 列的个数
				long rowCount = 0;

				for (Result result : results) {
					rowCount += result.size();
					for (KeyValue kv : result.raw()) {
						System.out.println(Bytes.toString(result.getRow()));
					}

				}
				System.out.println(rowCount);
				return rowCount;
			}
		});
		return count;
	}

	// 获取表中指定行，列簇，列的值
	public String get(String tableName, String rowName) {
		return hbaseTemplate.get(tableName, rowName, new RowMapper<String>() {

			@Override
			public String mapRow(Result result, int rowNum) throws Exception {
				List<Cell> ceList = result.listCells();
				String res = "";
				if (ceList != null && ceList.size() > 0) {
					for (Cell cell : ceList) {
						res = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
					}
				}
				System.out.println(ceList);
				return res;
			}
		});
	}

	// 读取一行
	public String getTableRowKey(String tableName) throws IOException {
		Connection connection = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration());
		Table table = connection.getTable(TableName.valueOf(tableName));
		Scan scan = new Scan();
		ResultScanner scanner = table.getScanner(scan);
		Result firstRow = scanner.next();
		scanner.close();
		table.close();
		if (firstRow == null)
			return "-1";

		return new String(firstRow.getRow());
	}

}
