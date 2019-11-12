/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;


import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.tnpm.party.service.TnpmRewardsInfoService;
import com.adpf.tnpm.party.entity.TnpmRewardsInfo;
import com.adpf.tnpm.party.repository.TnpmRewardsInfoRepository;

@Service
public class TnpmRewardsInfoServiceImpl implements TnpmRewardsInfoService {
	
    @Autowired
    private TnpmRewardsInfoRepository tnpmRewardsInfoRepository;

    
	@Override
	public JSONObject getList(Pageable page, TnpmRewardsInfo tnpmRewardsInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = tnpmRewardsInfoRepository.getList(tnpmRewardsInfo, params, page.getPageNumber(), page.getPageSize());
		int count = tnpmRewardsInfoRepository.getListCount(tnpmRewardsInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TnpmRewardsInfo tnpmRewardsInfo = (TnpmRewardsInfo) BeanUtils.mapToBean(params, TnpmRewardsInfo.class);
		tnpmRewardsInfoRepository.save(tnpmRewardsInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TnpmRewardsInfo tnpmRewardsInfo = (TnpmRewardsInfo) BeanUtils.mapToBean(params, TnpmRewardsInfo.class);
		TnpmRewardsInfo tnpmRewardsInfoInDb = tnpmRewardsInfoRepository.findOne(tnpmRewardsInfo.getRewardsId());
		if(tnpmRewardsInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tnpmRewardsInfo, tnpmRewardsInfoInDb);
		tnpmRewardsInfoRepository.save(tnpmRewardsInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			TnpmRewardsInfo info = tnpmRewardsInfoRepository.findOne(Long.valueOf(ids[i]));
			if(info != null) {
				String [] paths = info.getRewardsFile().split(",");
				for(String path :paths) {
					File file =new File ("E:\\work_space\\project\\adpf\\adpf-web\\WebContent\\tnpmImage\\"+path);
					file.delete();
				}
			}
			tnpmRewardsInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
