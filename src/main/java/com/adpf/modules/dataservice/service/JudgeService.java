package com.adpf.modules.dataservice.service;

import java.util.Map;
import com.alibaba.fastjson.JSONObject;

public interface JudgeService {
	//普通判断
	public JSONObject currency(String url);
	//URL判断
	public JSONObject apiUrl(String serverName,String url,Integer post,Map<String, Object> params);
	//apikey判断
	public void apikeyJudge(String apikey);
	//kw判断
	public void kwJudge(String kw);
}
