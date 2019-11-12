package com.adpf.modules.gdtm.service.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.adpf.modules.gdtm.service.hbase.impl.HBaseCrudServiceImpl;

public class Sz {
	
	public static void main(String[] args) throws IOException {
		FileInputStream fis1;
		String file = "E:\\duozhao1.txt";
		String str = "";
		fis1 = new FileInputStream(file);
		InputStreamReader isr1 = new InputStreamReader(fis1);
		BufferedReader br1 = new BufferedReader(isr1);
		HBaseCrudServiceImpl h = new HBaseCrudServiceImpl();
		List<String> list = new ArrayList();
		int i = 0;
		while ((str = br1.readLine()) != null) {
			try {
				i++;
				list.add(str.split(",")[2]);
				if(i%1000==0) {
					System.out.println(i);
					h.qurryTableTestBatchtest2(list);				
					list.clear();
				}
			}catch (Exception e) {
				continue;
			}
		}	h.qurryTableTestBatchtest2(list);	
	}
}
