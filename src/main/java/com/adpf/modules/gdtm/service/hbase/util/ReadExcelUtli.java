package com.adpf.modules.gdtm.service.hbase.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.adpf.modules.gdtm.service.hbase.impl.HBaseCrudServiceImpl;
import com.adpf.modules.gdtm.service.hive.HiveJDBC;
import com.adpf.modules.gdtm.util.MD5Util;

@Service
public class ReadExcelUtli {
	
	
	 public static void main(String[] args) throws Exception {
		 ReadExcelUtli r = new ReadExcelUtli();
		 r.readExcelWithTitle("E:\\imei\\data.xlsx");  
	    }
	 
	 public List<List<Map<String, String>>> readExcelWithTitle(String filepath) throws Exception{
			String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());
			InputStream is = null;
			Workbook wb = null;
			MD5Util md5 = new MD5Util();
			HBaseCrudServiceImpl hbase = new HBaseCrudServiceImpl();
			HiveJDBC hj = new HiveJDBC();
			try {
				is = new FileInputStream(filepath);
				
			    if (fileType.equals("xls")) {
			    	wb = new HSSFWorkbook(is);
			    } else if (fileType.equals("xlsx")) {
			    	wb = new XSSFWorkbook(is);
			    } else {
			    	throw new Exception("读取的不是excel文件");
			    }
			    
			    List<List<Map<String, String>>> result = new ArrayList<List<Map<String,String>>>();//对应excel文件
			    
			    int sheetSize = wb.getNumberOfSheets();
			    for (int i = 0; i < sheetSize; i++) {//遍历sheet页
			    	Sheet sheet = wb.getSheetAt(i);
			    	List<Map<String, String>> sheetList = new ArrayList<Map<String, String>>();//对应sheet页
			    	
			    	List<String> titles = new ArrayList<String>();//放置所有的标题
			    	
			    	int rowSize = sheet.getLastRowNum() + 1;
			    	for (int j = 0; j < rowSize; j++) {//遍历行
			    		Row row = sheet.getRow(j);
			    		if (row == null) {//略过空行
							continue;
						}
			    		int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列
			    		if (j == 0) {//第一行是标题行
			    			for (int k = 0; k < cellSize; k++) {
				    			Cell cell = row.getCell(k);
				    			titles.add(cell.toString());
				    		}
			    		} else {//其他行是数据行		    			
			    			Map<String, String> rowMap = new HashMap<String, String>();//对应一个数据行
			    			for (int k = 0; k < titles.size(); k++) {
			    				Cell cell = row.getCell(k);
			    				String key = titles.get(k);
			    				String value = null;
			    				if (cell != null) {
									value = cell.toString();
								}
			    				rowMap.put(key, value);		    				
			    			}
			    			String imei = rowMap.get("imei");
			    			String imei1 = imei.substring(0,imei.length()-1);
			    			String md5imei = md5.MD5(imei1);
//			    			System.out.println(md5imei);		    			
			    			hbase.getList("adpf_user_label", md5imei);
		//	    			hj.test(md5imei);
			    		}
			    	}
			    }
			    
			    return result;
			} catch (FileNotFoundException e) {
				throw e;
			} finally {
				if (wb != null) {
					wb.close();
				}
				if (is != null) {
					is.close();
				}
			}
		}
}
