package com.adpf.modules.dataservice.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adpf.modules.dataservice.doGet.Example;
import com.adpf.modules.dataservice.service.JudgeService;
import com.adpf.modules.dataservicelogin.repository.DataServiceLoginRepository;
import com.adpf.modules.gdtm.util.MD5Util;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;

@Service
public class JudgeServiceImpl implements JudgeService{
	
	@Autowired
	private DataServiceLoginRepository dataServiceLoginRepository;
	
	@Autowired
	private Example example;

	@Override
	public JSONObject currency(String url) {
		
		return null;
	}

	@Override
	public JSONObject apiUrl(String serverName,String url,Integer post,Map<String, Object> params) {
		JSONObject json = new JSONObject();
		try {
			//用户名
			String username = (String)params.get("username");
			//订单号
			String orders = (String)params.get("orders");
			//判断用户名是否为空
			if(null==username || null==orders)
				return RestfulRetUtils.getErrorMsg("100009", "用户名或订单为空");
			//根据用户名查询
			List<Map<String, Object>> list = dataServiceLoginRepository.getUserInfo(username);
			//用户名查询为空
			if(null==list || list.isEmpty())
				return RestfulRetUtils.getErrorMsg("100005", "用户名错误");
			json = RestfulRetUtils.getRetSuccess(list);
			//获取数据库appkey
			String appkey = json.getJSONArray("RetData").getJSONObject(0).get("appkey").toString();
			//后台拼接URL
			String url1 = "http://"+serverName+":"+post+"/api/adpf/transfer/verification?appkey="+appkey+"&orders="+orders+"&username="+username;
			if(null==appkey)
				return RestfulRetUtils.getErrorMsg("100000", "appkey为空！");
			//判断appkey是否匹配
			if(!appkey.equals((String)params.get("appkey")))
				return RestfulRetUtils.getErrorMsg("100012", "appkey不匹配");
			//验证前后端URL
			if(!MD5Util.MD5(url).equals(MD5Util.MD5(url1)))
				return RestfulRetUtils.getErrorMsg("100011", "URL不合法");
			json = example.getRequestFromUrl("http://"+serverName+":8050/api/adpf/dataServer/getList?orders="+orders);
		} catch (IOException e) {
			json = RestfulRetUtils.getErrorMsg("100013", "接口异常");
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public void apikeyJudge(String apikey) {
	
	}

	@Override
	public void kwJudge(String kw) {
	
	}

}
