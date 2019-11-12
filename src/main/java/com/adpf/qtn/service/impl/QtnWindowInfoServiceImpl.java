/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.service.impl;

import java.util.Date;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;


import com.adpf.qtn.service.QtnWindowInfoService;
import com.adpf.qtn.util.CallUtil;
import com.adpf.qtn.entity.QtnWindowInfo;
import com.adpf.qtn.repository.QtnTaskHandingRepository;
import com.adpf.qtn.repository.QtnWindowInfoRepository;

@Service
public class QtnWindowInfoServiceImpl implements QtnWindowInfoService {

	@Autowired
	private QtnWindowInfoRepository qtnWindowInfoRepository;

	@Autowired
	private QtnTaskHandingRepository qtnTaskHandingRepository;


	@Value("${resourceServer.uploadFilePath}")
	private String uploadFilePath;

	final static String APPID="5bbc1c02",APPKEY="040b6650178ed371da279cc91ea1bceb";	
	final static String url = "http://api.xfyun.cn/v1/service/v1/tts";	
	@Override
	public JSONObject getList(Pageable page, QtnWindowInfo qtnWindowInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = qtnWindowInfoRepository.getList(qtnWindowInfo, params, page.getPageNumber(), page.getPageSize());
		int count = qtnWindowInfoRepository.getListCount(qtnWindowInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
		QtnWindowInfo qtnWindowInfo = (QtnWindowInfo) BeanUtils.mapToBean(params, QtnWindowInfo.class);
		qtnWindowInfo.setCreateDate(new Date());
		qtnWindowInfo.setOrganizerId(userInfo.getOrganizerId());
		qtnWindowInfoRepository.save(qtnWindowInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
		QtnWindowInfo qtnWindowInfo = (QtnWindowInfo) BeanUtils.mapToBean(params, QtnWindowInfo.class);
		qtnWindowInfo.setUserId(userInfo.getUserId());
		QtnWindowInfo qtnWindowInfoInDb = qtnWindowInfoRepository.findOne(qtnWindowInfo.getWindowId());
		if(qtnWindowInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(qtnWindowInfo, qtnWindowInfoInDb);
		qtnWindowInfoRepository.save(qtnWindowInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			qtnWindowInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public  JSONObject call(Map<String, Object> params, UserInfo userInfo) throws Exception {
		synchronized(this){
			QtnWindowInfo windowInfo = (QtnWindowInfo) BeanUtils.mapToBean(params, QtnWindowInfo.class);
			if( windowInfo == null || windowInfo.getWindowId() == null) {
				return RestfulRetUtils.getErrorMsg("51008", "顺呼失败");
			}
			windowInfo = qtnWindowInfoRepository.findOne(windowInfo.getWindowId());
			
			if(windowInfo == null) {
				return RestfulRetUtils.getErrorMsg("51008", "不存在该窗口信息");
			}
			
			if(!windowInfo.getUserId().equals(userInfo.getUserId())) {
				return RestfulRetUtils.getErrorMsg("51008", "请在您关联的窗口工作！");
			}
			//每个用户有绑定机构
			Long organizerId = windowInfo.getOrganizerId();
			CallUtil call = new CallUtil();
			String number= null;
			
			String isVip = MapUtils.getString(params, "isVip");
			if(StringUtils.isBlank(isVip)) {
				return RestfulRetUtils.getErrorMsg("51008", "顺呼失败");
			}
			
			if("0".equals(isVip)) {
				//根据机构取出redis中普通取号队列的第一个号码
				number = call.sequenceCall(organizerId.toString());
			}else if("1".equals(isVip)) {
				//根据机构取出reids中VIP取号队列的第一个号码
				number = call.sequenceCall(organizerId+"VIP");
			}
			if(number == null) {
				return RestfulRetUtils.getErrorMsg("51008", "没有下一个号码");
			}
			
			//将上一个号码状态待处理改为过期
			qtnTaskHandingRepository.changeStatus("3",organizerId, call.nowDate(), windowInfo.getCurrentNumber());
			//修改待办信息
			qtnTaskHandingRepository.changeStartStatus("1", organizerId,call.nowDate(),number,new Date());
			//修改窗口信息
			qtnWindowInfoRepository.changeStatus("1", userInfo.getUserId(),number);
			List<Object> list =new ArrayList<>();
			//生成语音返回文件名
			String path = sendPostAndSave("请"+number+"到"+windowInfo.getWindowNumber()+"号窗口"+",请"+number+"到"+windowInfo.getWindowNumber()+"号窗口",number,windowInfo.getOrganizerId());
			list.add(path);
			list.add(userInfo.getUserId());
			list.add(number);
			return RestfulRetUtils.getRetSuccess(list);
		}
	}

	@Override
	public  JSONObject editNumberAndStatus(Map<String, Object> params, UserInfo userInfo) {
		QtnWindowInfo windowInfo = (QtnWindowInfo) BeanUtils.mapToBean(params, QtnWindowInfo.class);
		if( windowInfo == null || windowInfo.getWindowId() == null) {
			return RestfulRetUtils.getErrorMsg("51008", "结束办理失败");
		}
		windowInfo = qtnWindowInfoRepository.findOne(windowInfo.getWindowId());
		if( windowInfo == null ) {
			return RestfulRetUtils.getErrorMsg("51008", "不存在该窗口信息");
		}
		CallUtil call = new CallUtil();
		//修改待办信息状态
		qtnTaskHandingRepository.changeEndStatus("2", windowInfo.getOrganizerId(),call.nowDate(),windowInfo.getCurrentNumber(),new Date());
		//将窗口状态修改
		qtnWindowInfoRepository.changeStatus("0", userInfo.getUserId(),null);

		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject editUser(Map<String, Object> params, UserInfo userInfo) {

		params.put("userId", userInfo.getUserId());
		List<Map<String,Object>>list =qtnWindowInfoRepository.getList(new QtnWindowInfo(), params, 0, 1);

		if(list.size() == 1 && "1".equals(list.get(0).get("status"))){
			return RestfulRetUtils.getErrorMsg("51006","请先为"+list.get(0).get("windowName")+"结束办理业务");
		}

		QtnWindowInfo qtnWindowInfo = (QtnWindowInfo) BeanUtils.mapToBean(params, QtnWindowInfo.class);
		qtnWindowInfo.setUserId(userInfo.getUserId());
		QtnWindowInfo qtnWindowInfoInDb = qtnWindowInfoRepository.findOne(qtnWindowInfo.getWindowId());
		if(qtnWindowInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}


		//合并两个javabean
		BeanUtils.mergeBean(qtnWindowInfo, qtnWindowInfoInDb);

		//将改用户原来绑定的窗口取消绑定
		qtnWindowInfoRepository.changeUser(userInfo.getUserId());
		qtnWindowInfoRepository.save(qtnWindowInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject changeWindowStatusAndNumber() {
		qtnWindowInfoRepository.changeWindowStatusAndNumber();
		return RestfulRetUtils.getRetSuccess();
	}

	public  String sendPostAndSave(String text,String fileName,Long organizerId)throws Exception{
		String path =null;
		try { 
			Base64 base64 = new Base64(); 
			URL httpUrl = new URL(url);
			String param = "{\"auf\":\"audio/L16;rate=16000\",\"aue\":\"lame\",\"voice_name\":\"xiaoyan\",\"speed\":\"0\",\"volume\":\"80\",\"pitch\":\"50\",\"engine_type\":\"intp65\",\"text_type\":\"text\"}";
			String paramBase64=base64.encodeAsString(param.getBytes("UTF-8"));
			//建立连接
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");
			String currentTimeMillis =System.currentTimeMillis() / 1000L + "";
			String md5Hex = DigestUtils.md5Hex( (APPKEY + currentTimeMillis + paramBase64).getBytes());
			conn.setRequestProperty("X-CurTime", currentTimeMillis);
			conn.setRequestProperty("X-Param",paramBase64);
			conn.setRequestProperty("X-Appid",APPID);
			conn.setRequestProperty("X-CheckSum", md5Hex);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			System.out.println("paramBase64:--------"+paramBase64);
			System.out.println("md5Hex:--------"+md5Hex);
			System.out.println("currentTimeMillis:--------"+currentTimeMillis);
			// 设置请求 body				
			conn.setDoOutput(true);				
			conn.setDoInput(true);								
			//设置连接超时和读取超时时间				
			conn.setConnectTimeout(20000);				
			conn.setReadTimeout(20000);					 
			conn.connect();	 
			//POST请求	            
			OutputStream out = conn.getOutputStream(); 
			out.write(("text="+text).getBytes());	
			out.flush();	 
			//读取响应	            
			BufferedInputStream reader = new BufferedInputStream(conn.getInputStream());	 
			String headerField = conn.getHeaderField("Content-type");	
			System.out.println(headerField);	 
			out.close();	
			path = uploadFilePath+organizerId+fileName+".mp3";
			OutputStream outs = new FileOutputStream(path);
			int size = 0, len = 0;
			byte[] buf = new byte[1024];
			while ((size = reader.read(buf)) != -1) {
				len += size; 
				outs.write(buf, 0, size);
			} 
			outs.close();		 
			reader.close(); 
			conn.disconnect();	
			path = organizerId+fileName+".mp3";
//			RedisUtilsExtend.listAdd(organizerId+"sounds", organizerId+fileName+".mp3");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return path;
	}


} 
