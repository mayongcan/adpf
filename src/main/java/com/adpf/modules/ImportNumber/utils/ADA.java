package com.adpf.modules.ImportNumber.utils;

import java.io.File;

public class ADA {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*for(int i = 0; i<= 101;i++){
			String str=String.format("%03d",i);
			System.out.println(str);
		}*/
		
		File file = new File("C:\\Users\\Administrator\\Desktop\\cc");
        
        File[] fileList = file.listFiles();
        
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()) {
                String fileName = fileList[i].getName();
                System.out.println("文件：" + fileList[i]);                
            }
            
            if (fileList[i].isDirectory()) {
                String fileName = fileList[i].getName();
                System.out.println("目录：" + fileName);        
            }
        }
		
	}

}
