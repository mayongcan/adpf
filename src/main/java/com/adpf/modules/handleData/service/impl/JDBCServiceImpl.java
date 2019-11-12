package com.adpf.modules.handleData.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.adpf.modules.handleData.restful.DataHandleRecordRestful;
import com.adpf.modules.handleData.service.JDBCService;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

@Service
public class JDBCServiceImpl implements JDBCService {
	
	public static void main(String[] args) {
		JDBCServiceImpl j = new JDBCServiceImpl();
		String table = "tb_20181029_1_t";
//		j.createTbale(table);
//		j.insertData(table,"F:\\ss\\ss\\rtb20181029_1-t_0.txt");
		j.PlaceOfBelonging(table,"wjb_tb_out_two");
//		j.moveToOldTable();
//		j.ToRepeat("", "", "");
	}
	protected static final Logger logger = LogManager.getLogger(JDBCServiceImpl.class);
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://120.79.161.102:3306/wjb";
	
	static final String USER = "root";
	static final String PASS = "root";
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement ps = null;
	HSSFWorkbook hssfWorkbook = null;
	ResultSet rs = null;
	
	@Override
	public void createTbale(String table) {
		 try { 
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("连接池..........");
			conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = (Statement) conn.createStatement();
			System.out.println("创建表中.........");
			String sql = "CREATE TABLE " + table +
	                   "(tel VARCHAR(255), " +
	                   "times VARCHAR(255)," +
	                   "appid VARCHAR(255))";
			stmt.executeUpdate(sql);
			System.out.println("创建成功.........");
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(stmt!=null) {
					conn.close();
				}
				if(conn!=null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();			
			}
		}
	}

	@Override
	public String insertData(String table,String file) {
		String Return = "";
		FileInputStream fs = null;
		InputStreamReader isr = null;
		try {
			String str = "";
			fs = new FileInputStream(file);
			isr =  new InputStreamReader(fs);
			BufferedReader br = new BufferedReader(isr);
			conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
			StringBuilder sb = new StringBuilder();
			while((str = br.readLine())!=null) {
				String[] value = str.split(",");
				if(!"".equals(value[7])&&value[7].length()==11) {
					sb.append(value[7]+","+value[12]+","+value[3]+"\n");
					if(sb.length()==0) {
						Return="empty";
						return Return;
					}
				}
			}
			byte[] bytes = sb.toString().getBytes();
			InputStream is = new ByteArrayInputStream(bytes);
			String sql = "load data local infile '' into table "+table+" character set utf8 fields terminated by ','";
			ps = (PreparedStatement) conn.prepareStatement(sql);
//			ps.unwrap(PreparedStatement.class);
			ps.setLocalInfileInputStream(is);
			ps.execute();
            conn.commit();
		} catch (SQLException e) {
			Return = "exist";
			logger.error(e.getMessage(), e);
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(stmt!=null) {
				conn.close();
				}
				if(conn!=null) {
					conn.close();
				}
				if(fs!=null) {
					fs.close();
				}
				if(isr!=null) {
					isr.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Return;
	}

	@Override
	public JSONObject moveToOldTable(String table,String table_out) {
		JSONObject json = new JSONObject();
		try {
			conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = (Statement) conn.createStatement();
			String sql = "insert into "+table_out+" SELECT tel from "+table+" where not exists (select DISTINCT tel from "+table_out+" where tel="+table+".tel)";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			json = RestfulRetUtils.getErrorMsg("52210", "SQL错误");
			e.printStackTrace();
		}finally {
			try {
				if(stmt!=null) {
				conn.close();
				}
				if(conn!=null) {
					conn.close();
				}
			} catch (SQLException e) {
				json = RestfulRetUtils.getErrorMsg("52210", "SQL错误");
				e.printStackTrace();
			}
		}
		return json;
	}

	@Override
	public JSONObject ToRepeat(String table,String tableA,String tableB) {
		try {
			conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = (Statement) conn.createStatement();
			String sql = "inter into "+table+" select tel,a1.times+b1.times from "+tableA+" as a1,"+tableB+" as b1 where a1.tel=b1.tel";
			stmt.executeUpdate(sql);
			String sqlA = "inter into "+table+" select * from "+tableA+" not in (select * from "+tableB+")";
			stmt.executeUpdate(sqlA);
			String sqlB = "inter into "+table+" select * from "+tableB+" not in (select * from "+tableA+")";
			stmt.executeUpdate(sqlB);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(stmt!=null) {
				conn.close();
				}
				if(conn!=null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public JSONObject Custom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject PlaceOfBelonging(String table,String table_out) {
		JSONObject json = new JSONObject();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet spreadsheet = workbook
	                .createSheet("test");
	        XSSFRow row = spreadsheet.createRow(0);
	        
	        row.createCell(0).setCellValue("tel");
            row.createCell(1).setCellValue("times");
            row.createCell(2).setCellValue("city");
            row.createCell(3).setCellValue("province");
            row.createCell(4).setCellValue("label");

			conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = (Statement) conn.createStatement();
			String sql = "select tb.tel,tb.times,tb.city,tb.province,ad.label "
					+ "from (SELECT a1.tel as tel,a1.appid as appid,a1.times as times ,m.city as city,m.province as province "
					+ "from wjb."+table+" a1 JOIN wjb.mobile_region_all m on LEFT(a1.tel,7)=m.number) tb,"
					+ "adpf.adpf_tag_library ad "
					+ "where left(tb.tel,2)!='17' and tb.appid=ad.tag_id and tb.tel not in (select tel from wjb."+table_out+") "
					+ "GROUP BY tb.tel ORDER BY length(tb.times) DESC,tb.times DESC";
			rs = stmt.executeQuery(sql);
			//跳过标题行
            int index = 1;
			while(rs.next()) {
				//下一行开始
                row = spreadsheet.createRow(index++);
                //每列信息
                row.createCell(0).setCellValue(rs.getString(1));
                row.createCell(1).setCellValue(rs.getString(2));
                row.createCell(2).setCellValue(rs.getString(3));
                row.createCell(3).setCellValue(rs.getString(4));
                row.createCell(4).setCellValue(rs.getString(5));
			}
			File  file = new File("D:/Users/zzd/Work/ProjectRun/Resources/"+table+".xlsx");
			FileOutputStream out = new FileOutputStream(file);
	        workbook.write(out);
	        out.close();
	        json = RestfulRetUtils.getRetSuccess();
		} catch (SQLException e) {
			json = RestfulRetUtils.getErrorMsg("52210", "SQL错误");
			e.printStackTrace();
		}catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("52210", "错误");
			e.printStackTrace();
		}
		finally {
				try {
					if(stmt!=null) {
					conn.close();
					}
					if(conn!=null) {
						conn.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return json;
	}

}
