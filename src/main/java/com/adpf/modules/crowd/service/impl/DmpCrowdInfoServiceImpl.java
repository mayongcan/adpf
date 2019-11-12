/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.crowd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.adpf.modules.crowd.entity.DmpCrowdInfo;
import com.adpf.modules.crowd.entity.DmpCrowdNumberInfo;
import com.adpf.modules.crowd.entity.DmpCrowdOperatingLog;
import com.adpf.modules.crowd.repository.DmpCrowdInfoRepository;
import com.adpf.modules.crowd.repository.DmpCrowdNumberInfoRepository;
import com.adpf.modules.crowd.repository.DmpCrowdOperatingLogRepository;
import com.adpf.modules.crowd.service.DmpCrowdInfoService;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

@Service
public class DmpCrowdInfoServiceImpl implements DmpCrowdInfoService {
	
    @Autowired
    private DmpCrowdInfoRepository dmpCrowdInfoRepository;
    
    @Autowired
    private DmpCrowdOperatingLogRepository dmpCrowdOperatingLogRepository;
    
    @Autowired
    private DmpCrowdNumberInfoRepository dmpCrowdNumberInfoRepository;

	@Override
	public JSONObject getList(Pageable page, DmpCrowdInfo dmpCrowdInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = dmpCrowdInfoRepository.getList(dmpCrowdInfo, params, page.getPageNumber(), page.getPageSize());
		//System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD"+list);
		int count = dmpCrowdInfoRepository.getListCount(dmpCrowdInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    DmpCrowdInfo dmpCrowdInfo = (DmpCrowdInfo) BeanUtils.mapToBean(params, DmpCrowdInfo.class);
		dmpCrowdInfo.setCreateDate(new Date());
		dmpCrowdInfoRepository.save(dmpCrowdInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        DmpCrowdInfo dmpCrowdInfo = (DmpCrowdInfo) BeanUtils.mapToBean(params, DmpCrowdInfo.class);
		DmpCrowdInfo dmpCrowdInfoInDb = dmpCrowdInfoRepository.findOne(dmpCrowdInfo.getCrowdId());
		if(dmpCrowdInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(dmpCrowdInfo, dmpCrowdInfoInDb);
		dmpCrowdInfoRepository.save(dmpCrowdInfoInDb);
		
		
		DmpCrowdOperatingLog dmpCrowdOperatingLog = new DmpCrowdOperatingLog();
		//添加修改名称操作记录
		if(StringUtils.isNotEmpty(dmpCrowdInfo.getCrowdName())) {
			dmpCrowdOperatingLog.setLogName("修改人群名称");
			dmpCrowdOperatingLog.setLogDetails("修改人群名称为"+dmpCrowdInfo.getCrowdName());
			dmpCrowdOperatingLog.setCreateDate(new Date());
			dmpCrowdOperatingLog.setCrowdId(dmpCrowdInfo.getCrowdId());
			dmpCrowdOperatingLogRepository.save(dmpCrowdOperatingLog);
		}
		//添加修改描述操作记录
		if(StringUtils.isNotEmpty(dmpCrowdInfo.getCrowdDetails())) {
			dmpCrowdOperatingLog.setLogName("修改人群描述");
			dmpCrowdOperatingLog.setLogDetails("修改人群描述为"+dmpCrowdInfo.getCrowdDetails());
			dmpCrowdOperatingLog.setCreateDate(new Date());
			dmpCrowdOperatingLog.setCrowdId(dmpCrowdInfo.getCrowdId());
			dmpCrowdOperatingLogRepository.save(dmpCrowdOperatingLog);
		}
		
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			dmpCrowdInfoRepository.delete(StringUtils.toLong(ids[i]));
			dmpCrowdNumberInfoRepository.delByCrowdId(ids[i]);
			dmpCrowdOperatingLogRepository.delByCrowdId(ids[i]);
		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject batchAdd(Map<String, Object> params, UserInfo userInfo) {
		List<DmpCrowdInfo> list =new ArrayList();
		for(Map.Entry<String, Object> map:params.entrySet()) {
			DmpCrowdInfo dmpCrowdInfo = (DmpCrowdInfo) BeanUtils.mapToBean((Map)map.getValue(), DmpCrowdInfo.class);
			dmpCrowdInfo.setCreateDate(new Date());
			dmpCrowdInfo.setStatus("2");
			dmpCrowdInfo.setIsJoin("0");
			list.add(dmpCrowdInfo);
		}
		list = dmpCrowdInfoRepository.save(list);
		
		//根据新增的人群添加对应的创建记录
		List<DmpCrowdOperatingLog> logList = new ArrayList();
		for(DmpCrowdInfo dci : list) {
			DmpCrowdOperatingLog operatingLog = new DmpCrowdOperatingLog();
			operatingLog.setCreateDate(dci.getCreateDate());
			operatingLog.setCrowdId(dci.getCrowdId());
			operatingLog.setLogName("创建人群");
			operatingLog.setLogDetails("创建人群"+ dci.getCrowdName());
			logList.add(operatingLog);
		}
		dmpCrowdOperatingLogRepository.save(logList);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject selectByCrowdId(Map<String, Object> params, UserInfo userInfo) {
		List<Map<String,Object>> list = dmpCrowdInfoRepository.selectByCrowdId(new DmpCrowdInfo(), params);
		List<Map<String,Object>> newList = new ArrayList<>();
		for(Map<String,Object> map :list) {
			int txtNum = dmpCrowdNumberInfoRepository.getListCount(new DmpCrowdNumberInfo(),map);
			List<Map<String,Object>> logList = dmpCrowdOperatingLogRepository.selectByCrowdId(new DmpCrowdOperatingLog(), params);
			map.put("txtNum", txtNum);
			map.put("logList", logList);
			newList.add(map);
		}
		return RestfulRetUtils.getRetSuccessWithPage(list, list.size());
	}
}
