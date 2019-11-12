package com.adpf.modules.gdtm.service.hbase.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

import com.adpf.modules.gdtm.service.hbase.impl.HBaseCrudServiceImpl;
import com.adpf.modules.gdtm.service.hbase.test.HBaseConnectionUtils;

@Service
public class BasicLabel {
	
	public static void main(String[] args) throws IOException {
		BasicLabel b = new BasicLabel();
		b.LabelPut("E:\\test","E:\\test11\\err","adpf_test1","cf1");
	}

	//基本标签
	public void LabelPut(String filePath,String errfilePath,String hbaseTableName,String familyName) throws IOException {
		HBaseCrudServiceImpl h = new HBaseCrudServiceImpl();
		Connection connection = null;
		connection = HBaseConnectionUtils.getConnection();
		Table table = connection.getTable(TableName.valueOf(hbaseTableName));
		//设置数据缓存区域
//	    table.setWriteBufferSize(64*1024*1024); 

		//文件夹目录
		//String filePath = "E:\\test";
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
				//权重
				Integer weight = 1;
				List<Put> putList = new ArrayList<>();
				int size = 0;
				try {
					while ((str = br1.readLine()) != null) {
						size++;
						//每一行通过','切割每一段
						String[] nums = str.split(",");
						//每一行的第一块
						String date = nums[0];
						String[] date1 = date.split("_");
						//每一行的日期
						String date2 = date1[3];
				//		System.out.println(date2);
						//通过rowKey获取标签
//						String label = h.get(hbaseTableName, nums[1],familyName,"label");			    
//						//判断标签是否相同
//						if(label.equals(nums[3])) {
//							Integer weight1 = Integer.valueOf(h.get(hbaseTableName, nums[1],familyName,"weight"));
//							weight = weight1+1;
//							System.out.println("条件符合，权重加1");
//						}					
//						Put put = new Put(Bytes.toBytes(nums[1]));
//						//不写WAL日志
//						//put.setDurability(Durability.SKIP_WAL); 
//						put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes("province"), Bytes.toBytes(nums[2]));
//						put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes("label"), Bytes.toBytes(nums[3]));
//						put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes("devices"), Bytes.toBytes(nums[5]));
//						put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes("weight"), Bytes.toBytes(weight.toString()));
//						putList.add(put);
//						//每size条提交一次table.put
//						if(size%500==0) {
//						    table.put(putList);	
//						    System.out.println(size);
//						    putList.clear();
//						}	
//						throw new Exception();
					}
					table.put(putList);
				} catch (Exception e) {
					//写入异常
					
	    			File file2  = new File(errfilePath+"\\"+readfile.getName()+"-err.txt");
	    			if (!file2.exists() && !folder.isDirectory()) {    				
	    				file2.mkdirs();
	    		    }
	    			FileOutputStream outFile = new FileOutputStream(file2, true);
	    			outFile.write((str+"\r\n").getBytes());
	    			outFile.flush();
	    			outFile.close();		
				}finally {
					if (table != null) {
						table.close();
					}
					if(br1 != null) {
						br1.close();
					}if(isr1 != null) {
						isr1.close();
					}
				}
			}
		}
	}
}
