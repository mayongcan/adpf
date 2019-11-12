///*
// * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
// */
//package com.adpf.modules.wechat.restful;
//
//import java.io.File;
//import java.util.*;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.alibaba.fastjson.JSONObject;
//import com.gimplatform.core.entity.UserInfo;
//import com.gimplatform.core.utils.BeanUtils;
//import com.gimplatform.core.utils.RestfulRetUtils;
//import com.gimplatform.core.utils.SessionUtils;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import com.adpf.modules.handleData.entity.DataHandleRecord;
//import com.adpf.modules.handleData.service.DataHandleRecordService;
//import com.adpf.modules.handleData.service.JDBCService;
//import com.adpf.qtn.util.RedisUtilsExtend;
//import com.adpf.qtn.util.WeChatUtil;
//
///**
// * Restful接口
// * @version 1.0
// * @author 
// */
//@RestController
//@RequestMapping("/api/adpf/web")
//public class WeChatRestful {
//
//	protected static final Logger logger = LogManager.getLogger(WeChatRestful.class);
//	
//	@RequestMapping(value="/getToken",method=RequestMethod.GET)
//	public JSONObject getTokenByCode(HttpServletRequest request,@RequestParam Map<String,Object>params) {
//		JSONObject json =new JSONObject();
//		try {
//			String code = (String) params.get("code");
//			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxc3b5ba2650ec2c20&secret=1d271c58a462178693ee9f71a108bd09&code="+code+"&grant_type=authorization_code";
//			if(params.get("code") != null) {
//				HttpClient httpClient = HttpClientBuilder.create().build();
//	            HttpGet httpGet = new HttpGet(url);
//	            HttpResponse httpResponse = httpClient.execute(httpGet);
//	            HttpEntity httpEntity = httpResponse.getEntity();
//	            String tokens = EntityUtils.toString(httpEntity, "utf-8");
//	            System.out.println(url);
//	            System.out.println(tokens);
//			}
//		}catch(Exception e){
//			logger.error(e.getMessage(),e);
//			json = RestfulRetUtils.getErrorMsg("51002","获取access_token失败");
//		}
//		return json;
//	}
//}
