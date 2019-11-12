/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.party.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.tnpm.party.service.TnpmDifficultSituationService;
import com.adpf.tnpm.party.entity.TnpmDifficultSituation;
import com.adpf.tnpm.party.repository.TnpmDifficultSituationRepository;

@Service
public class TnpmDifficultSituationServiceImpl implements TnpmDifficultSituationService {
	
    @Autowired
    private TnpmDifficultSituationRepository tnpmDifficultSituationRepository;

	@Override
	public JSONObject getList(Pageable page, TnpmDifficultSituation tnpmDifficultSituation, Map<String, Object> params) {
		List<Map<String, Object>> list = tnpmDifficultSituationRepository.getList(tnpmDifficultSituation, params, page.getPageNumber(), page.getPageSize());
		int count = tnpmDifficultSituationRepository.getListCount(tnpmDifficultSituation, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TnpmDifficultSituation tnpmDifficultSituation = (TnpmDifficultSituation) BeanUtils.mapToBean(params, TnpmDifficultSituation.class);
		tnpmDifficultSituationRepository.save(tnpmDifficultSituation);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TnpmDifficultSituation tnpmDifficultSituation = (TnpmDifficultSituation) BeanUtils.mapToBean(params, TnpmDifficultSituation.class);
		TnpmDifficultSituation tnpmDifficultSituationInDb = tnpmDifficultSituationRepository.findOne(tnpmDifficultSituation.getId());
		if(tnpmDifficultSituationInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tnpmDifficultSituation, tnpmDifficultSituationInDb);
		tnpmDifficultSituationRepository.save(tnpmDifficultSituationInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tnpmDifficultSituationRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
