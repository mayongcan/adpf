package com.adpf.modules.cms.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.alibaba.fastjson.JSON;

public class HttpUtil {

	//public static Log logger = LogFactory.getLog(HttpUtil.class);
	protected static final Logger log = LogManager.getLogger(HttpUtil.class);
    //get请求
    public static com.alibaba.fastjson.JSONObject doGet(String requestUrl) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String responseContent  = null;
        com.alibaba.fastjson.JSONObject result = null;
        try {
            //创建Get请求，
            HttpGet httpGet = new HttpGet(requestUrl);
            //执行Get请求，
            response = httpClient.execute(httpGet);
            //得到响应体
            HttpEntity entity = response.getEntity();
            //获取响应内容
            responseContent  = EntityUtils.toString(entity,"UTF-8");
            //转换为map
            result = JSON.parseObject(responseContent);
        } catch (IOException e) {
            log.error("HttpUtil=====Start");
            log.error(e.getMessage(),e);
            log.error("HttpUtil=====End");
        }
        return result;
    }
}
