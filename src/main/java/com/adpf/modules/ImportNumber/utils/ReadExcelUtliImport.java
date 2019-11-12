package com.adpf.modules.ImportNumber.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adpf.modules.ImportNumber.entity.AdpfImportNumberFile;
import com.adpf.modules.ImportNumber.repository.AdpfImportNumberFileRepository;
import com.adpf.modules.ImportNumber.restful.AdpfImportNumberFileRestful;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;

@Component
public class ReadExcelUtliImport  {

	  	@Autowired
	     public AdpfImportNumberFileRepository adpfImportNumberFileRepository;

	    public static ReadExcelUtliImport logTool;
	    @PostConstruct
	    public void init() {
	    	logTool = this;
	    }
		@Autowired
	     public AdpfImportNumberFileRestful ImportNumber;
		
	  	public void readExcelWithTitle(HttpServletRequest request,String filepath) throws Exception{
			String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());
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
			    int sheetSize = wb.getNumberOfSheets();
			    for (int i = 0; i < sheetSize; i++) {//遍历sheet页
			    	Map<Integer, String> result1 = new HashMap<Integer,String>();//对应excel文件
			    	Sheet sheet = wb.getSheetAt(i);
			    	//行
			    	int rowSize = sheet.getLastRowNum() + 1;
			    	//System.out.println(rowSize);
			    	for (int j = 1; j < rowSize; j++) {//遍历行
			    		Map<String, Object> result = new HashMap<String,Object>();//对应excel文件
			    		int serialNumber=0;
			    		Row row = sheet.getRow(j);
			    		if (row == null) {//略过空行
							continue;
						}
			    		if(j==1){
			    			int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列
			    			for (int k = 0; k < cellSize; k++) {
				    			Cell cell = row.getCell(k);
				    			String a=cell.toString();
				    			if(a.contains("电信")){
				    				result1.put(1, "中国电信");
				    			}
				    		}
			    		}else if(j==2){
			    			int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列
			    			for (int k = 3; k < cellSize; k++) {
				    			Cell cell = row.getCell(k);
				    			String a=cell.toString();
				    			result1.put(k, a);
				    		}
			    		}else{
			    			int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列
			    			for (int k = 0; k <= cellSize-1; k++) {
			    					result.put("operator", result1.get(1));
				    			if(k==0){
				    				Cell cell = row.getCell(k);
				    				result.put("province", cell.toString());
				    			}else if(k==1){
				    				Cell cell = row.getCell(k);
				    				result.put("city", cell.toString());
				    			}else if(k==2){
				    				Cell cell = row.getCell(k);
				    				result.put("areaCode", cell.toString());
				    			}else{
				    				Cell cell = row.getCell(k);
				    				if("".equals(cell.toString())==false){
				    					String a1=cell.toString();
				    					String[] dada=a1.split("、");
					    				String fafa=result1.get(k);
					    				String qq=fafa.substring(0,4);
					    				//System.out.println(qq);
					    				for (int L = 0; L<dada.length;L++) {
					    					String[] xiaox=dada[L].split("-");
					    					if(xiaox.length>1){
					    						int s=Integer.parseInt(xiaox[0]);
					    						int s1=Integer.parseInt(xiaox[1]);
					    						for (int A =s ; A< s1;A++) {
					    							serialNumber+=1;
					    							result.put("phone", qq+A);
					    							result.put("serialNumber", serialNumber+"");
					    							result.put("filePath", filepath);
					    							///System.out.println("数组："+result);
					    							AdpfImportNumberFile adpfImportNumberFile = (AdpfImportNumberFile) BeanUtils.mapToBean(result, AdpfImportNumberFile.class);
					    							//System.out.println(adpfImportNumberFile);
					    							logTool.adpfImportNumberFileRepository.save(adpfImportNumberFile);
					    							
					    						}
					    					}else{
					    						serialNumber+=1;
					    						result.put("serialNumber", serialNumber+"");
					    						result.put("phone", qq+xiaox[0]);
					    						result.put("filePath", filepath);
					    						//System.out.println("数组"+result);
					    						AdpfImportNumberFile adpfImportNumberFile = (AdpfImportNumberFile) BeanUtils.mapToBean(result, AdpfImportNumberFile.class);
					    						//System.out.println(adpfImportNumberFile);
					    						logTool.adpfImportNumberFileRepository.save(adpfImportNumberFile);
					    					}
					    			}
				    				}else{
				    					continue;
				    				}
				    			}
			    			}
			    		}
			    	}
			    	//System.out.println("号码"+result1);
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
		}
}
