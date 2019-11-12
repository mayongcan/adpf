package com.adpf.modules.gdtm.service.dpiinf;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.adpf.modules.gdtm.util.DemoBase;
import com.adpf.modules.gdtm.util.KvUtil;
import com.adpf.modules.gdtm.util.MD5Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;

public class NoHbaseDpiGetData {

	private static final Logger logger = Logger.getLogger(NoHbaseDpiGetData.class);
	private static final String FLAG = "_";

	public static Properties getProperties(String url, Properties urlsProp) {
//		String relativelyPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		urlsProp = getPropertiesSelf(url, urlsProp);
		return urlsProp;
	}

	/**
	 * 主函数,获取自定义配置路径
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static Properties getPropertiesSelf(String url, Properties urlsProp) {
		try {
			InputStream in2 = new BufferedInputStream(new FileInputStream(url));
			urlsProp = new Properties();
			urlsProp.load(in2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return urlsProp;
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
		// 获取配置参数
		Properties urlsProp = null;
//		urlsProp = getProperties("/usr/local/program/platform/project/adpf-1.0.2/config/httpget.properties", urlsProp);
		urlsProp = getProperties("/Users/zhijianzhang/git/adpf/src/main/resources/httpget.properties", urlsProp);

		String date = urlsProp.getProperty("date");
		String total = urlsProp.getProperty("total");
		String performTime = urlsProp.getProperty("performTime");
		String filePath = urlsProp.getProperty("filePath");
		String basePath = urlsProp.getProperty("basePath");
		String num = urlsProp.getProperty("num");
		String bs = urlsProp.getProperty("bs");
		int begin = Integer.valueOf(urlsProp.getProperty("begin"));

		String tableName = urlsProp.getProperty("tableName");
		String rowNum = urlsProp.getProperty("rowNum");
		String isNew = urlsProp.getProperty("isNew");
		String province = urlsProp.getProperty("province");

		int t = Integer.valueOf(total) / Integer.valueOf(num);
		createDirectory(filePath + date);
		createDirectory(basePath + date);

		for (int i = 0; i < Integer.valueOf(num); i++) {
			int beginStr = begin + t * i;
			int endStr = begin + t * i + t;
			if (endStr > Integer.valueOf(total)) {
				endStr = Integer.valueOf(total);
			}
			System.out.println(Thread.currentThread().getName() + " " + i + " " + beginStr + " " + endStr);
			Thread myThread1 = new MyThreadNoHbase(i, beginStr, endStr, filePath, date, performTime, bs, tableName,
					rowNum, isNew, province, basePath);
			myThread1.start();
		}

	}

	/**
	 * 创建目录
	 * 
	 * @param descDirName 目录名,包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	private static boolean createDirectory(String descDirName) {
		String descDirNames = descDirName;
		if (!descDirNames.endsWith(File.separator)) {
			descDirNames = descDirNames + File.separator;
		}
		File descDir = new File(descDirNames);
		if (descDir.exists()) {
			System.out.println("目录 " + descDirNames + " 已存在!");
			return false;
		}
		// 创建目录
		if (descDir.mkdirs()) {
			System.out.println("目录 " + descDirNames + " 创建成功!");
			return true;
		} else {
			System.out.println("目录 " + descDirNames + " 创建失败!");
			return false;
		}

	}

}

class MyThreadNoHbase extends Thread {

	private int inum = 0;
	private static final String FLAG = "_";

	private int beginStr;
	private int endStr;
	private String filePath;
	private String date;
	private String performTime;
	private String bs;
	private String tableName;
	private String rowNum;
	private String isNew;
	private String province;
	private String basePath;

	public MyThreadNoHbase(int inum, int beginStr, int endStr, String filePath, String date, String performTime,
			String bs, String tableName, String rowNum, String isNew, String province, String basePath) {
		super();
		this.inum = inum;
		this.beginStr = beginStr;
		this.endStr = endStr;
		this.filePath = filePath;
		this.date = date;
		this.performTime = performTime;
		this.bs = bs;
		this.tableName = tableName;
		this.rowNum = rowNum;
		this.isNew = isNew;
		this.province = province;
		this.basePath = basePath;
	}

	@Override
	public void run() {
		Business busi = new Business();
		busi.setCompanyApikey("CD1BC28CAF762A86D75BBC072D8B4F08");
		busi.setCompanySecretkey("3DE2C39E5B06F33336A5DC21E1A3370F");
		busi.setKvmdnurl("42.123.106.17:18080");
		// 必填，由gdyt（前缀），区域：guangdong，业务类型：yx（游戏），时间标识：20171031：序号：1组成
		busi.setCompanyName("广东翼天");
		busi.setCompanyCode("tmbd");
		busi.setCompanyLabel(bs);

		File f = new File(filePath + date + "/rtb" + date + "_" + performTime + "_" + inum + ".txt");
		File basePathFile = new File(basePath + date + "/rtb" + date + "_" + performTime + "_" + inum + ".txt");

		if (!basePathFile.exists()) {
			try {
				basePathFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 不存在则创建
		}
		if (f.exists()) {
			System.out.println("文件存在");
			try {
				String lastLine = readLastLine(f, "utf-8");
				System.out.println(lastLine);
				String[] words = StringUtils.split(lastLine, ",");
				String[] tmp2 = StringUtils.split(words[0], "_");
				String key = tmp2[4].replaceAll("\"", "");
				System.out.println(key);
				beginStr = Integer.valueOf(key);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// return;
		} else {
			System.out.println("文件不存在");
			try {

				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 不存在则创建
		}
		BufferedWriter output = null;
		BufferedWriter baseOutput = null;
		try {

			output = new BufferedWriter(new FileWriter(f, true));
			baseOutput = new BufferedWriter(new FileWriter(basePathFile, true));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		} // true,则追加写入text文本
		Date d2 = new Date();
		for (int i = beginStr; i < endStr; i++) {
			try {
				Date d1 = new Date();

				long interval = (d1.getTime() - d2.getTime()) / 1000;
				if (interval >= 2) {
					d2 = new Date();
					int left = endStr - i;
					System.out.println("当前线程：" + inum + " #总数据：" + endStr + " #已经读取:" + i + " #剩下数据：" + left);
				}
				String key = setKey(busi, i, date, "000");

				String itemJSONObj = KvUtil.getMdnToKv(busi,
						// "http://42.123.72.93:8081/bdapi/restful/fog/gdyttx/getItems/ctyun_bdcsc_gdyttx/",
						"http://111.235.158.136:8999/bdapi/restful/fog/vendorgdtmsj_hljxtjc/getItems/3591543456646283/",
						"3591543456646283", "F0B29B032376D13A9A2B40E222FA8DF7", key, "2");

				JSONObject kvRet = JSONObject.parseObject((String) itemJSONObj);
				// logger.info("获取授编码:" + mapresutl1.toString());
				Integer code = (Integer) kvRet.get("code");
				if (code == 200) {
					JSONObject dataObj = (JSONObject) kvRet.get("data");
					String value = (String) dataObj.get("value");
					String content = key + "," + value + ",";
					output.write(content);
					output.write("\r\n");// 换行
					output.flush();
				} else {
					System.out.println("接口异常,错误码:" + key + "  " + kvRet.toString());
					// throw new RuntimeException("接口调用失败,错误码" + jsonObj.getInt("code"));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			if (baseOutput != null) {
				baseOutput.close();
			}
			if (output != null) {
				output.close();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 根据厂商信息处理key
	 * 
	 * @param business 厂商信息
	 * @param mdnCode  编号
	 * @return
	 */
	// Key格式为：厂商编码_省份缩写编码_标签类型编码_日期_编码
	public static String setKey(Business business, int mdnCode, String DATE, String city) {
		StringBuffer key = new StringBuffer();
		key.append(business.getCompanyCode()).append(FLAG)
				// 省份缩写编码
				.append(city).append(FLAG)
				// 标签编号
				.append(business.getCompanyLabel()).append(FLAG)
				// 日期
				.append(DATE).append(FLAG)
				// 顺序
				.append(mdnCode);
		return key.toString();
	}

	public static String readLastLine(File file, String charset) throws IOException {
		if (!file.exists() || file.isDirectory() || !file.canRead()) {
			return null;
		}
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "r");
			long len = raf.length();
			if (len == 0L) {
				return "";
			} else {
				long pos = len - 1;
				while (pos > 0) {
					pos--;
					raf.seek(pos);
					if (raf.readByte() == '\n') {
						break;
					}
				}
				if (pos == 0) {
					raf.seek(0);
				}
				byte[] bytes = new byte[(int) (len - pos)];
				raf.read(bytes);
				if (charset == null) {
					return new String(bytes);
				} else {
					return new String(bytes, charset);
				}
			}
		} catch (FileNotFoundException e) {
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (Exception e2) {
				}
			}
		}
		return null;
	}

	// 根据row key获取表中的该行数据
	public JSONObject getOne(Table table, String rowKey) throws IOException {
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
//				System.out.println("columnFamily:{}" + Bytes.toString(entry.getKey()));
				json.put("rowKey", rowKey);
				json.put("columnFamily", Bytes.toString(entry.getKey()));
				NavigableMap<byte[], NavigableMap<Long, byte[]>> map = entry.getValue();
				for (Map.Entry<byte[], NavigableMap<Long, byte[]>> en : map.entrySet()) {
					// System.out.print(Bytes.toString(en.getKey()) + "##");
					json.put("Column", Bytes.toString(en.getKey()));
					NavigableMap<Long, byte[]> nm = en.getValue();
					for (Map.Entry<Long, byte[]> me : nm.entrySet()) {
//						System.out.println("column key:{}, value:{}" + me.getKey() + " " + new String(me.getValue()));
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
