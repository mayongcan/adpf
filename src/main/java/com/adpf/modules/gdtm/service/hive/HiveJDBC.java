package com.adpf.modules.gdtm.service.hive;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.adpf.modules.gdtm.service.hbase.impl.HBaseCrudServiceImpl;
import com.adpf.modules.gdtm.service.hbase.test.HBaseConnectionUtils;
import com.adpf.modules.gdtm.service.hive.util.HiveJDBCConnectionUtils;
import com.gimplatform.core.utils.RedisUtils;

import redis.clients.jedis.Jedis;

public class HiveJDBC {

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    
    public static void main(String[] args) throws Exception {
    	HiveJDBCConnectionUtils h = new HiveJDBCConnectionUtils();
    	HiveJDBC hj = new HiveJDBC();
    	stmt = h.Connection();
    	hj.showTables();
    	hj.destory();
	}

    // 创建数据库
    public void createDatabase() throws Exception {
        String sql = "create database hive_jdbc_test";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }
 
    public void test() throws Exception {
    	HiveJDBCConnectionUtils h = new HiveJDBCConnectionUtils();
    	stmt = h.Connection();
    	String dec = "ALTER TABLE adpf_test1 DROP IF EXISTS PARTITION (name='LI',age='18')";
        String sql1 = "insert into adpf_md5_test values()";
        String sql = " insert into table adpf_imei select imei_md5,province,idfa from adpf_imei_test11";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }


    // 查询所有数据库
    public void showDatabases() throws Exception {
        String sql = "show databases";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

    // 创建表
    public void createTable() throws Exception {
             String sql="create EXTERNAL TABLE adpf_inkepay(\n" +
                        "id string,\n" +
                        "devicecode int,\n" +
                        "province string,\n" +
                        "devices string,\n" +
                        "weight int)\n" +
                        "STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'\n" +
                        "WITH SERDEPROPERTIES (\"hbase.columns.mapping\" = \":key,cf1:province,cf1:label,cf1:devices,cf1:weight\")\n" +
                        "TBLPROPERTIES(\"hbase.table.name\" = \"adpf_basic_label\")";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    // 查询所有表
    public void showTables() throws Exception {
        String sql = "show tables";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

    // 查看表结构
    public void descTable() throws Exception {
        String sql = "desc formatted adpf_label";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t" + rs.getString(2));
        }
    }

    // 加载数据
    public void loadData() throws Exception {
       String filePath = "/user/hive_q/rtb20180730_rq-20-1c.txt";
        String sql = "load data inpath '" + filePath + "' into table adpf_partition_test PARTITION (month='20180730',day='30')";
    	String sql1 ="alter table adpf_label add if not exists partition (month='201807',day='09')";
    	System.out.println("Running: " + sql1);
        stmt.execute(sql1);
    }
    

    // 查询数据
    public void selectData(String str) throws Exception {
    	HiveJDBCConnectionUtils h = new HiveJDBCConnectionUtils();
    	stmt = h.Connection();
    	String sql5 = "select a.imei_md5,label,b.imei from (select imei_md5,label from adpf_imei_label_test)a left join (select md5_imei,imei from adpf_imei)b on a.imei_md5=b.md5_imei";
    	String sql3 = "select label,count(label) from adpf_label_new where label in ("+str+") GROUP BY label";
    	String sql4 = "select a.label,a.md5_imei,collect_set(b.imei)[0],collect_set(mp_model)[0]  from (select label,imei_md5 from adpf_label.adpf_label_new) a left join (select * from adpf_label.adpf_imei)b on a.imei_md5=b.md5_imei where label in ("+str+") GROUP BY a.label,b.md5_imei";
        String sql1 = "select a.label,a.imei_md5,a.imeitranslate_md5_15,collect_set(a.mp_model)[0],collect_set(b.imei)[0],collect_set(c.imei)[0] from (select * from adpf_label.adpf_label_new) a join (select * from adpf_label.adpf_imei)b on a.imei_md5=b.md5_imei join (select * from  imei.testimei3)c on a.imei_md5=c.key where label in ("+str+") GROUP BY a.imei_md5,a.label,b.imei,imeitranslate_md5_15";
        String sql2 = "select phoneNum,label from adpf_label where length(phoneNum)=32";
        System.out.println("Running: " + sql4);
        rs = stmt.executeQuery(sql4);
		Connection connection = null;
		connection = HBaseConnectionUtils.getConnection();
		Table table = connection.getTable(TableName.valueOf("adpf_user_label"));
		HBaseCrudServiceImpl h1 = new HBaseCrudServiceImpl();
		List<Put> putList = new ArrayList<Put>();
        int i = 0;
        Jedis jedis = new Jedis("localhost");
        String value = "";
        String an = "";
        while (rs.next()) {
        	i++;
        	if(jedis.get(rs.getString(1))==null) {
        		value = rs.getString(1);
        	}else {
        		value = jedis.get(rs.getString(1));
        	}
        	an = rs.getString(4);
        	if(!"".equals(rs.getString(4)) && rs.getString(4)!=null) {
        		if(!"Android".equals(rs.getString(4))&&!"iPhone".equals(rs.getString(4))) {
        			an = "-";
        		}
        	}
//        	System.out.println(rs.getString(2)+","+","+rs.getString(3)+value+","+an+","+rs.getString(5)+","+rs.getString(6)+","+rs.getString(7));
//        	System.out.println(value+","+rs.getString(2));
        	System.out.println(rs.getString(1)+","+","+rs.getString(2)+","+","+rs.getString(3));
        	File file2  = new File("D://zzz/9999tttt.txt");
			FileOutputStream outFile = new FileOutputStream(file2, true);
			outFile.write((value+","+rs.getString(2)+","+rs.getString(3)+"\r\n").getBytes());
			outFile.flush();
        }
        System.out.println("完成");
    }
    
    //清空分区表
    public void decTest() throws Exception {
    	HiveJDBCConnectionUtils h = new HiveJDBCConnectionUtils();
    	stmt = h.Connection();
    	String dec = "ALTER TABLE adpf_label_transfer DROP IF EXISTS PARTITION (month='201808',day='35')";
		stmt.execute(dec);
		System.out.println(dec);
    }
    
    //adpf_imei
    public void Test1111() throws Exception {
    	String sql1 = "select idfa from adpf_label_new";
    	String insert = "select tel,imei_md5,urltranslate from adpf_label_new where month='2018' and day='21' and substr(`urltranslate`, 0, 2)='86' and length(urltranslate)='15' or substr(`urltranslate`, 0, 2)='35' and length(urltranslate)='15' and length(imei_md5)='32' GROUP BY urltranslate,imei_md5,tel";
    	String sql ="insert into table adpf_imei select tel,imei_md5,urltranslate from adpf_label_new where substr(`urltranslate`, 0, 2)='86' and length(urltranslate)='15' or substr(`urltranslate`, 0, 2)='35' and length(urltranslate)='15' and length(imei_md5)='32' GROUP BY urltranslate,imei_md5,tel";
    	System.out.println(sql1);
    	rs = stmt.executeQuery(sql1);
    	while(rs.next()) {
    		System.out.println(rs.getString(1));
    	}
    }
    
    public void test11(String str1) throws Exception{
    	HiveJDBCConnectionUtils h = new HiveJDBCConnectionUtils();
    	stmt = h.Connection();
    	String sql ="select * from adpf_imei where md5_imei in ("+str1+")";
    	 rs = stmt.executeQuery(sql);
    	 System.out.println(sql);
    	 String imei ="";
    	 while(rs.next()) {
    		 if(rs.getString(3)!=null && !"".equals(rs.getString(3))) {
         		if(rs.getString(3).length()>15) {
         			List<String> list = new ArrayList<String>(); 
         			String str[] = rs.getString(3).split("-");
         			for(int i = 0;i<str.length;i++) {
         				if(!list.contains(str[i])) {   
         		            list.add(str[i]);
         		        }   
         			}
     				StringBuffer sb = new StringBuffer();
         			for(int i = 0;i<list.size();i++) {
         				sb.append(list.get(i)+"-");
         			}
         			sb.deleteCharAt(sb.length()-1);
         			imei = sb.toString();
         		}else {
         			imei = rs.getString(3);
         		}
         	}else {
         		imei = rs.getString(3);
         	}
    		 System.out.println(rs.getString(2)+","+rs.getString(2)+","+imei);
    	 }
    }
    
    public void test111(String str1) throws Exception{
    	HiveJDBCConnectionUtils h = new HiveJDBCConnectionUtils();
    	stmt = h.Connection();
    	String sql ="select imei_md5,label,imei from adpf_28_test";
    	 rs = stmt.executeQuery(sql);
    	 System.out.println(sql);
    	 HBaseCrudServiceImpl h1 = new HBaseCrudServiceImpl();
    	 String imei ="";
    	 int i = 0;
    	 List<String> list = new ArrayList();
    	 FileOutputStream outFile = null;
    	 Map map = new HashMap<>();
    	 while(rs.next()) {
    		 i++;	 
    		 if("-".equals(rs.getString(3))) {
    			 list.add(rs.getString(1));
    			 map.put(rs.getString(1)+rs.getString(2), rs.getString(1)+""+rs.getString(2));
    			 if(i%50000==0) {
 					System.out.println(i);
 					h1.qurryTableTestBatchtest(list,map);
 					list.clear();
 					map.clear();
 				}
    		 }else if(rs.getString(3).length()>15){
    			String iemi[] = rs.getString(3).split("-");
      			for(int i1 = 0;i1<iemi.length;i++) {
      				if(!list.contains(iemi[i1])) {
      		            list.add(iemi[i1]);
      		        }   
      			}
      			StringBuffer sbz = new StringBuffer();
      			for(int i2 = 0;i2<list.size();i++) {
      				sbz.append(list.get(i2)+"-");
      			}
 				File file2  = new File("E:\\zzz\\ssss.txt");
 	        	if (!file2.exists()) {
 					file2.mkdirs();
 			    }
 				outFile = new FileOutputStream(file2, true);
 				outFile.write((rs.getString(1)+","+rs.getString(2)+","+sbz+"\r\n").getBytes("UTF-8"));
 				outFile.flush();
    		 }else{
    			 File file2  = new File("E:\\zzz\\ssss.txt");
  	        	if (!file2.exists()) {
  					file2.mkdirs();
  			    }
  				outFile = new FileOutputStream(file2, true);
  				outFile.write((rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+"\r\n").getBytes("UTF-8"));
  				outFile.flush();
    		 }
    	 }
    	 h1.qurryTableTestBatchtest(list,map);
    }
    
    public void test1111() throws Exception{
    	HiveJDBCConnectionUtils h = new HiveJDBCConnectionUtils();
    	stmt = h.Connection();
    	String sql ="select imei_md5,label from adpf_28_test";
    	 rs = stmt.executeQuery(sql);
    	 System.out.println(sql);
    	 HBaseCrudServiceImpl h1 = new HBaseCrudServiceImpl();
    	 String imei ="";
    	 int i = 0;
    	 List<String> list = new ArrayList();
    	 FileOutputStream outFile = null;
    	 Map map = new HashMap<>();
    	 while(rs.next()) {
    		 i++;	 
//    		 if("-".equals(rs.getString(3))) {
    		 System.err.println(rs.getString(1)+" "+rs.getString(2));
    			 list.add(rs.getString(1));
    			 map.put(rs.getString(1), rs.getString(1)+","+rs.getString(2));
    			 if(i%10000==0) {
 					System.out.println(i);
 					h1.qurryTableTestBatchtest(list,map);
 					list.clear();
 					map.clear();
// 				}
//    		  if(rs.getString(3).length()>15){
//    			String iemi[] = rs.getString(3).split("-");
//      			for(int i1 = 0;i1<iemi.length;i++) {
//      				if(!list.contains(iemi[i1])) {
//      		            list.add(iemi[i1]);
//      		        }   
//      			}
//      			StringBuffer sbz = new StringBuffer();
//      			for(int i2 = 0;i2<list.size();i++) {
//      				sbz.append(list.get(i2)+"-");
//      			}
// 				File file2  = new File("E:\\zzz\\ssss.txt");
// 	        	if (!file2.exists()) {
// 					file2.mkdirs();
// 			    }
// 				outFile = new FileOutputStream(file2, true);
// 				outFile.write((rs.getString(1)+","+rs.getString(2)+","+sbz+"\r\n").getBytes("UTF-8"));
// 				outFile.flush();
//    		 }else{
//    			 File file2  = new File("E:\\zzz\\ssss.txt");
//  	        	if (!file2.exists()) {
//  					file2.mkdirs();
//  			    }
//  				outFile = new FileOutputStream(file2, true);
//  				outFile.write((rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+"\r\n").getBytes("UTF-8"));
//  				outFile.flush();
//    		 }
    	 }
    	
    	 }
    	 h1.qurryTableTestBatchtest(list,map);
    }
    
    
    // 查询数据
    public void selectData1() throws Exception {
    	String sqlT = "show partitions adpf_label";
    	String sql = "select collect_set(date)[0],label,count(label) as num from adpf_test2 GROUP BY label";
        String sql1 = "SELECT * FROM adpf_imei";
        String sql5 = "SELECT collect_set(a.two)[0],collect_set(a.eight)[0],b.imei from (select * from adpf_inkepay) a left join (select * from adpf_imei)b on a.two = b.md5_imei GROUP BY b.imei";
        String sql2 = "select rowkey,label,count(label) as num from adpf_test2 GROUP BY all rowkey,label";
        String sql3 = "SELECT rowkey,label FROM adpf_test2 where substr(`date`, 14, 8)='20180730' or substr(`date`, 14, 8)='20180707'";
        System.out.println("Running: " + sql5);
        rs = stmt.executeQuery(sql5);
        FileOutputStream outFile = null;
        File file2  = new File("E:\\inkepay.txt");
        String imei ="";
        while (rs.next()) {
        	if(rs.getString(3)!=null && !"".equals(rs.getString(3))) {
        		if(rs.getString(3).length()>15) {
        			List<String> list = new ArrayList<String>(); 
        			String str[] = rs.getString(3).split("-");
        			for(int i = 0;i<str.length;i++) {
        				if(!list.contains(str[i])) {   
        		            list.add(str[i]);
        		        }   
        			}
    				StringBuffer sb = new StringBuffer();
        			for(int i = 0;i<list.size();i++) {
        				sb.append(list.get(i)+"-");
        			}
        			sb.deleteCharAt(sb.length()-1);
        			imei = sb.toString();
        		}else {
        			imei = rs.getString(3);
        		}
        	}
        	System.out.println(rs.getString(1)+","+rs.getString(2)+","+imei);
//        	outFile = new FileOutputStream(file2, true);
//			outFile.write((rs.getString(1)+","+rs.getString(2)+","+imei+"\r\n").getBytes());
//			outFile.flush();
        }
        outFile.close();
    }

    // 统计查询（会运行mapreduce作业）
    public void countData() throws Exception {
        String sql = " select province,label from adpf_label_transfer where month='201808' and day='26' and length(label)=8";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1)+" "+rs.getString(2));
        }
    }

    // 删除数据库
//    public void dropDatabase() throws Exception {
//        String sql = "drop database if exists hive_jdbc_test";
//        System.out.println("Running: " + sql);
//        stmt.execute(sql);
//    }

    // 删除数据库表
    public void deopTable() throws Exception {
        String sql = "drop table if exists adpf_label_test_111";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    // 释放资源
    public void destory() throws Exception {
        if ( rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
    
}
