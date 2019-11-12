/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.Tencent.weChatLogin.restful;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RedisUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.adpf.modules.Tencent.weChatLogin.entity.WechatLoginUserinfo;
import com.adpf.modules.Tencent.weChatLogin.service.WechatLoginUserinfoService;
import com.adpf.modules.Tencent.weChatLogin.utils.WeChatUtils;
import com.adpf.modules.gdtm.util.MD5Util;

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/adpf/weChatUser")
public class WechatLoginUserinfoRestful {

	protected static final Logger logger = LogManager.getLogger(WechatLoginUserinfoRestful.class);
    
    @Autowired
    private WechatLoginUserinfoService wechatLoginUserinfoService;
    @Autowired
    private WeChatUtils weChatUtils;
    
	/**
	 * 用于记录打开日志
	 * @param request
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public JSONObject index(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getList",method=RequestMethod.GET)
	public JSONObject getList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
//			UserInfo userInfo = SessionUtils.getUserInfo();
//			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
//			else {
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
				WechatLoginUserinfo wechatLoginUserinfo = (WechatLoginUserinfo)BeanUtils.mapToBean(params, WechatLoginUserinfo.class);				
				json = wechatLoginUserinfoService.getList(pageable, wechatLoginUserinfo, params);
//			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	@RequestMapping(value="/getToken",method=RequestMethod.GET)
	public JSONObject getTokenByCode(HttpServletRequest request,@RequestParam Map<String,Object>params) {
		JSONObject tokenOrOpenin =new JSONObject();
		JSONObject weChatUser =new JSONObject();
		JSONObject json =new JSONObject();
		String str = "";
		try {
			String code = (String) params.get("code");
			if(params.get("code") != null) {
				tokenOrOpenin = weChatUtils.token(code);
				String token = (String) tokenOrOpenin.get("access_token");
				String openid = (String) tokenOrOpenin.get("openid");
				if(openid==null&&"".equals(openid)) {
					json = RestfulRetUtils.getErrorMsg("51002","获取access_token失败");
					return json;
				}
				weChatUser = weChatUtils.userInfo(token, openid);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("openid", weChatUser.get("openid"));
				map.put("country", weChatUser.get("country"));
				map.put("province", weChatUser.get("province"));
				map.put("city", weChatUser.get("city"));
				map.put("sex", weChatUser.get("sex"));
				map.put("nickname", weChatUser.get("nickname"));
				map.put("headimgurl", weChatUser.get("headimgurl"));
				map.put("language", weChatUser.get("language"));
				map.put("privilege", weChatUser.get("privilege"));
				json = wechatLoginUserinfoService.queryUser(map);
				Pattern p=Pattern.compile("\\{[^}]+\\}");
		        Matcher matcher=p.matcher(json.getString("RetData"));
		        if (matcher.find()) {
		        	str = matcher.group(0);
		        }
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			json = RestfulRetUtils.getErrorMsg("51002","获取access_token失败");
			return json;
		}
		WechatLoginUserinfo wechatLoginUserinfo = JSONObject.parseObject(str,WechatLoginUserinfo.class);
		return RestfulRetUtils.getRetSuccess(wechatLoginUserinfo);
	}
	
	@RequestMapping(value="/getCode",method=RequestMethod.GET)
	public JSONObject getCodeByOpId(HttpServletRequest request,@RequestParam Map<String,Object>params) {
		JSONObject tokenOrOpenin =new JSONObject();
//		JSONObject weChatUser =new JSONObject();
		JSONObject json =new JSONObject();
		String str = "";
		try {
			String code = (String) params.get("code");
			if(params.get("code") != null) {
				tokenOrOpenin = weChatUtils.token(code);
//				String token = (String) tokenOrOpenin.get("access_token");
				String openid = (String) tokenOrOpenin.get("openid");
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("openid", openid);
				json = wechatLoginUserinfoService.queryUser(map);
				Pattern p=Pattern.compile("\\{[^}]+\\}");
		        Matcher matcher=p.matcher(json.getString("RetData"));
		        if (matcher.find()) {
		        	str = matcher.group(0);
		        }
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			json = RestfulRetUtils.getErrorMsg("51002","获取access_token失败");
			return json;
		}
		WechatLoginUserinfo wechatLoginUserinfo = JSONObject.parseObject(str,WechatLoginUserinfo.class);
		return RestfulRetUtils.getRetSuccess(wechatLoginUserinfo);
	}
	
	@RequestMapping(value="/userInfo",method=RequestMethod.POST)
	public JSONObject userInfo(HttpServletRequest request,@RequestBody Map<String, Object> params) {
		JSONObject json = new JSONObject();
		String str = "";
//		json = wechatLoginUserinfoService.userInfo(params);
		json = wechatLoginUserinfoService.queryID(params);
		Pattern p=Pattern.compile("\\{[^}]+\\}");
        Matcher matcher=p.matcher(json.getString("RetData"));
        if (matcher.find()) {
        	str = matcher.group(0);
        }
        WechatLoginUserinfo wechatLoginUserinfo = JSONObject.parseObject(str,WechatLoginUserinfo.class); 
		return RestfulRetUtils.getRetSuccess(wechatLoginUserinfo);
	}
	
	/**
	 * 新增信息
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public JSONObject add(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
//			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
//			else {
				json = wechatLoginUserinfoService.add(params, userInfo);
//			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51002","新增信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
		
	/**
	 * 编辑信息
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public JSONObject edit(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
//			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
//			else {
				json = wechatLoginUserinfoService.edit(params, userInfo);
//			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51003","编辑信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 删除信息
	 * @param request
	 * @param idsList
	 * @return
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public JSONObject del(HttpServletRequest request,@RequestBody String idsList){
		JSONObject json = new JSONObject();
		try {
			UserInfo userInfo = SessionUtils.getUserInfo();
//			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
//			else {
				json = wechatLoginUserinfoService.del(idsList, userInfo);
//			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
}
