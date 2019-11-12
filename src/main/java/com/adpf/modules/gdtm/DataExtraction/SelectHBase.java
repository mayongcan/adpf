package com.adpf.modules.gdtm.DataExtraction;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.adpf.modules.gdtm.service.hbase.impl.HBaseCrudServiceImpl;

public class SelectHBase {
	
	public static void main(String[] args) throws Exception {
		FileInputStream fis1;
		String file = "E:\\djwl.txt";
		String str = "";
		fis1 = new FileInputStream(file);
		InputStreamReader isr1 = new InputStreamReader(fis1);
		BufferedReader br1 = new BufferedReader(isr1);
		HBaseCrudServiceImpl h = new HBaseCrudServiceImpl();
		List<String> list = new ArrayList();
		FileOutputStream outFile = null;
		int i = 0;
		while ((str = br1.readLine()) != null) {
			String str1[] = str.split(",");
			i++;
			list.add(str1[1]);
			if(i%500==0) {
				System.out.println(i);
				h.qurryTableTestBatchtest2(list);	
				list.clear();
			}
		}
		h.qurryTableTestBatchtest2(list);
	}

}
