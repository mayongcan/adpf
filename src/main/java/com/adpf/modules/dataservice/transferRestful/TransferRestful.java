/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.dataservice.transferRestful;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.adpf.modules.dataservice.service.JudgeService;
import com.adpf.modules.dataservice.utils.Api;

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/adpf/transfer")
public class TransferRestful{

	protected static final Logger logger = LogManager.getLogger(TransferRestful.class);
    
	@Autowired
	private JudgeService judgeService;
	@Autowired
	private Api apiUrl;

	@RequestMapping(value="/verification",method=RequestMethod.GET)
	public JSONObject getList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try {
			//URL
			String url = "http://" + request.getServerName()+":"
					+ request.getServerPort()
					+ request.getContextPath()
					+ request.getServletPath()
					+ "?" + (request.getQueryString());
			String serverName = request.getServerName();
			Integer serverPort = request.getServerPort();
//			String param = request.getQueryString();
			json = judgeService.apiUrl(serverName,url,serverPort,params);
		} catch (Exception e){
			json = RestfulRetUtils.getErrorMsg("100015", "验证错误!");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	//get
	@RequestMapping(value="/apiget",method=RequestMethod.GET)
	public JSONObject dataApiGet(HttpServletRequest request, @RequestParam Map<String, String> params){
		//http://localhost:8050/api/adpf/transfer/apiget?api=xxx&type=xxx
		JSONObject json = new JSONObject();
		try {
			json = apiUrl.api(params);
		}catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("100014", "接口错误!");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	@RequestMapping(value="/apipost",method=RequestMethod.POST)
	public JSONObject dataApiPost(HttpServletRequest request, @RequestBody Map<String, Object> params){
		return null;
	}
}
