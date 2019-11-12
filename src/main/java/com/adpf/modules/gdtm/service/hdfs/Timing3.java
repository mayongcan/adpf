package com.adpf.modules.gdtm.service.hdfs;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.adpf.modules.gdtm.count.entity.AdpfLabelCount;
import com.adpf.modules.gdtm.count.entity.AdpfLabelCountProvince;
import com.adpf.modules.gdtm.count.repository.AdpfLabelCountProvinceRepository;
import com.adpf.modules.gdtm.count.repository.AdpfLabelCountRepository;
import com.adpf.modules.gdtm.service.hbase.test.HBaseConnectionUtils;
import com.adpf.modules.gdtm.service.hive.impl.HiveServiceImpl;
import com.adpf.modules.gdtm.service.hive.util.HiveJDBCConnectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;

@Component
public class Timing3 {
	
	protected static final Logger logger = LogManager.getLogger(HiveServiceImpl.class);
	
	private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();
    
    @Autowired
    private AdpfLabelCountRepository adpfLabelCountRepository;
	
    @Autowired
    private AdpfLabelCountProvinceRepository adpfLabelCountProvinceRepository;
    
//	@Scheduled(cron="0/60 * * * * ?")
    public void b() throws Exception {
    	System.out.println("进入定时..............");
    	loadData();
    	System.out.println("进入完毕定时...........");
    }
	
	public void loadData() throws Exception {
  		String path = "hdfs://192.168.40.104:8020/user/hive_q";
  		logger.info("path："+path);
  		
//    	HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();
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
	    	    	String sql = "alter table adpf_label_new add if not exists partition (month='"+ month +"',day='"+day+"')";
	    	    	logger.info("添加分区中,Running: " + sql);
	    	    	stmt.execute(sql);
	    	    	
	    	    	String sqlT = "alter table adpf_label_transfer add if not exists partition (month='"+ month +"',day='"+day+"')";
	    	    	logger.info("添加中间表分区中,Running: " + sqlT);
	    	    	stmt.execute(sqlT);
	    	        
		    		String path1 = "hdfs://192.168.40.104:8020/user/hive_q/"+file.getPath().getName();
		    		FileStatus[] status1 = fs.listStatus(new Path(path1));
		    		int fileLength = status1.length;
		    		for(FileStatus file1 : status1) {
		    			String sqlLoad = "load data inpath '/user/hive_q/"+filePathName+"/"+file1.getPath().getName()+"' into table adpf_label_transfer PARTITION (month='"+ month +"',day='"+day+"')";
			    		//	String sqlLoad = "load data inpath '/user/hive_q/"+filePathName+"/"+file1.getPath().getName()+"' into table "+ Hive_TableName +" PARTITION (month='"+ month +"',day='"+day+"')";
			    		logger.info("数据正在往adpf_label_transfer中间表中,Running: " + sqlLoad);
			    		try{
			    			stmt.execute(sqlLoad);
					  		logger.info("装载成功");
			    		}catch (SQLException e) {
			    			continue;
						}
		    		}
		    		if(fileLength!=0) {
			    		String insert = "insert into table adpf_label_new PARTITION (month='"+ month +"',day='"+day+"') select id,imei_md5,province,label,urltranslate,idfa,mp_model,imeitranslate,imeitranslate_md5_15,sai,tar,tel,travel from adpf_label_transfer where month='"+month+"' and day='"+day+"'";
				  		logger.info("正在把中间表的数据往adpf_label存储,Running: " + insert);
				  		stmt.execute(insert);
				  		logger.info("存储成功");
		    			countData("adpf_label_new", month, day);
			    		countData1("adpf_label_new", month, day);
			    		countData2("adpf_label_transfer", "adpf_user_label_new", month+day);
//			    		Url("adpf_label_new", month, day);
			    		dec(month, day);
		    		}	
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
			    		fs.delete(new Path(path1), true);
			    		logger.info("删除空文件夹..."+path1);
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
	
	// hive数据插到mysql里
		public JSONObject countData(String HiveTableName,String month,String day) throws Exception {
			//hive连接池
//			HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();
			stmt = connection.Connection();
	    	JSONObject json = new JSONObject();
	    	//标签全用户
	    	String sql = "select label,count(label)as num from "+ HiveTableName +" where month='"+ month +"' and day='"+ day +"' and length(imei_md5)=32 and length(label)=8 GROUP BY label";
			Map<String,Object> params = new HashMap<>();
			Map<String,Object> params1 = new HashMap<>();
			logger.info("查询"+month+day+"用户标签,Running: " + sql);
			try {
				 rs = stmt.executeQuery(sql);
			}catch (Exception e) {
				e.printStackTrace();
			}		    
	        logger.info("查询中.................................");
			while (rs.next()) {
				//获取时间
				params.put("date", month+day);
				params.put("label", rs.getString("label"));
				params.put("size", rs.getString("num"));
				params.put("weight", rs.getString("num"));
			    System.out.println(params);
				//查询表中最新时间
			    AdpfLabelCount adpfLabelCount = (AdpfLabelCount) BeanUtils.mapToBean(params, AdpfLabelCount.class);
			    List<Map<String,Object>> list = adpfLabelCountRepository.getDate(adpfLabelCount, params);
			    String response = null;
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
			        //插入信息
			        AdpfLabelCount adpfLabelCount1 = (AdpfLabelCount) BeanUtils.mapToBean(params, AdpfLabelCount.class);
					adpfLabelCountRepository.save(adpfLabelCount1);
					logger.info("数据为空，插入");
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
//			HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();
			stmt = connection.Connection();	
	    	JSONObject json = new JSONObject();
	    	//省份标签用户
	    	String sql = "select province,label,count(label)as num from "+ HiveTableName +" where month='"+ month +"' and day='"+ day +"' and length(imei_md5)=32 and length(label)=8 GROUP BY label,province"; 
	    	
			Map<String,Object> params = new HashMap<>();
			Map<String,Object> params1 = new HashMap<>();
			logger.info("查询"+month+day+"省份用户标签,Running: " + sql);

			try {
				 rs = stmt.executeQuery(sql);
			}catch (Exception e) {
				e.printStackTrace();
			}
	        logger.info("查询中..........................");
			while (rs.next()) {
				//获取时间
				params.put("date", month+day);
				params.put("label", rs.getString("label"));
				params.put("province", rs.getString("province"));
				params.put("size", rs.getString("num"));
				params.put("weight", rs.getString("num"));
				AdpfLabelCountProvince adpfLabelCountProvince = (AdpfLabelCountProvince) BeanUtils.mapToBean(params1, AdpfLabelCountProvince.class);
			    List<Map<String,Object>> list = adpfLabelCountProvinceRepository.getDate(adpfLabelCountProvince, params);
			    String response = null;
			    //判断表里面是否有数据
			    if(list.size()!=0) {   
			    	String label = list.get(0).get("label").toString();
			        String id = list.get(0).get("id").toString();
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
					logger.info("数据不为空,日期是"+month+day+",更新");
			    }
		        //数据为空，插入
			    if(list.size()==0){		    	
			        //插入信息
			        AdpfLabelCountProvince adpfLabelCountProvince1 = (AdpfLabelCountProvince) BeanUtils.mapToBean(params, AdpfLabelCountProvince.class);
					adpfLabelCountProvinceRepository.save(adpfLabelCountProvince1);	
					logger.info("数据为空，条件符合，插入");
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
		//查询字段urltranslate=36或86的数据，导到adpf_imei
		public void Url(String HiveTableName,String month,String day) throws Exception {
			HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();
			stmt = connection.Connection();	
			String sql ="insert into table adpf_imei select tel,imei_md5,urltranslate from adpf_label_new where month='"+month+"' and day='"+day+"' and substr(`urltranslate`, 0, 2)='86' and length(urltranslate)='15' or substr(`urltranslate`, 0, 2)='35' and length(urltranslate)='15' and length(imei_md5)='32' GROUP BY urltranslate,imei_md5,tel";
			logger.info("查询表字段urltranslate=35或86 " + sql);
	    	try {
				stmt.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	logger.info("查询导入成功................."); 
	        if (stmt != null) {
	            stmt.close();
	        }
	        if (conn != null) {
	            conn.close();
	        }		
		}
		
		// hive数据插到hbase里
		public void countData2(String HiveTableName,String HbaseTableName,String date) throws Exception {
			String month = date.substring(0,6);
			String day = date.substring(6,8);
//			HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();
			org.apache.hadoop.hbase.client.Connection HBaseconnection = null;
			HBaseconnection = HBaseConnectionUtils.getConnection();
			Table table = HBaseconnection.getTable(TableName.valueOf(HbaseTableName));
			stmt = connection.Connection();
		    JSONObject json = new JSONObject();
		    	
		    String sql = "SELECT tel,label FROM "+ HiveTableName +" where month='"+ month +"' and day='"+ day +"' and length(tel)=32 and length(label)=8";
//			List<Increment> putList = new ArrayList<>();
			List<Row> batch = new ArrayList<>();
			logger.info("查询"+month+day+"用户标签权重,Running: " + sql);
			try {
			    rs = stmt.executeQuery(sql);
			    int i = 0;
				while (rs.next()) {
					i++;
					Increment increment2 = new Increment(Bytes.toBytes(rs.getString("tel")));
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
				File file2  = new File("/usr/error.txt");
				if(!file2.exists()){
					file2.mkdirs();
				} 
				FileOutputStream outFile = new FileOutputStream(file2, true);
				outFile.write((rs.getString("tel")+","+rs.getString("label")).getBytes());
				outFile.flush();	
				outFile.close();
				logger.info("出错了:"+" "+rs.getString("tel"));
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
		}
		//清空中间表adpf_label_transfer数据
		public void dec(String month,String day) {
//			HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();
			try {
				stmt = connection.Connection();
				String dec = "ALTER TABLE adpf_label_transfer DROP IF EXISTS PARTITION (month='"+month+"',day='"+day+"')";
	    		logger.info("正在清空中间表............");
	    		stmt.execute(dec);
	    		logger.info("清除完成................");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
