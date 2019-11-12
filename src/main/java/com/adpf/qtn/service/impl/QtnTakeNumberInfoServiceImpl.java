/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.apache.commons.collections.MapUtils;
import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RedisUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import com.gimplatform.core.utils.StringUtils;

import redis.clients.jedis.Jedis;

import com.adpf.qtn.service.QtnTakeNumberInfoService;
import com.adpf.qtn.util.CallUtil;
import com.adpf.qtn.util.QRCodeUtil;
import com.adpf.qtn.util.RedisUtilsExtend;
import com.adpf.qtn.entity.QtnTakeNumberInfo;
import com.adpf.qtn.entity.QtnTaskHanding;
import com.adpf.qtn.repository.QtnTakeNumberInfoRepository;
import com.adpf.qtn.repository.QtnTaskHandingRepository;
import com.adpf.qtn.repository.QtnWindowInfoRepository;

@Service
public class QtnTakeNumberInfoServiceImpl implements QtnTakeNumberInfoService {

	@Autowired
	private QtnTakeNumberInfoRepository qtnTakeNumberInfoRepository;

	@Autowired
	private QtnTaskHandingRepository qtnTaskHandingRepository;

	@Autowired
	private QtnWindowInfoRepository qtnWindowInfoRepository;

	@Value("${resourceServer.uploadFilePath}")
	private String uploadFilePath;

	@Value("${resourceServer.takeNumberPath}")
	private String takeNumberPath;

	@Override
	public JSONObject getList(Pageable page, QtnTakeNumberInfo qtnQrcodeInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = qtnTakeNumberInfoRepository.getList(qtnQrcodeInfo, params, page.getPageNumber(), page.getPageSize());
		int count = qtnTakeNumberInfoRepository.getListCount(qtnQrcodeInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
		QtnTakeNumberInfo qtnQrcodeInfo = (QtnTakeNumberInfo) BeanUtils.mapToBean(params, QtnTakeNumberInfo.class);
		qtnTakeNumberInfoRepository.save(qtnQrcodeInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
		QtnTakeNumberInfo qtnQrcodeInfo = (QtnTakeNumberInfo) BeanUtils.mapToBean(params, QtnTakeNumberInfo.class);
		QtnTakeNumberInfo qtnQrcodeInfoInDb = qtnTakeNumberInfoRepository.findOne(qtnQrcodeInfo.getId());
		if(qtnQrcodeInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(qtnQrcodeInfo, qtnQrcodeInfoInDb);
		qtnTakeNumberInfoRepository.save(qtnQrcodeInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			qtnTakeNumberInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject getOrgList(Pageable page, Map<String, Object> params) {
		List<Map<String, Object>> list = qtnTakeNumberInfoRepository.getOrgList(params, page.getPageNumber(), page.getPageSize());
		if(list.size() == 1) {
			Map<String,Object> newMap = list.get(0);
			RedisUtilsExtend redis =new RedisUtilsExtend();
			newMap.put("waitNumber", redis.getList(MapUtils.getString(newMap, "organizerId")) == null ? 0 : redis.getList(MapUtils.getString(newMap, "organizerId")).size());
			newMap.put("vipWaitNumber", redis.getList(MapUtils.getString(newMap, "organizerId")+"VIP") == null ? 0 : redis.getList(MapUtils.getString(newMap, "organizerId")+"VIP").size());
			list=new ArrayList<Map<String,Object>>();
			list.add(newMap);
		}
		return RestfulRetUtils.getRetSuccessWithPage(list, list.size());	
	}

	@Override
	public JSONObject createQRCode(Map<String, Object> params) throws Exception {
		QtnTakeNumberInfo qtnTakeNumberInfo = (QtnTakeNumberInfo) BeanUtils.mapToBean(params, QtnTakeNumberInfo.class);
		qtnTakeNumberInfo.setCreateDate(new Date());
		QtnTakeNumberInfo info = qtnTakeNumberInfoRepository.save(qtnTakeNumberInfo);
		String text = takeNumberPath+info.getId();//取号页面
		String reqPath = uploadFilePath + MapUtils.getLong(params, "organizerId")+".jpg";//二维码存放路径
		String path = QRCodeUtil.encode(text, null, reqPath, true);

		//合并两个javabean
		QtnTakeNumberInfo infoDb =qtnTakeNumberInfoRepository.findOne(info.getId());
		info.setQrcodePath(path);
		info.setNumber("000");
		info.setVipNumber("000");
		BeanUtils.mergeBean(info, infoDb);
		QtnTakeNumberInfo lastInfo = qtnTakeNumberInfoRepository.save(infoDb);

		List<String> param = new ArrayList<String>(); 
		param.add("要删除的值");
		//生成二维码时，在redis声明一个存放排号顺序的对象
		RedisUtilsExtend.setList(lastInfo.getOrganizerId().toString(), param,0);
		//生成二维码时，在redis声明一个存放VIP排号顺序的对象
		RedisUtilsExtend.setList(lastInfo.getOrganizerId()+"VIP", param,0);
		RedisUtilsExtend.lpopList(info.getOrganizerId().toString());
		RedisUtilsExtend.lpopList(info.getOrganizerId()+"VIP");

		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject takeNumber(Map<String, Object> params,UserInfo userInfo) {
		synchronized(this){
			JSONObject object =new JSONObject();
			SimpleDateFormat sdf =new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			boolean  ifChange = true;
			boolean haveMax = false;
			//微信取号的情况下判断是否已经取号
			if(StringUtils.isNotBlank((String)params.get("openId"))) {
				//拼接返回信息
				CallUtil util =new CallUtil();
				List<String> statusList = new ArrayList<String>();
				statusList.add("0");
				statusList.add("1");
				List<QtnTaskHanding> waitDataList =  qtnTaskHandingRepository.getListByOpenId((String)params.get("openId"),statusList,util.nowDate());
				if(waitDataList.size() == 1 ) {
					if("0".equals((String)waitDataList.get(0).getStatus()) || "1".equals((String)waitDataList.get(0).getStatus())) {//办理/等待状态下
						for(QtnTaskHanding task : waitDataList) {
							Map<String,Object> orgMap = new HashMap<String,Object>();
							orgMap.put("organizerId", task.getOrganizerId());
							List<Map<String,Object>> list = qtnWindowInfoRepository.getListByOrganizerId(orgMap);
							if(list.size() == 0) {
								return RestfulRetUtils.getErrorMsg("51003", "没有可办理窗口");
							}
							String windowNum = "";
							for(Map<String,Object> m :list) {
								windowNum += m.get("windowNumber") + ",";
								if(StringUtils.isNotBlank((String)m.get("currentNumber"))){
									//判断窗口所在号码有当前openid的号码，有则不需要重新取号
									if(Long.valueOf((String)m.get("currentNumber")) == Long.valueOf(task.getFormatNumber())){
										ifChange = false;
									}
									//判断窗口所在号码比当前openid的号码大，则说明已经顺呼过了
									if(Long.valueOf((String)m.get("currentNumber")) > Long.valueOf(task.getFormatNumber())) {
										haveMax = true;
									}
								}
							}
							//跳出窗口循环后，判断是否需要过期重新取号
							//顺呼过了，并且窗口所在号码不是当前openId的排号，则说明过期要重新取号
							if(ifChange && haveMax) {
								//递归取号
								JSONObject  json = takeNumber(params,userInfo);
								return json;
							}

							if(windowNum.lastIndexOf(",") == windowNum.length()-1) {
								windowNum = windowNum.substring(0,windowNum.length()-1);
							}
							object.put("windowNum",windowNum);
							object.put("number", task.getFormatNumber());
							object.put("createDate", sdf.format(task.getCreateDate()));
							List<String> numberList= RedisUtilsExtend.getList(String.valueOf(task.getOrganizerId()));
							object.put("waitNum", numberList != null ? numberList.size()-1 : 0);
							object.put("RetCode", "0000");
						}
					}
					return  object;
				}
			}
			QtnTakeNumberInfo qtnTakeNumberInfo = (QtnTakeNumberInfo) BeanUtils.mapToBean(params, QtnTakeNumberInfo.class);
			QtnTaskHanding task = (QtnTaskHanding) BeanUtils.mapToBean(params, QtnTaskHanding.class);
			QtnTakeNumberInfo qtnTakeNumberInfoDb = qtnTakeNumberInfoRepository.findOne(qtnTakeNumberInfo.getId());

			if(qtnTakeNumberInfoDb == null){
				return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
			}
			QRCodeUtil qRCodeUtil = new QRCodeUtil();
			//将改营业厅的当前排号 加一
			long newNum = Integer.valueOf(qtnTakeNumberInfoDb.getNumber())+1;
			String newNumber = qRCodeUtil.changeNumber(newNum); 
			qtnTakeNumberInfo.setNumber(newNumber);

			task.setFormatNumber(newNumber);
			task.setCreateDate(new Date());
			task.setStatus("0");
			task.setOrganizerId(qtnTakeNumberInfoDb.getOrganizerId());
			if(userInfo.getUserId() != null) {//后台管理员取号
				task.setUserId(userInfo.getUserId()); 
				task.setUserType("1");
			}else {//微信取号
				task.setOpenId((String)params.get("openId"));
				task.setUserType("0");
			}

			QtnTaskHanding handing = qtnTaskHandingRepository.save(task);

			//合并两个javabean
			BeanUtils.mergeBean(qtnTakeNumberInfo, qtnTakeNumberInfoDb);
			QtnTakeNumberInfo info = qtnTakeNumberInfoRepository.save(qtnTakeNumberInfoDb);

			//普通取号
			RedisUtilsExtend.listAdd(info.getOrganizerId().toString(), info.getNumber());

			if(userInfo.getUserId() != null) {//后台
				return RestfulRetUtils.getRetSuccess(info);
			}else {//微信
				Map<String,Object> newMap = new HashMap<String,Object>();
				newMap.put("organizerId", handing.getOrganizerId());
				List<Map<String,Object>> list = qtnWindowInfoRepository.getListByOrganizerId(newMap);
				String windowNum = "";
				for(Map<String,Object> map :list) {
					windowNum += map.get("windowNumber") + ",";
				}
				if(windowNum.lastIndexOf(",") == windowNum.length()-1) {
					windowNum = windowNum.substring(0,windowNum.length()-1);
				}
				object.put("windowNum", windowNum);
				object.put("number", handing.getFormatNumber());
				object.put("createDate", sdf.format(handing.getCreateDate()));
				List<String> numberList= RedisUtilsExtend.getList(info.getOrganizerId().toString());
				object.put("waitNum", numberList.size()-1);
				object.put("RetCode", "0000");
				return object;
			}
		}
	}

	@Override
	public  JSONObject changeNumber() {
		qtnTakeNumberInfoRepository.changeNumber();
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject initRedis() {
		List<QtnTakeNumberInfo> list =qtnTakeNumberInfoRepository.findAll();
		List<String> param =new ArrayList<String>();
		param.add("要删除的值");
		for(QtnTakeNumberInfo info : list) {
			RedisUtilsExtend.setList(info.getOrganizerId().toString(), param,0);
			RedisUtilsExtend.lpopList(info.getOrganizerId().toString());
			RedisUtilsExtend.setList(info.getOrganizerId()+"VIP", param,0);
			RedisUtilsExtend.lpopList(info.getOrganizerId()+"VIP");

		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject takeVIPNumber(Map<String, Object> params, UserInfo userInfo) {
		synchronized(this) {
			SimpleDateFormat sdf =new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			//微信取号的情况下判断是否已经取号
			QtnTakeNumberInfo qtnTakeNumberInfo = (QtnTakeNumberInfo) BeanUtils.mapToBean(params, QtnTakeNumberInfo.class);
			QtnTaskHanding task = (QtnTaskHanding) BeanUtils.mapToBean(params, QtnTaskHanding.class);
			QtnTakeNumberInfo qtnTakeNumberInfoDb = qtnTakeNumberInfoRepository.findOne(qtnTakeNumberInfo.getId());

			if(qtnTakeNumberInfoDb == null){
				return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
			}
			QRCodeUtil qRCodeUtil = new QRCodeUtil();
			//将改营业厅的当前排号 加一
			long newNum = Integer.valueOf(qtnTakeNumberInfoDb.getVipNumber().substring(1))+1;
			String newNumber = "V"+qRCodeUtil.changeNumber(newNum); 
			qtnTakeNumberInfo.setVipNumber(newNumber);

			task.setFormatNumber(newNumber);
			task.setCreateDate(new Date());
			task.setStatus("0");
			task.setOrganizerId(qtnTakeNumberInfoDb.getOrganizerId());
			//后台管理员取号
			task.setUserId(userInfo.getUserId()); 
			task.setUserType("1");
			QtnTaskHanding handing = qtnTaskHandingRepository.save(task);
			//合并两个javabean
			BeanUtils.mergeBean(qtnTakeNumberInfo, qtnTakeNumberInfoDb);
			QtnTakeNumberInfo info = qtnTakeNumberInfoRepository.save(qtnTakeNumberInfoDb);

			//vip取号
			RedisUtilsExtend.listAdd(info.getOrganizerId()+"VIP", info.getVipNumber());

			return RestfulRetUtils.getRetSuccess(info);
		}
	}
}
