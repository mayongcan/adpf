package com.adpf.modules.gdtm.service.hive.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import com.adpf.modules.gdtm.service.hive.HiveJDBC;

public class CountLabel {
	
	public static void main(String[] args) {
		FileInputStream fis1;
		String file = "E:\\游戏ID8-28.txt";
		String str = "";
		try {
			fis1 = new FileInputStream(file);
			InputStreamReader isr1 = new InputStreamReader(fis1);
			BufferedReader br1 = new BufferedReader(isr1);
			StringBuffer sb = new StringBuffer();
			int i = 0;
			while ((str = br1.readLine()) != null) {
				i++;
		
					if(str!=null && !"".equals(str))
						sb.append("'"+str+"'"+",");
	
			}
			String str11 = sb.substring(0, sb.length()-1);
			System.out.println(str11);
	    	HiveJDBC hj = new HiveJDBC();
			hj.selectData(str11); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
