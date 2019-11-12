package com.adpf.modules.dataservice.utils;

import org.springframework.stereotype.Service;

import com.adpf.modules.gdtm.util.MD5Util;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;

@Service
public class Judge {
	
	public JSONObject apiUrl(String url) {
		String urlMd5 = MD5Util.MD5(url);
		System.out.println(url);
		System.out.println(urlMd5);
		if(urlMd5==urlMd5) {
			return RestfulRetUtils.getRetSuccess();
		}
		else {
			return RestfulRetUtils.getErrorMsg("100001", "非法URL！");	
		}
	}
	
	public void apikeyJudge(String apikey) {}
	
	public void kwJudge(String kw) {}

}
