/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.service.OrganizerInfoService;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import lombok.Synchronized;

import com.adpf.qtn.service.QtnTaskHandingService;
import com.adpf.qtn.util.CallUtil;
import com.adpf.qtn.util.QRCodeUtil;
import com.adpf.qtn.util.RedisUtilsExtend;
import com.adpf.qtn.entity.QtnTakeNumberInfo;
import com.adpf.qtn.entity.QtnTaskHanding;
import com.adpf.qtn.repository.QtnTaskHandingRepository;
import com.adpf.qtn.repository.QtnWindowInfoRepository;

@Service
public class QtnTaskHandingServiceImpl implements QtnTaskHandingService {
	
    @Autowired
    private QtnTaskHandingRepository qtnTaskHandingRepository;
    
    @Autowired
    private QtnWindowInfoRepository qtnWindowInfoRepository;
    
	@Override
	public JSONObject getList(UserInfo userInfo,Pageable page, QtnTaskHanding qtnTaskHanding, Map<String, Object> params) {
		if(params.get("organizerId") == null) {
			return RestfulRetUtils.getErrorMsg("51001", "查询失败");
		}
		CallUtil callUtil =new CallUtil();
		params.put("today",callUtil.nowDate());
		List<Map<String, Object>> list = qtnTaskHandingRepository.getList(qtnTaskHanding, params, page.getPageNumber(), page.getPageSize());
		int count = qtnTaskHandingRepository.getListCount(qtnTaskHanding, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    QtnTaskHanding qtnTaskHanding = (QtnTaskHanding) BeanUtils.mapToBean(params, QtnTaskHanding.class);
		qtnTaskHanding.setCreateDate(new Date());
		qtnTaskHandingRepository.save(qtnTaskHanding);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        QtnTaskHanding qtnTaskHanding = (QtnTaskHanding) BeanUtils.mapToBean(params, QtnTaskHanding.class);
		QtnTaskHanding qtnTaskHandingInDb = qtnTaskHandingRepository.findOne(qtnTaskHanding.getTaskId());
		if(qtnTaskHandingInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(qtnTaskHanding, qtnTaskHandingInDb);
		qtnTaskHandingRepository.save(qtnTaskHandingInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			qtnTaskHandingRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject getWaitList(Pageable pageable,Map<String, Object> params, UserInfo userInfo) {
		params.put("organizerId", userInfo.getOrganizerId());
		List<Map<String, Object>> list = qtnWindowInfoRepository.getListByOrganizerId(params);
		List<Long> num = new ArrayList<Long>();
		List<String> numList = new ArrayList<String>();
		//取出所有窗口当前号
		for(Map<String,Object> map :list) {
			if(StringUtils.isNotBlank((String)map.get("currentNumber"))) {
				num.add(Long.valueOf((String)map.get("currentNumber")));
				numList.add((String)map.get("currentNumber"));
			}
		}
		QRCodeUtil qrCodeUtil =new QRCodeUtil();
		CallUtil callUtil =new CallUtil();
		if(numList.size() != 0) {//当前窗口有人在办理
			Collections.sort(numList);
			long number =num.get(0);
//			for(int i =0;i<6-num.size();i++) {
//				numList.add(qrCodeUtil.changeNumber(Integer.valueOf(numList.get(numList.size()-1))+1));
//			}
			List<QtnTaskHanding> dataList = qtnTaskHandingRepository.getDataInList(numList,callUtil.nowDate(),userInfo.getOrganizerId());
			
			List<Map<String,Object>> newDataList = new ArrayList<Map<String,Object>>();
			Map<String,Object> map =null;
			for(QtnTaskHanding task : dataList) {
				map = new HashMap<String,Object>();
				map.put("formatNumber", task.getFormatNumber());
				map.put("organizerId", task.getOrganizerId());
				map.put("status", task.getStatus());
				map.put("isVip", task.getIsVip());
				 if("1".equals(task.getStatus())) {
					for(Map<String,Object> newMap :list) {
						if(task.getFormatNumber().equals((String)newMap.get("currentNumber"))) {
							map.put("windowName", newMap.get("windowName"));
						}
					}
				}
				newDataList.add(map);
			}
			return RestfulRetUtils.getRetSuccess(newDataList);
		}else {//所有窗口都是没人的
			
			params.put("today",callUtil.nowDate());
			params.put("status", "0");
			List<Map<String, Object>> newList =  qtnTaskHandingRepository.getWaitList(new QtnTaskHanding(), params, 0, 6);
			return RestfulRetUtils.getRetSuccess(newList);
		}
	}

}
