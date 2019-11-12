package com.adpf.modules.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteTxtUtils {


//	public static void  writeTxt() {
//		Workbook book;
//		try {
//			book = WriteTxtUtils.getWorkBook(new File("C:\\Users\\Administrator\\Desktop\\新建文件夹\\全量更新方式H码表-截至2018年第一批\\附件18.中国电信1740H码汇总表.xls"));
//			Sheet sheet = book.getSheetAt(0);// 获得第一个工作表对象
//
//			int rows = sheet.getPhysicalNumberOfRows();
//			Row row = sheet.getRow(3);
//			int columns = row.getPhysicalNumberOfCells();
//			File file =new File("C:\\Users\\Administrator\\Desktop\\新建文件夹\\txt\\附件18.中国电信1740H码汇总表.txt");
//			if(!file.exists()){
//				file.createNewFile();
//			} 
//			BufferedWriter bw =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
//			// 循环从第3行开始的所有行,从0开始
//			for (int i = 3; i <rows; i++) {
//				StringBuffer sb =new StringBuffer();
//				//循环所有列
//				for (int j = 0; j <columns; j++) {
//					sheet.getRow(i).getCell(j).setCellType(Cell.CELL_TYPE_STRING);
//					sheet.getRow(2).getCell(j).setCellType(Cell.CELL_TYPE_STRING);
//					if(j<3) {
//						sb.append(sheet.getRow(i).getCell(j).getStringCellValue()+",");
//						if(j == 2) {
//							sb.append("电信,");
//						}
//					}else {
//						if(sheet.getRow(i).getCell(j) != null && !"".equals(sheet.getRow(i).getCell(j).getStringCellValue())) {
//
//							
//							
//
//							String cellVal = sheet.getRow(i).getCell(j).getStringCellValue();
//							String []  cellValArr= cellVal.split("、");
//							//拿出单个列的值拆分成数组，根据“、”进行拆分
//							for(int z=0;z<cellValArr.length;z++) {
//								if(cellValArr[z] != null && !"".equals(cellValArr[z])) {
//									if(cellValArr[z].indexOf("-") !=-1) {
//										String [] arr = cellValArr[z].split("-");
//										for(int k = Integer.valueOf(arr[0]);k<=Integer.valueOf(arr[1]);k++) {
//											String val =String.valueOf(k);
//											if(val.length()<3) {
//												String newVal = "000"+val;
//												val = newVal.substring(newVal.length()-3);
//											}
//											String s =sb.toString()+sheet.getRow(2).getCell(j).getStringCellValue()+val+"\r\n";
//											bw.write(s);
//										}
//									}else {
//										String  s = sb.toString() + sheet.getRow(2).getCell(j).getStringCellValue()+cellValArr[z]+ "\r\n";
//										bw.write(s);
//									}
//								}
//							}
//						}
//					}
//				}
//				System.out.println("完成了第"+i+"行");
//			}
//			bw.flush();
//			bw.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		}
//	}
//
//	public static Workbook getWorkBook(File file) throws IOException {  
//		//获得文件名  
//		String fileName = file.getName();  
//		//创建Workbook工作薄对象，表示整个excel  
//		Workbook workbook = null;  
//
//		//获取excel文件的io流  
//		InputStream is = new FileInputStream(file);  
//		//根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象  
//		if(fileName.endsWith("xls")){  
//			//2003  
//			workbook = new HSSFWorkbook(is);  
//		}else if(fileName.endsWith("xlsx")){  
//			//2007  
//			workbook = new XSSFWorkbook(is);  
//		}  
//
//		return workbook;  
//	}  
}
