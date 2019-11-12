package com.adpf.collection;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.adpf.collection.entity.Collection;
import com.adpf.collection.repository.CollectionRepository;
import com.gimplatform.core.utils.BeanUtils;

public class getCode {
	
	//@SuppressWarnings("unused")
	@Autowired
	private CollectionRepository collectionRepository;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		 File file = new File("c://line//");
		 File[] fileList = file.listFiles();
		 for(int i = 0;i < fileList.length; i++){
			 if(fileList[i].isFile()){
				 System.out.println(fileList[i]);
				 Document doc = Jsoup.parse(fileList[i],"UTF-8","");
				 //System.out.println(doc);
				 Elements tag = doc.select("div.app-info");
				 //System.out.println(tag);
				 for(Element aElement :tag){
					
					 System.out.println(aElement.select("a:eq(1)").attr("href"));
					 //System.out.println(aElement.select("img").attr("src"));
					 
				 }
				 //System.out.println(aa.attr("href"));
//				 for(Element a :tag){
//					 String apkName	= a.attr("href").split("=")[1];
//					 String name = a.text();
//					 System.out.println(apkName+" "+name);
//					 Map<String, String> map = new HashMap<String, String>();
//					 map.put("apkname", apkName);
//					 map.put("name", name);
//					 Collection collection = (Collection) BeanUtils.mapToBean(map, Collection.class);
//					 
//					 
//				 }
				 
				 System.out.println("..........................................");
			 }
		 }
	}

}
