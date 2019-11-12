package com.adpf.modules.gdtm.service.hbase.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.adpf.modules.gdtm.service.hbase.impl.HBaseCrudServiceImpl;

public class SelectHBaseTel2 {

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

	public static void main(String[] args) throws Exception {
		Properties urlsProp = null;
		urlsProp = getProperties("/usr/local/program/platform/project/adpf-1.0.2/config/httpget.properties", urlsProp);

		String transfileName = urlsProp.getProperty("transfileName");

		FileInputStream fis1;
		// 输入文件
		String file = "/home/openftp/zhangzj/" + transfileName;
		System.out.println(file);
		String str = "";
		fis1 = new FileInputStream(file);
		InputStreamReader isr1 = new InputStreamReader(fis1);
		BufferedReader br = new BufferedReader(isr1);
		HBaseCrudServiceImpl h = new HBaseCrudServiceImpl();
		// HBase表名
		String hbaseTable = "adpf_tel2";
		// 输出文件
		String pathFile = "/home/openftp/zhangzj/outTg-" + transfileName;
//		h.qurryTableTel2(str,hbaseTable,pathFile);
		while ((str = br.readLine()) != null) {
			try {
				System.out.println(str);
				h.qurryTableTel2(str, hbaseTable, pathFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
