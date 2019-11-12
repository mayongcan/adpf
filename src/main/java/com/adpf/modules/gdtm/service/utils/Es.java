package com.adpf.modules.gdtm.service.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Es {
	
	public static void main(String[] args) {
		FileInputStream fis1;
		String file = "F:\\rtb20181019_1-hjb_0.txt";
		String str = "";
		try {
			fis1 = new FileInputStream(file);
			InputStreamReader isr1 = new InputStreamReader(fis1);
			BufferedReader br1 = new BufferedReader(isr1);
			FileOutputStream outFile = null;
			while ((str = br1.readLine()) != null) {
				String value[] = str.split(",");
//				System.out.println(value[12]);
				if(Integer.parseInt(value[12])>=5) {
//					System.out.println();
					if(!"".equals(value[7])&&!"17".equals(value[7].substring(0, 2))) {
						System.out.println(value[7]+","+value[12]);
						File file2  = new File("F:\\ss\\19.txt");
						outFile = new FileOutputStream(file2, true);
			    		outFile.write((value[7]+","+value[12]+"\r\n").getBytes("UTF-8"));
			    		outFile.flush();
					}
				}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
