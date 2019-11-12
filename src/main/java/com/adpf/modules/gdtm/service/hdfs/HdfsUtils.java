package com.adpf.modules.gdtm.service.hdfs;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;



public class HdfsUtils {
	public static void main(String[] args) throws IOException {

		String path = "hdfs://192.168.40.104:8020/user/hive_q";
 
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(path), conf);
		FileStatus[] status = fs.listStatus(new Path(path));
		for(FileStatus file : status) {
			if (!file.getPath().getName().startsWith(".")) {
				System.out.println(file.getPath().getName());
				if(file.isDir()) {
					System.out.println(file.getPath().getName());
					String path1 = "hdfs://192.168.40.104:8020/user/hive_q/"+file.getPath().getName();
		    		FileStatus[] status1 = fs.listStatus(new Path(path1));
		    		int ss = status1.length;
		    		for(FileStatus file1 : status1) {
		    			if(status1.length==0) {
		    				System.out.println("1");
		    			}
		    			System.out.println(file1.getPath().getName()); 
		    		}
				}
			}
		}
	}
}
