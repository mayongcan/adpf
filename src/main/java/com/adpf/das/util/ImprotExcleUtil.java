package com.adpf.das.util;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImprotExcleUtil {

    ArrayList<Map<String, String>> mapList = new ArrayList<>();

    public ArrayList<Map<String, String>> improtExcelFileRestful(HttpServletRequest request, String filePathName) throws Exception {
        String fileType = filePathName.substring(filePathName.lastIndexOf(".") + 1, filePathName.length());
        InputStream is = null;
        Workbook wb = null;
        is = new FileInputStream(filePathName);
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook(is);
        } else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook(is);
        } else {
            throw new Exception("读取的不是excel文件");
        }
        String[] proyName = {"curlid", "videoId", "albumId", "period", "tvid", "tvName", "tvType", "videoName", "mediaName", "completeUrl"};

        //开始解析
        //设置第一行的值，因为表获取的第一行数据跟实体类的属性不一致
        Sheet sheet = wb.getSheetAt(0);
        for (int i = 0; i < proyName.length; i++) {
            sheet.getRow(0).getCell((short) i).setCellValue(proyName[i]);
        }

        //第一行是列名，所以从第二行开始遍历
        int firstRowNum = sheet.getFirstRowNum()+1;
        int lastRowNum = sheet.getLastRowNum();
        for (int i = firstRowNum; i < lastRowNum + 1; i++) {
            Map map = new HashMap();
            //获取当前行的内容
            Row row = sheet.getRow(i);
            if (row != null) {
                int firstCellNum = row.getFirstCellNum();
                int lastCellNum = row.getLastCellNum();
                for (int cIndex = firstCellNum; cIndex < lastCellNum; cIndex++) {
                    row.getCell(cIndex).setCellType(Cell.CELL_TYPE_STRING);
                    //获取单元格的值
                    String value = row.getCell(cIndex).getStringCellValue();
                    //获取此单元格对应第一行的值
                    String key = sheet.getRow(0).getCell(cIndex).getStringCellValue();
                    //第一行中的作为键，第n行的作为值
                    map.put(key, value);
                }
                mapList.add(map);
            }
        }
        return mapList;
    }
}
