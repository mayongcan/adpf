package com.adpf.modules.openam.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class RequestUtil {

	/**
	 * 
	 * @param requestData
	 * @param secret
	 * @return
	 */
	public static String getParamString(Map<String, String> requestData, String secret){
		StringBuilder paramBuilder = new StringBuilder();
		String sign = getSignString(requestData, secret);
		for(String key : requestData.keySet()){
			String value = requestData.get(key);
			paramBuilder.append(key).append("=").append(value).append("&");
		}
		paramBuilder.append("sign=").append(sign);
		
		return paramBuilder.toString();
	}
	
	/**
	 * 
	 * @param requestData
	 * @param secret
	 * @return
	 */
	public static String getSignString(Map<String, String> requestData, String secret){
		String encryptValue = generatePlainText(requestData);
		String signString = HMAC_SHA1.bytesToHexString(HMAC_SHA1.getHmacSHA1(encryptValue, secret));
		return signString;
	}
	
	/**
	 * 参数排序
	 * @param returnData
	 * @return
	 */
	private static String generatePlainText(Map<String,String> returnData){
		//排序参数
		List<Map.Entry<String,String>> mappingList = null;
		mappingList = new ArrayList<Map.Entry<String,String>>(returnData.entrySet()); 
		Collections.sort(mappingList,new Comparator<Map.Entry<String,String>>(){public int compare(
				Map.Entry<String,String> mapping1,
				Map.Entry<String,String> mapping2){ 
				return mapping1.getKey().compareTo(mapping2.getKey()); 
			} 
		}); 
	
		String plainText="";
		for(Map.Entry<String,String> mapping:mappingList){		  
			plainText+=mapping.getValue(); 
		}
		return plainText;
	}
}
