package com.adpf.modules.gdtm.count.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class DateUtils {
	
	private static final String RET_CODE = "RetCode";
    private static final String RET_MSG = "RetMsg";
    private static final String RET_DATA = "RetData";
    private static final String RET_ROWS = "rows";
    private static final String RET_TOTAL = "total";

    private static final String CODE_OK = "000000";
    private static final String CODE_NO_USER = "10010";
    private static final String CODE_NO_CONTENT = "000001";
    private static final String CODE_NO_PARAMS = "000002";

    private static final String MESSAGE_NO_USER = "缺少用户对象";
    private static final String MESSAGE_NO_CONTENT = "获取内容为空";
    private static final String MESSAGE_NO_PARAMS = "传输参数有误";
    
    public static JSONObject getRetSuccessWithPage(List<?> list, long total) {
        JSONObject json = new JSONObject();
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
        if (list != null) {
            json.put(RET_CODE, CODE_OK);
            json.put(RET_ROWS, JSON.parse(JSON.toJSONString(list, SerializerFeature.WriteDateUseDateFormat)));
            json.put(RET_TOTAL, total);
        } else {
            json.put(RET_CODE, CODE_NO_CONTENT);
            json.put(RET_MSG, MESSAGE_NO_CONTENT);
        }
        return json;
    }
    
    public static JSONObject getRetSuccess(List<?> list, long total) {
        JSONObject json = new JSONObject();
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM";
        if (list != null) {
            json.put(RET_CODE, CODE_OK);
            json.put(RET_ROWS, JSON.parse(JSON.toJSONString(list, SerializerFeature.WriteDateUseDateFormat)));
            json.put(RET_TOTAL, total);
        } else {
            json.put(RET_CODE, CODE_NO_CONTENT);
            json.put(RET_MSG, MESSAGE_NO_CONTENT);
        }
        return json;
    }

}
