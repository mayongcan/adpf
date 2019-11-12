package com.adpf;

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
import com.google.common.io.Files;

public class getCode {
	
	
	
	public static void main(String[] args) throws IOException {	  
		File file = new File("c://line//");
		 File[] fileList = file.listFiles();
		 for(int i = 0;i < fileList.length; i++){
			 if(fileList[i].isFile()){
				 System.out.println(fileList[i]);
				 Document doc = Jsoup.parse(fileList[i],"UTF-8","");
				 //System.out.println(doc);
				// Elements tag = doc.select("div.app-info-desc > a:eq(0)");
				   Elements sElements = doc.select("div.app-info");
				 //System.out.println(aa.attr("href"));
				 Map<String, String> map = new HashMap<String, String>();
				 for(Element param:sElements){
					 String imgpath = param.select("img").attr("src");
					 String apkname = param.select("div.app-info-desc > a:eq(0)").attr("href").split("=")[1];
					 String name = param.select("div.app-info-desc > a:eq(0)").text();
					 String apksize = param.select("span.size").text();
					 String installurl = param.select("a.com-install-btn").attr("ex_url");
					 String cateid = doc.select("div.category-wrapper").attr("cateid");
					 System.out.println(installurl);
//					 map.put("imgpath", imgpath);
//					 map.put("apkname", apkname);
//					 map.put("name", name);
//					 map.put("apksize", apksize);
//					 map.put("installurl", installurl);
//					 map.put("cateid", cateid);
//					 Collection collection1 = (Collection) BeanUtils.mapToBean(map, Collection.class);
//					 cc.save(collection1);
					
				 }
	 
	 
 }
		        
		    

}
		 
	}
	
}
