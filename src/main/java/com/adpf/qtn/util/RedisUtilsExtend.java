package com.adpf.qtn.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gimplatform.core.utils.RedisUtils;

import redis.clients.jedis.Jedis;

public class RedisUtilsExtend extends RedisUtils{
	
	private static Logger logger  = LoggerFactory.getLogger(RedisUtilsExtend.class);
	
	/**
	 * 取list中先添加的第一个元素并在list中删除
	 * @param key
	 * @return
	 */
	public static String lpopList(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.lpop(key);
                logger.debug("lpopList ", key, value);
            }
        } catch (Exception e) {
            logger.warn("lpopList", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }
	
	/**
	 * lrange方法，从list中，根据start ，end获取多个值
	 * @param key redis中的key
	 * @param start 开始下标 （0开始）
	 * @param end 结束下标（取出的包含这个下标元素的值）
	 * @return
	 */
	
	 public static List<String> lrangeList(String key,long start,long end){
		 List<String> list = null;
		 Jedis jedis =null;
		 try {
			jedis = getResource();
			if(jedis.exists(key)) {
				list=jedis.lrange(key, start, end);
				logger.debug("lrangeList",key,list);
			}
		 }catch(Exception e) {
			 logger.warn("lrangeList",key,list,e);
		 }finally {
			 returnResource(jedis);
		 }
		 return list;
	 }
	
}
