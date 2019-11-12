package com.adpf.modules.gdtm.DataExtraction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.adpf.modules.gdtm.service.hbase.impl.HBaseCrudServiceImpl;
import com.adpf.modules.gdtm.service.hbase.test.HBaseConnectionUtils;
import com.adpf.modules.gdtm.service.hive.impl.HiveServiceImpl;
import com.adpf.modules.gdtm.service.hive.util.HiveJDBCConnectionUtils;

public class SelectHive {
	
	public static void main(String[] args) throws Exception {
		SelectHive s = new SelectHive();
		s.MatchedData();
	}
	
	private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    private static boolean zz = true;
    Table table = null;
    protected static final Logger logger = LogManager.getLogger(HiveServiceImpl.class);
    

	public void MatchedData() throws Exception{
					 
		//创建表
		HiveJDBCConnectionUtils h = new HiveJDBCConnectionUtils();
	    try {
			stmt = h.Connection();
//			String sql ="create table adpf_data_temp(\n" +
//	        		"a string,\n" +
//	        		"b string,\n" +
//	        		"c string,\n" +
//	        		"d string,\n" +
//	        		"e string,\n" +
//	        		"f string\n" +
//	                ")\n" +
//	                "row format delimited fields terminated by ','";
//		    System.out.println("Running: " + sql);
//			stmt.execute(sql);
//			System.out.println("adpf_data_temp表创建完成..................");
//		
//			//加载文件数据到表
//			String sql1 = "load data inpath '/user/hive_q/rtb20180925_1-wjb-jingrong_0.txt' overwrite into table adpf_data_temp";
//			System.out.println("Running: " + sql1);
//			stmt.execute(sql1);
//			System.out.println("adpf_data_temp表数据加载完成.................");
		
			//查询数据
			String sql2 = "SELECT b1.imei_md5,b1.tel from adpf_data_temp as a1,adpf_label_new as b1 WHERE a1.b = b1.imei_md5 GROUP BY b1.imei_md5,b1.tel";
			System.out.println("mr作业中.....................");
			rs = stmt.executeQuery(sql2);
			SelectHive hbase = new SelectHive();
			List<String> list = new ArrayList();
			Map<String,String> map = new HashMap<>();
			int i = 0;
			while (rs.next()) {
		       	i++;
		       	System.out.println(rs.getString(1)+rs.getString(2));   	
		       	list.add(rs.getString(2));
		       	map.put(rs.getString(2), rs.getString(1));
				if(i%500==0) {
					System.out.println(i);
					hbase.qurryTableTestBatchtest2(list,map);
					list.clear();
				}
	        }
			hbase.qurryTableTestBatchtest2(list,map);
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
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
	
	public void qurryTableTestBatchtest2(List<String> rowkeyList,Map<String,String> map) throws IOException {
        List<Get> getList = new ArrayList();
        org.apache.hadoop.hbase.client.Connection HBaseconnection = null;
        Table table = null;
       
        HBaseconnection = HBaseConnectionUtils.getConnection();
    	table = HBaseconnection.getTable(TableName.valueOf("adpf_tel"));

		FileOutputStream outFile = null;
		System.out.println("把rowkey加到get里，再把get装到list中........");
        for (String rowkey : rowkeyList){
        	//把rowkey加到get里，再把get装到list中
        	if(rowkey!=null) {
        		 Get get = new Get(Bytes.toBytes(rowkey));
                 getList.add(get);
        	}
        }
        System.out.println("把rowkey加到get里，再把get装到list中完成...................");
        String row = "";
        String quali = "";
        String value = "";
        String family = "";
        Result[] results = table.get(getList);//重点在这，直接查getList<Get>
        for (Result result : results){//对返回的结果集进行操作
        	
        	StringBuffer sb = new StringBuffer();
        	boolean zj = true;
            for (Cell cell : result.rawCells()) {
            	row = Bytes.toString(cell.getRowArray(),
					cell.getRowOffset(), cell.getRowLength());
            	
            	quali = Bytes.toString(cell.getQualifierArray(),
					cell.getQualifierOffset(),
					cell.getQualifierLength());
            	value = Bytes.toString(cell.getValueArray(),
					cell.getValueOffset(),
					cell.getValueLength());
				
				// 获取列簇
				family = Bytes.toString(cell.getFamilyArray(),
					cell.getFamilyOffset(), 
					cell.getFamilyLength());
				String s =map.get(row);
				System.out.println(row);
				System.out.println(s);
				if(zj==true) {
					sb.append(s+","+row+","+value);
					zj=false;
				}
				else {
					sb.append(","+value);
				}
            }
            System.out.println(sb);
            File file2  = new File("D:\\tel_test.txt");
	        
			outFile = new FileOutputStream(file2, true);
			if(sb.toString()!=null&&!"".equals(sb.toString())) {
				outFile.write((sb.toString()+"\r\n").getBytes("UTF-8"));
				outFile.flush();
			}
        } 
	}
}
