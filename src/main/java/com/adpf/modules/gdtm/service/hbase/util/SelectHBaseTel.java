package com.adpf.modules.gdtm.service.hbase.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.adpf.modules.gdtm.service.hbase.impl.HBaseCrudServiceImpl;

public class SelectHBaseTel {
	public static void main(String[] args) throws Exception {
		FileInputStream fis1;
		String file = "F:\\ss\\s\\kaimenkong1.log";
		String str = "";
		fis1 = new FileInputStream(file);
		InputStreamReader isr1 = new InputStreamReader(fis1);
		BufferedReader br = new BufferedReader(isr1);
		List<String> list = new ArrayList<String>();
		HBaseCrudServiceImpl h = new HBaseCrudServiceImpl();
		int i = 0;
		while ((str = br.readLine()) != null) {
//			h.qurryTableTest3(str);
		}
	}
}
