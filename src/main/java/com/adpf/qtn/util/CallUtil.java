package com.adpf.qtn.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.Synchronized;

public class CallUtil {
	
	/**
	 * 根据key在redis取值 
	 * @param key
	 * @return
	 */
	public String sequenceCall(String key) {
		//锁机制
		synchronized(this){
			String value = RedisUtilsExtend.lpopList(key);
			return value;
		}
	}
	
	/**
	 * 根据key在redis中list对象取多个值
	 * @param key
	 * @return
	 */
	public List<String> lrangeList(String key,long start,long end) {
		//锁机制
		synchronized(this){
			List<String> list = RedisUtilsExtend.lrangeList(key,start,end);
			return list;
		}
	}
	 
	
	/**
	 * 获取当前时间的年月日
	 * @return
	 * @throws ParseException 
	 */
	public String nowDate()  {
		SimpleDateFormat sfd =new SimpleDateFormat("yyyy-MM-dd");
		Date today =new Date();
		String date = sfd.format(today);
		return date;
	}
}