package com.adpf.modules.cms;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class reptiles {
		static String sendGet(String url){
			// 定义一个字符串用来存储网页内容
			String result = "";
			// 定义一个缓冲字符输入流
			BufferedReader in = null;
			try{
				// 将string转成url对象
				URL realUrl = new URL(url);
				// 初始化一个链接到那个url的连接
				URLConnection connection = realUrl.openConnection();
				// 开始实际的连接
				connection.connect();
				// 初始化 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				// 用来临时存储抓取到的每一行的数据
				String line;
				while ((line = in.readLine()) != null){
					// 遍历抓取到的每一行并将其存储到result里面
					//System.out.println(line);
					result += line;
				}
			} catch (Exception e){
				System.out.println("发送GET请求出现异常！" + e);
				e.printStackTrace();
			} // 使用finally来关闭输入流
			finally{
				try{
					if (in != null){
						in.close();
					}
				} catch (Exception e2){
					e2.printStackTrace();
				}
			}
			return result;
		}
		 public String getString(String s,FileWriter fw) throws Exception {
			   //List<String> strs = new ArrayList<String>();
			   String strs="OK";
			   Pattern p = Pattern.compile("watch-pic(.*?)watch-icon");
			   Pattern p1 = Pattern.compile("title(.*?)img");
			   Pattern p2 = Pattern.compile("href(.*?)target");
			   Matcher m = p.matcher(s);
			   while(m.find()) {
				   String data=m.group();
				   Matcher m1 = p1.matcher(data);
				   Matcher m2 = p2.matcher(data);
				   
				   while(m1.find()&&m2.find()) {
					   String ss=m1.group();
					   String a= ss.replace("title=\"", "");
					   String a1=a.replace("\"><img", "");
					   
					   String sss=m2.group();
					   int b1= sss.indexOf("/",sss.length()-16);
					   String b2= sss.substring(b1+1,sss.length()-9);
					   
					   fw.write(a1+"               "+b2+"\r\n");
				   }
			   }
			   return strs;
			}
		public static void main(String[] args) throws Exception{
			FileWriter fw = new FileWriter("C:\\土豪表.txt");
            for(int i=1000;i<=1684;i++){
            	String url="";
            	if(i==1){
            		url = "http://watch.xbiao.com/";
            		//fw.write("\r\n第"+i+"页！\r\n\r\n");
            	}else{
            		// 定义即将访问的链接
        			 url = "http://watch.xbiao.com/p"+i+".html";
        			 //fw.write("\r\n\r\n\r\n\r\n第"+i+"页！\r\n\r\n");
            	}
    			// 访问链接并获取页面内容
    			String result = sendGet(url);
    			reptiles reptiles=new reptiles();
    			String result1=reptiles.getString(result,fw);
    			System.out.println(result1+i);
            }
            fw.close();
		}
}
