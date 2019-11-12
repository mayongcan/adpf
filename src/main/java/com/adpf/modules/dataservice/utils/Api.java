package com.adpf.modules.dataservice.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adpf.modules.dataservice.doGet.Example;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;

@Service
public class Api {
	
	@Autowired
	private Example example;
	private static String APIKEY = "8YrWb9POB8yENGAxdaf2eocZ4of9zABF2axYqD5vL8MvaWuL3EBEdkGnOifmTwGw";
	//360手机助手  sort 0 最热 1 最新
	private String mobile360Url = "http://api01.idataapi.cn:8000/mobileapp/mobile360?apikey=APIKEY";
	//360手机助手评论
	private String mobile360Comment = "http://api01.idataapi.cn:8000/comment/mobile360?apikey=APIKEY";
	//360网页搜索  range
	private String webSearch360 = "http://api01.idataapi.cn:8000/page/so?apikey=APIKEY";
	//360新闻
//	private String news360 = "http://api01.idataapi.cn:8000/page/so?apikey=APIKEY";
	//BBC新闻
	private String newsBBC = "http://api02.idataapi.cn:8000/news/bbc?apikey=APIKEY";
	//reddit文章 
	private String redditArticle = "http://api02.idataapi.cn:8000/post/reddit?apikey=APIKEY";
	//reddit评论 
	private String retheDead = "http://api02.idataapi.cn:8000/comment/reddit?apikey=APIKEY";
	//东方财富股吧
	private String eastmoneyguba = "http://api01.idataapi.cn:8000/post/eastmoneyguba?apikey=APIKEY";
	//东方财富股吧用户信息
	private String eastUser = "http://api01.idataapi.cn:8000/profile/eastmoneyguba?apikey=APIKEY";
	//东方财富股吧评论
	private String eastComment = "http://api01.idataapi.cn:8000/comment/eastmoneyguba?apikey=APIKEY";
	//央视新闻plus 
	private String CCTVPlus = "http://api01.idataapi.cn:8000/news/cctvplus?apikey=APIKEY";
	//央视新闻plus评论 
	private String CCTVPlusComment = "http://api01.idataapi.cn:8000/comment/cctvplus?apikey=APIKEY";
	//手机号码归属地查询 
	private String MPAttribution = "http://api02.idataapi.cn:8000/tools/phone_number_ascription?apikey=APIKEY";
	//搜狗新闻
	private String newsSogou = "http://api01.idataapi.cn:8000/news/sogou?apikey=APIKEY";
	//搜狗网页搜索
//	private String webSearchSogou = "http://api01.idataapi.cn:8000/page/sogou?apikey="+apikey;
	//搜狗指数
	private String sogouRank = "http://api01.idataapi.cn:8000/kwindex/sogou?apikey=APIKEY";
	//新浪体育
	private String sinaSports = "http://api01.idataapi.cn:8000/post/sina_sport?apikey=APIKEY";
	//番组计划book
	private String bangumiBook = "http://api01.idataapi.cn:8000/book/bangumi?apikey=APIKEY";
	//番组计划game
	private String bangumiGame = "http://api01.idataapi.cn:8000/game/bangumi?apikey=APIKEY";
	//bangumi小组 
	private String bangumiGroup = "http://api01.idataapi.cn:8000/group/bangumi?apikey=APIKEY";
	//bangumi文章
	private String bangumiArticle = "http://api01.idataapi.cn:8000/post/bangumi?apikey=APIKEY";
	//bangumi用户信息 
	private String bangumiUserInfo = "http://api02.idataapi.cn:8000/profile/bangumi?apikey=APIKEY";
	//bangumi音乐 
	private String bangumiMusic = "http://api02.idataapi.cn:8000/album/bangumi?apikey=APIKEY";
	//bangumi短评论 
	private String bangumiComment = "http://api02.idataapi.cn:8000/comment/bangumi?apikey=APIKEY";
	//百思不得姐
	private String baisibudejie = "http://api01.idataapi.cn:8000/profile/baisibudejie?apikey=APIKEY";
	//百思不得姐帖子
	private String baisibudejiePost = "http://api01.idataapi.cn:8000/post/baisibudejie?apikey=APIKEY";
	//百思不得姐评论
	private String baisibudejieComment = "http://api01.idataapi.cn:8000/comment/baisibudejie?apikey=APIKEY";
	//腾讯体育
	private String qqSport = "http://api01.idataapi.cn:8000/post/qqsport?apikey=APIKEY";
	//腾讯体育用户信息
	private String qqSportUserInfo = "http://api01.idataapi.cn:8000/profile/qqsport?apikey=APIKEY";
	//腾讯体育评论
	private String qqSportReview = "http://api01.idataapi.cn:8000/comment/qqsport?apikey=APIKEY";
	//购房网
	private String goufangwang = "http://api01.idataapi.cn:8000/house/goufangwang?apikey=APIKEY";
	//通用新闻资讯接口
	private String idataapi = "http://api01.idataapi.cn:8000/article/idataapi?apikey=APIKEY";
	
	private static Map<String,String> map = new HashMap<>();
	{
		map.put("mobileap/pmobile360", mobile360Url);
		map.put("comment/mobile360", mobile360Comment);
		map.put("page/so", webSearch360);
//		map.put("page/so", news360);
		map.put("news/bbc", newsBBC);
		map.put("post/reddit", redditArticle);
		map.put("comment/reddit", retheDead);
		map.put("post/eastmoneyguba", eastmoneyguba);
		map.put("profile/eastmoneyguba", eastUser);
		map.put("comment/eastmoneyguba", eastComment);
		map.put("news/cctvplus", CCTVPlus);
		map.put("comment/cctvplus", CCTVPlusComment);
		map.put("tools/phone_number_ascription", MPAttribution);
		map.put("news/sogou", newsSogou);
		map.put("kwindex/sogou", sogouRank);
		map.put("post/sina_sport", sinaSports);
		map.put("book/bangumi", bangumiBook);
		map.put("game/bangumi", bangumiGame);
		map.put("group/bangumi", bangumiGroup);
		map.put("post/bangumi", bangumiArticle);
		map.put("profile/bangumi", bangumiUserInfo);
		map.put("album/bangumi", bangumiMusic);
		map.put("comment/bangumi", bangumiComment);
		map.put("profile/baisibudejie", baisibudejie);
		map.put("post/baisibudejie", baisibudejiePost);
		map.put("comment/baisibudejie", baisibudejieComment);
		map.put("post/qqsport", qqSport);
		map.put("profile/qqsport", qqSportUserInfo);
		map.put("comment/qqsport", qqSportReview);
		map.put("house/goufangwang", goufangwang);
		map.put("article/idataapi", idataapi);
	}
	
	public JSONObject api(Map<String,String> params) {
		JSONObject json = new JSONObject();
		System.out.println(params);
		try {
			String api = params.get("api");
			String mold = params.get("mold");
			if(api==null)
				return RestfulRetUtils.getErrorMsg("100002", "未知api！");
			else
				if(mold==null) 
					return RestfulRetUtils.getErrorMsg("100003", "未知类型！");
			StringBuilder url = new StringBuilder();
			String urlMap= (String)map.get(mold+"/"+api);
			url.append(urlMap.replace("APIKEY", APIKEY));
			System.out.println(mold+"/"+api);
			if(params.get("baid")!=null)
				url.append("&baid="+params.get("baid"));		
			if(params.get("id")!=null)
				url.append("&id="+params.get("id"));
			if(params.get("kw")!=null)
				url.append("&kw="+params.get("kw"));
			if(params.get("catid")!=null)
				url.append("&catid="+params.get("catid"));
			if(params.get("phoneNumber")!=null)
				url.append("&phoneNumber="+params.get("phoneNumber"));
			if(params.get("range")!=null)
				url.append("&range="+params.get("range"));
			if(params.get("site")!=null)
				url.append("&site="+params.get("site"));
			if(params.get("uid")!=null)
				url.append("&uid="+params.get("uid"));
			if(params.get("cityid")!=null)
				url.append("&cityid="+params.get("cityid"));
			if(params.get("pageToken")!=null)
				url.append("&pageToken="+params.get("pageToken"));
			if(params.get("page")!=null)
				url.append("&page="+params.get("page"));
			if(params.get("type")!=null)
				url.append("&type="+params.get("type"));
			if(params.get("beginDate")!=null)
				url.append("&beginDate="+params.get("beginDate"));
			if(params.get("endDate")!=null)
				url.append("&endDate="+params.get("endDate"));
			if(params.get("city")!=null)
				url.append("&city="+params.get("city"));
//			if()
//				return RestfulRetUtils.getErrorMsg("100003", "没有参数！");
			System.out.println(url.toString());
			json = example.getRequestFromUrl(url.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return RestfulRetUtils.getErrorMsg("100005", "url格式错误！");
		}
		return json;
	}
}
