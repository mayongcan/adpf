package com.adpf.modules.gdtm.service.hive.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.adpf.modules.cms.restful.AdpfCmsArticleRestful;
import com.adpf.modules.gdtm.count.entity.AdpfLabelCount;
import com.adpf.modules.gdtm.count.entity.AdpfLabelCountProvince;
import com.adpf.modules.gdtm.count.repository.AdpfLabelCountProvinceRepository;
import com.adpf.modules.gdtm.count.repository.AdpfLabelCountRepository;
import com.adpf.modules.gdtm.service.hbase.impl.HBaseCrudServiceImpl;
import com.adpf.modules.gdtm.service.hbase.test.HBaseConnectionUtils;
import com.adpf.modules.gdtm.service.hive.HiveService;
import com.adpf.modules.gdtm.service.hive.util.HiveJDBCConnectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;

@Service
public class HiveServiceImpl implements HiveService{
	
	private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    
    
    protected static final Logger logger = LogManager.getLogger(HiveServiceImpl.class);
    
	@Autowired
    private AdpfLabelCountRepository adpfLabelCountRepository;
	
    @Autowired
    private AdpfLabelCountProvinceRepository adpfLabelCountProvinceRepository;
    
    public static void main(String[] args) throws Exception {
    	HiveJDBCConnectionUtils h = new HiveJDBCConnectionUtils();
    	stmt = h.Connection();
    	HiveServiceImpl hj = new HiveServiceImpl();
//    	hj.createUserTable("adpf_imei_test11");
    	hj.createTable("adpf_iemi_tel_province");
//    	hj.loadData();
    	hj.destory();
	}
    
    public void selectData11() throws Exception {
        String sql1 = "SELECT rowKey,label FROM adpf_label al WHERE al.label IN (SELECT label FROM adpf_label_1)";
        String sql = "SELECT al.rowKey,fa.idfa FROM adpf_label.adpf_label al left join default.idfa_0418 fa on al.rowKey=fa.imei14_md5 WHERE al.label='00002050'";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        FileOutputStream outFile = null;
        while (rs.next()) {
        	System.out.println(rs.getString(1)+rs.getString(2));
        	File file2  = new File("E:\\zzz\\user222.txt");
        	if (!file2.exists()) {
				file2.mkdirs();
		    }
			outFile = new FileOutputStream(file2, true);
			outFile.write((rs.getString(1)+","+rs.getString(2)+"\r\n").getBytes());
			outFile.flush();
        }
        if(outFile != null) {
			outFile.close();
		}
    }
    
    //创建数据库
	@Override
	public void createDatabase(String base) {
		String sql = "create database "+base;
        System.out.println("Running: " + sql);
        try {
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//查看数据库
	@Override
	public void showDatabases() {
		String sql = "show databases";
        System.out.println("Running: " + sql);
        try {
        	rs = stmt.executeQuery(sql);
			while (rs.next()) {
			    System.out.println(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	//查看表
	@Override
	public JSONObject showTables() throws SQLException {
		HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();	
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();	
		String sql = "show tables";
        System.out.println("Running: " + sql);        
        try {
        	stmt = connection.Connection();
        	rs = stmt.executeQuery(sql);
			while (rs.next()) {
			 json.put("tableName",rs.getString(1));
			 System.out.println(rs.getString(1));
			 array.add(JSON.parse(JSON.toJSONString(json)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
		return RestfulRetUtils.getRetSuccessWithPage(array,1);
		
	}

	//查看表结构
	@Override
	public JSONObject descTable(String tableName) throws SQLException {
		HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();	
		JSONObject json = new JSONObject();	
		JSONArray array = new JSONArray();	
		String sql1 = "desc formatted " + tableName;
		String sql = "desc " + tableName;
        System.out.println("Running: " + sql);
        try {
        	stmt = connection.Connection();
        	rs = stmt.executeQuery(sql);
			while (rs.next()) {
				json.put("type",rs.getString(1));
				json.put("field",rs.getString(2));
				array.add(JSON.parse(JSON.toJSONString(json)));
				System.out.println(rs.getString(1) + "\t" + rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
		return RestfulRetUtils.getRetSuccessWithPage(array,1);
	}

	//查询表信息
	@Override
	public void selectData(String tableName) {
		 String sql = "select rowkey,collect_set(province)[0],collect_set(devices)[0] from " + tableName +" where devices='iPhone' or devices='Android' GROUP BY rowKey";
	     System.out.println("Running: " + sql);  
	     try {
	    	rs = stmt.executeQuery(sql);
			while (rs.next()) { 
				System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void selectDataTest() {
		 String sql = "select * from adpf_imei";
	     System.out.println("Running: " + sql);  
	     try {
	    	rs = stmt.executeQuery(sql);
	    	int i = 0;
			while (rs.next()) {
				i++;
				System.out.println(rs.getString(""));
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// hive数据插到mysql里
	public JSONObject countData(String HiveTableName,String month,String day) throws Exception {
		//hive连接池
		HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();
		stmt = connection.Connection();
    	JSONObject json = new JSONObject();
    	//标签全用户
    	String sql = "select label,count(label)as num from "+ HiveTableName +" where month='"+ month +"' and day='"+ day +"' GROUP BY label";
    	String sqlTest = "select collect_set(date)[0],label,count(label)as num from "+ HiveTableName +" GROUP BY label";
		Map<String,Object> params = new HashMap<>();
		Map<String,Object> params1 = new HashMap<>();
		
		logger.info("查询"+month+day+"用户标签,Running: " + sql);
	    rs = stmt.executeQuery(sql);
        logger.info("查询中.................................");
		while (rs.next()) {
			//获取时间
			params.put("date", month+day);
			params.put("label", rs.getString("label"));
			params.put("size", rs.getString("num"));
			params.put("weight", rs.getString("num"));
		    System.out.println("params"+params);
			//查询表中最新时间
		    AdpfLabelCount adpfLabelCount = (AdpfLabelCount) BeanUtils.mapToBean(params, AdpfLabelCount.class);
		    List<Map<String,Object>> list = adpfLabelCountRepository.getDate(adpfLabelCount, params);
		    System.out.println(list);
		    //判断表里面是否有数据
		    if(list.size()!=0) {
		        String id = list.get(0).get("id").toString();
		        String label = list.get(0).get("label").toString();
			    params1.put("id", id);
			    params1.put("label", label);
				params1.put("size", rs.getString("num"));
				params1.put("weight", rs.getString("num"));
				System.out.println("params1"+params1);
				
				AdpfLabelCount adpfLabelCount2 = (AdpfLabelCount) BeanUtils.mapToBean(params1, AdpfLabelCount.class);
				AdpfLabelCount adpfLabelCountInDb = adpfLabelCountRepository.findOne(adpfLabelCount2.getId());
				if(adpfLabelCountInDb == null){
					return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
				}
				//合并两个javabean
				BeanUtils.mergeBean(adpfLabelCount2, adpfLabelCountInDb);
				adpfLabelCountRepository.save(adpfLabelCountInDb);
			    logger.info("时间不为空,日期为"+month+day+",更新");
		    }
	        //最新时间和当天时间不等就插入
		    if(list.size()==0){
		    	logger.info("数据为空,条件符合，插入");
		        //插入信息
		        AdpfLabelCount adpfLabelCount1 = (AdpfLabelCount) BeanUtils.mapToBean(params, AdpfLabelCount.class);
				adpfLabelCountRepository.save(adpfLabelCount1);
		    }		  
		}
		if ( rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
	    return json;
	}
	
	//测试  hive数据插到mysql里
	public JSONObject countData1(String HiveTableName,String month,String day) throws Exception {
		//hive连接池
		HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();
		stmt = connection.Connection();	
    	JSONObject json = new JSONObject();
    	//省份标签用户
    	String sql = "select province,label,count(label)as num from "+ HiveTableName +" where month='"+ month +"' and day='"+ day +"' GROUP BY label,province"; 
    	
		Map<String,Object> params = new HashMap<>();
		Map<String,Object> params1 = new HashMap<>();
		logger.info("查询"+month+day+"省份用户标签,Running: " + sql);

	    rs = stmt.executeQuery(sql);
        logger.info("查询中..........................");
		while (rs.next()) {
			params.put("date", month+day);
			params.put("label", rs.getString("label"));
			params.put("province", rs.getString("province"));
			params.put("size", rs.getString("num"));
			params.put("weight", rs.getString("num"));
			System.out.println("params"+params);
			AdpfLabelCountProvince adpfLabelCountProvince = (AdpfLabelCountProvince) BeanUtils.mapToBean(params, AdpfLabelCountProvince.class);
		    List<Map<String,Object>> list = adpfLabelCountProvinceRepository.getDate(adpfLabelCountProvince, params);
		    //判断表里面是否有数据
		    if(list.size()!=0) {
		        String label = list.get(0).get("label").toString();
		        String id = list.get(0).get("id").toString();
		        params1.put("id", id);
		        params1.put("label", label);
				params1.put("size", rs.getString("num"));
				params1.put("weight", rs.getString("num"));
				System.out.println("params1"+params1);
		        logger.info("数据不为空,日期是"+month+day+",更新");
		        
				AdpfLabelCount adpfLabelCount2 = (AdpfLabelCount) BeanUtils.mapToBean(params1, AdpfLabelCount.class);
				AdpfLabelCount adpfLabelCountInDb = adpfLabelCountRepository.findOne(adpfLabelCount2.getId());
				if(adpfLabelCountInDb == null){
					return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
				}
				//合并两个javabean
				BeanUtils.mergeBean(adpfLabelCount2, adpfLabelCountInDb);
				adpfLabelCountRepository.save(adpfLabelCountInDb);
		    }
	        //最新时间和当天时间不等就插入
		    if(list.size()==0){
		    	logger.info("数据为空，条件符合，插入");
		        //插入信息
		        AdpfLabelCountProvince adpfLabelCountProvince1 = (AdpfLabelCountProvince) BeanUtils.mapToBean(params, AdpfLabelCountProvince.class);
				adpfLabelCountProvinceRepository.save(adpfLabelCountProvince1);	
		     }
		}
		if ( rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
	    return json;
	}
	
	// hive数据插到hbase里
	public JSONObject countData2(String HiveTableName,String HbaseTableName,String date) throws Exception {
//		String month = date.substring(0,6);
//		String day = date.substring(6,8);
		HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();
		org.apache.hadoop.hbase.client.Connection HBaseconnection = null;
		HBaseconnection = HBaseConnectionUtils.getConnection();
		Table table = HBaseconnection.getTable(TableName.valueOf(HbaseTableName));
		stmt = connection.Connection();
	    JSONObject json = new JSONObject();
	    	
	    String sql = "SELECT rowkey,label FROM "+ HiveTableName +" where month='transfer' and day='transfer'";
		HBaseCrudServiceImpl h = new HBaseCrudServiceImpl();
//		List<Increment> putList = new ArrayList<>();
		List<Row> batch = new ArrayList<>();
		logger.info("查询中间表用户标签权重,Running: " + sql);
		try {
		    rs = stmt.executeQuery(sql);
		    int i = 0;
		    Increment increment2 = null;
			while (rs.next()) {
//				//通过rowKey获取标签
//				String weightStr = h.get(HbaseTableName, rs.getString("rowkey"),"cf1",rs.getString("label"));	    
//				//判断标签是否相同
//				if(!weightStr.isEmpty()) {
//					weight += Integer.valueOf(weightStr);
//					System.out.println(weight);
//				}	
//				Put put = new Put(Bytes.toBytes(rs.getString("rowkey")));
//				increment2.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes(rs.getString("label")), Bytes.toBytes(weight.toString()));
//				putList.add(increment2);
				i++;
				increment2 = new Increment(Bytes.toBytes(rs.getString("rowkey")));
				increment2.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes(rs.getString("label")),(long)1);
				batch.add(increment2);
				if(i%1000==0) {
					table.batch(batch);
					batch.clear();
					logger.info("正在插入第"+i+"条");
				}
			}
			table.batch(batch);
			logger.info("插入完成");
		} catch (SQLException e) {
			e.printStackTrace();
			File file2  = new File("D://imei1/error.txt");
			FileOutputStream outFile = new FileOutputStream(file2, true);
			outFile.write((rs.getString("rowkey")+","+rs.getString("label")).getBytes());
			outFile.flush();	
			outFile.close();
			logger.info("出错了:"+" "+rs.getString("rowkey"));
		}finally {
			if (table != null) {
				table.close();
			}
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
		    return json;
		}
	
	//删除表
	@Override
	public JSONObject deopTable(String HiveTableName) throws SQLException{
		HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();
        try {
        	stmt = connection.Connection();	
        	String sql = "drop table if exists "+HiveTableName;
            System.out.println("Running: " + sql);
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {	
	        if (stmt != null) {
	            stmt.close();
	        }
	        if (conn != null) {
	            conn.close();
	        }
		}
		return RestfulRetUtils.getRetSuccess();	
	}

	//释放资源
	@Override
	public void destory() throws SQLException {
		if ( rs != null) {
            rs.close();
            System.out.println("关闭资源");
        }
        if (stmt != null) {
            stmt.close();
            System.out.println("关闭资源");
        }
        if (conn != null) {
            conn.close();
            System.out.println("关闭资源");
        }
    }
	
	//test插入
	public void test() {
		String sql="insert into emptest values(1,'1')";
		System.out.println("Running: " + sql);
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	//创建表
    public void createUserTable(String hiveTableName) throws SQLException{
        try {
        	HiveJDBCConnectionUtils h = new HiveJDBCConnectionUtils();
        	stmt = h.Connection();
        	String sql = "create table "+ hiveTableName +"(\n" +
        			"id string,\n" +
                    "imei_md5 string,\n" +
                    "province string,\n" +
                    "label string,\n" +
                    "urltranslate string,\n" +
                    "idfa string,\n" +
                    "mp_model string,\n" +
                    "imeitranslate string,\n" +
                    "imeitranslate_md5_15 string,\n" +
                    "sai string,\n" +
                    "tar string,\n" +
                    "tel string,\n" +
                    "travel string\n" +
                     ")\n" +
                  "row format delimited fields terminated by ','";
        	 System.out.println("Running: " + sql);
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
	        if (stmt != null) {
	            stmt.close();
	        }
	        if (conn != null) {
	            conn.close();
	        }
		}
        
    }
    
    //装载文件
    public void loadData() throws Exception {
        String filePath = "/user/hive_q/duizhao.txt";
        String sql = "load data inpath '" + filePath + "' OVERWRITE into table adpf_imei_nocontrol";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }
    
	//创建分区表
    public void createPartition(String hiveTableName) throws SQLException{
        try {
        	HiveJDBCConnectionUtils h = new HiveJDBCConnectionUtils();
        	stmt = h.Connection();
        	String sql = "create table "+ hiveTableName +"(\n" +
        			"id string,\n" +
                    "imei_md5 string,\n" +
                    "province string,\n" +
                    "label string,\n" +
                    "urltranslate string,\n" +
                    "idfa string,\n" +
                    "mp_model string,\n" +
                    "imeitranslate string,\n" +
                    "imeitranslate_md5_15 string,\n" +
                    "sai string,\n" +
                    "tar string,\n" +
                    "tel string,\n" +
                    "travel string\n" +
                     ")\n" +
                     "partitioned by (month string,day string)"+
                     "row format delimited fields terminated by ','";
        	 System.out.println("Running: " + sql);
			 stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
	        if (stmt != null) {
	            stmt.close();
	        }
	        if (conn != null) {
	            conn.close();
	        }
		}  
    }
    
    //添加分区
    public void addPartition(String hiveTableName,String month,String day) throws SQLException{
        try {
        	HiveJDBCConnectionUtils h = new HiveJDBCConnectionUtils();
        	stmt = h.Connection();
        	String sql = "alter table "+hiveTableName+" add partition (month='"+ month +"',day='"+day+"')";
        	System.out.println("Running: " + sql);	
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
	        if (stmt != null) {
	            stmt.close();
	        }
	        if (conn != null) {
	            conn.close();
	        }
		}  
    }
    
    
    //装载数据
  	@Override
  	public void loadData(String filePath,String Hive_TableName) throws Exception {
  		//  filePath = "/user/hive_q/rtb20180730_rq-11c.txt";
  		//"load data local inpath '"+ filePath +"' overwrite into table "+ Hive_TableName;	
  		String path = "hdfs://192.168.40.104:8020/user/hive_q";
  		logger.info("path："+path);
  		
    	HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();
    	stmt = connection.Connection();
    	  	
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(path), conf);
		FileStatus[] status = fs.listStatus(new Path(path));
	
		for (FileStatus file : status) {
		    if (!file.getPath().getName().startsWith(".")) {
		    	if(file.isDir()) {
		    		String filePathName = file.getPath().getName();
		    		String month = filePathName.substring(0, 6);
	    	    	String day = filePathName.substring(6, 8);
	    	    	//添加分区
	    	    	String sql = "alter table "+Hive_TableName+" add partition (month='"+ month +"',day='"+day+"')";
	    	    	logger.info("添加分区中,Running: " + sql);
	    	    	stmt.execute(sql);
	    	        
		    		String path1 = "hdfs://192.168.40.104:8020/user/hive_q/"+file.getPath().getName();
		    		FileStatus[] status1 = fs.listStatus(new Path(path1));
		    		for(FileStatus file1 : status1) {
		    			if(!file1.getPath().getName().isEmpty()) {
		    			String sqlLoad = "load data inpath '/user/hive_q/"+filePathName+"/"+file1.getPath().getName()+"' into table adpf_label_transfer PARTITION (month='transfer',day='transfer')";
		    		//	String sqlLoad = "load data inpath '/user/hive_q/"+filePathName+"/"+file1.getPath().getName()+"' into table "+ Hive_TableName +" PARTITION (month='"+ month +"',day='"+day+"')";
		    			logger.info("数据正在往adpf_label_transfer中间表中,Running: " + sqlLoad);
			  			stmt.execute(sqlLoad);
			  			logger.info("装载成功");
			  			String insert =  "insert into table "+ Hive_TableName +" PARTITION (month='"+ month +"',day='"+day+"') select date,rowKey,province,label,phoneNum,devices from adpf_label_transfer where month='transfer' and day='transfer'";
			  			logger.info("正在把中间表的数据往"+ Hive_TableName +"存储,Running: " + sqlLoad);
			  			stmt.execute(insert);
			  			logger.info("存储成功");
				            continue;
		    			}
		    		}	
		    		countData("adpf_label", month, day);
		    		countData1("adpf_label", month, day);
		    		countData2("adpf_label_transfer", "adpf_user_label", filePathName);	
		    		String dec = "ALTER TABLE adpf_label_transfer DROP IF EXISTS PARTITION (month='transfer',day='transfer')";
		    		logger.info("正在清空中间表...........");
		    		stmt.execute(dec);
		    		logger.info("清除完成................");
		    	}
		    }
		}
		//遍历空文件夹
		for(FileStatus file : status) {
			if (!file.getPath().getName().startsWith(".")) {
				if(file.isDir()) {
					String path1 = "hdfs://192.168.40.104:8020/user/hive_q/"+file.getPath().getName();
					FileStatus[] status1 = fs.listStatus(new Path(path1));
					if(status1.length==0) {
						logger.info("删除空文件夹...");
			    		fs.delete(new Path(path1), true);
					}
				}
			}
		}
		if (stmt != null) {
	        stmt.close();
	       }
	    if (conn != null) {
	        conn.close();
	       }
  	}
   
    //字段，hive表名，分割符
    public void createHiveTable(String tableType,String field,String hiveTableName,String division) throws SQLException {
    	String[] cfsArr = field.split(",", -1);
    	StringBuilder cf1 = new StringBuilder();
		for (String cf : cfsArr) {		
			cf1.append(cf +",\n");			
		}
		cf1.deleteCharAt(cf1.length()-2);
		String sql="create TABLE "+ hiveTableName + " (\n" +
				cf1 + 
				")\n" +
                "row format delimited fields terminated by '"+ division +"'";
		System.out.println("Running: " + sql);
		try {
			HiveJDBCConnectionUtils h = new HiveJDBCConnectionUtils();
        	stmt = h.Connection();
			stmt.execute(sql);
		} catch (Exception e) {	
			e.printStackTrace();
		}finally {
	        if (stmt != null) {
	            stmt.close();
	        }
	        if (conn != null) {
	            conn.close();
	        }
		}
    }
    
    //hbase映射表..  EXTERNAL 外部表
  	public void createTable(String HiveTableName) {	
  		try {
  			HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();
  	    	stmt = connection.Connection();
  	  		String sql="create EXTERNAL TABLE "+ HiveTableName +" (\n" +
  	                  "imei_md5 string,\n" +
  	                  "tel string,\n" +
  	                  "province string)\n" +
  	                  "STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'\n" +
  	                  "WITH SERDEPROPERTIES (\"hbase.columns.mapping\" = \":key,cf1:tel,cf1:province\")\n" +
  	                  "TBLPROPERTIES(\"hbase.table.name\" = \"adpf_imei_tel_province\")";
  	  		System.out.println("Running: " + sql);
  			stmt.execute(sql);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  	}
  	
  //测试 ...查询基本标签
  	public JSONObject selectData() {
  		HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();
      	try {
  			stmt = connection.Connection();
  		} catch (Exception e1) {
  			e1.printStackTrace();
  		}
  		 String sql = "select * from (select * from adpf_basic_label_test)a left outer join (select * from adpf_tag_library_test)b on a.label = b.key";
  	     System.out.println("Running: " + sql); 
  	     JSONObject json = new JSONObject();
  	     JSONArray array = new JSONArray();
  	     try {
  	    	rs = stmt.executeQuery(sql);
  			while (rs.next()) {
  			     json.put("key",rs.getString("key"));
  			     json.put("devices",rs.getString("devices"));
  			     json.put("label",rs.getString("name"));
  			     json.put("province",rs.getString("province"));
  			     json.put("weight",rs.getString("weight"));
  			     array.add(JSON.parse(JSON.toJSONString(json)));
  			 }
  			System.out.println(array);
  		} catch (SQLException e) {
  			e.printStackTrace();
  		}
  	    return RestfulRetUtils.getRetSuccessWithPage(array,1);
  	}
  	
  //通过外部表导入hive表
    public void createHiveTable(String NewHiveTableName,String HiveTableName) throws Exception {
        String sql = "create table "+NewHiveTableName+" as select * from "+HiveTableName;
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }
 
    //映射Hbase,创建外部表
    @Override
    public void createTable(String field,String tableName,String Hbase_tableName,String Hbase_field) {
    	String[] cfsArr = field.split(",", -1);
    	StringBuilder cf1 = new StringBuilder();
		for (String cf : cfsArr) {		
			cf1.append(cf +",\n");			
		}
		cf1.deleteCharAt(cf1.length()-2);
		String sql="create EXTERNAL TABLE "+ tableName + " (\n" +
				cf1 + 
				")\n" +
                "STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'\n" +
                "WITH SERDEPROPERTIES (\"hbase.columns.mapping\" = \"" + Hbase_field +"\")\n" +
                "TBLPROPERTIES(\"hbase.table.name\" = \""+ Hbase_tableName +"\")";
		System.out.println("Running: " + sql);	
		try {
			stmt.execute(sql);
		} catch (SQLException e) {	
			e.printStackTrace();
		}	
    }

	@Override
	public void dropDatabase(String base) {
		// TODO Auto-generated method stub
		
	}
}
