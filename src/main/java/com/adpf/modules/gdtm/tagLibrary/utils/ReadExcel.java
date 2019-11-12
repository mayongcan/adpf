package com.adpf.modules.gdtm.tagLibrary.utils;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adpf.modules.atm.repository.impl.AdpfAtmAppdefineRepositoryImpl;
import com.adpf.modules.gdtm.service.hbase.impl.HBaseCrudServiceImpl;
import com.adpf.modules.gdtm.tagLibrary.entity.AdpfTagLibrary;
import com.adpf.modules.gdtm.tagLibrary.repository.AdpfTagLibraryRepository;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RedisUtils;
import com.gimplatform.core.utils.RestfulRetUtils;

@Service
public class ReadExcel {
	
     @Autowired
     public AdpfTagLibraryRepository adpfTagLibraryRepository;
	
	 public static void main(String[] args) throws Exception {
		 ReadExcel r = new ReadExcel();
		 r.readExcelWithTitle("E:\\app.xlsx");       
	        
	    }
	 public List<List<Map<String, String>>> readExcelWithTitle(String filepath) throws Exception{
			String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());
			HBaseCrudServiceImpl hb = new HBaseCrudServiceImpl();
			Map<String, String> params = new HashMap<>();
			Map<String, String> params1 = new HashMap<>();
			InputStream is = null;
			Workbook wb = null;
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
    			List<AdpfTagLibrary> list = new ArrayList<>();
				RedisUtils ru = new RedisUtils();
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
			    			 List<AdpfTagLibrary> data = new ArrayList<>();
			    			for (int k = 0; k < titles.size(); k++) {
			    				Cell cell = row.getCell(k);
			    				String key = titles.get(k);
			    				String value = null;
			    				if (cell != null) {
									value = cell.toString();
								}
			    				rowMap.put(key, value);		    				
			    			}
			    			String id = rowMap.get("aid");
			    			String name = rowMap.get("aname");
			    			String rowKey = rowMap.get("appid");
			    			
			    			Double f = Double.valueOf(id);

			    			int aid = (int)Math.ceil(f);
			    			
			    			params.put("id", String.valueOf(aid));
			    			params.put("label", name);
			    			params.put("tagId", rowKey);
		    				
			    			AdpfTagLibrary adpfTagLibrary = (AdpfTagLibrary) BeanUtils.mapToBean(params, AdpfTagLibrary.class);
			    			AdpfTagLibrary adpfTagLibraryInDb = adpfTagLibraryRepository.findOne(adpfTagLibrary.getId());
			    			if(adpfTagLibraryInDb != null){
			    				//合并两个javabean
				    			BeanUtils.mergeBean(adpfTagLibrary, adpfTagLibraryInDb);
				    			adpfTagLibraryRepository.save(adpfTagLibraryInDb);			    			
			    			}else {
				    			params1.put("label", name);
				    			params1.put("tagId", rowKey);
				    			params1.put("isValid", "N");
			    				AdpfTagLibrary adpfTagLibrary1 = (AdpfTagLibrary) BeanUtils.mapToBean(params1, AdpfTagLibrary.class);			    			
				    			adpfTagLibraryRepository.save(adpfTagLibrary1);
				    			
			    			}
			    		}
			    	}
			    }
			   			   
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
			 return null;
		}
}
