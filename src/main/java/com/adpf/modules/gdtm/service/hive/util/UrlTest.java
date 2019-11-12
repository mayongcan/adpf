package com.adpf.modules.gdtm.service.hive.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.adpf.modules.gdtm.service.hive.HiveJDBC;

public class UrlTest {
	
	public static void main(String[] args) {
		FileInputStream fis1;
		String file = "E:\\zzz\\rtb20180823_2-ws-811_0.txt";
		String str = "";
		try {
			fis1 = new FileInputStream(file);
			InputStreamReader isr1 = new InputStreamReader(fis1);
			BufferedReader br1 = new BufferedReader(isr1);
			StringBuffer sb = new StringBuffer();
			while ((str = br1.readLine()) != null) {
				String strTest[] = str.split(",");
				sb.append("'"+strTest[1]+"'"+",");
			}
			String str11 = sb.substring(0, sb.length()-1);
			System.out.println(str11);
	    	HiveJDBC hj = new HiveJDBC();
			hj.test11(str11);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
