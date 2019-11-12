/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.applicant.service.impl;

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
import com.adpf.tnpm.applicant.entity.TnpmApplicantAdministrationDutyInfo;
import com.adpf.tnpm.applicant.entity.TnpmApplicantJoinPartyPeopleInfo;
import com.adpf.tnpm.applicant.repository.TnpmApplicantAdministrationDutyInfoRepository;
import com.adpf.tnpm.applicant.repository.TnpmApplicantJoinPartyPeopleInfoRepository;
import com.adpf.tnpm.applicant.service.TnpmApplicantAdministrationDutyInfoService;
import com.adpf.tnpm.applicant.service.TnpmApplicantJoinPartyPeopleInfoService;

@Service
public class TnpmApplicantAdministrationDutyInfoServiceImpl implements TnpmApplicantAdministrationDutyInfoService {
	
    @Autowired
    private TnpmApplicantAdministrationDutyInfoRepository tnpmAdministrationDutyInfoRepository;
    

	@Override
	public JSONObject getList(Pageable page, TnpmApplicantAdministrationDutyInfo tnpmAdministrationDutyInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = tnpmAdministrationDutyInfoRepository.getList(tnpmAdministrationDutyInfo, params, page.getPageNumber(), page.getPageSize());
		int count = tnpmAdministrationDutyInfoRepository.getListCount(tnpmAdministrationDutyInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TnpmApplicantAdministrationDutyInfo tnpmAdministrationDutyInfo = (TnpmApplicantAdministrationDutyInfo) BeanUtils.mapToBean(params, TnpmApplicantAdministrationDutyInfo.class);
		tnpmAdministrationDutyInfoRepository.save(tnpmAdministrationDutyInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TnpmApplicantAdministrationDutyInfo tnpmAdministrationDutyInfo = (TnpmApplicantAdministrationDutyInfo) BeanUtils.mapToBean(params, TnpmApplicantAdministrationDutyInfo.class);
		TnpmApplicantAdministrationDutyInfo tnpmAdministrationDutyInfoInDb = tnpmAdministrationDutyInfoRepository.findOne(tnpmAdministrationDutyInfo.getDutyId());
		if(tnpmAdministrationDutyInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tnpmAdministrationDutyInfo, tnpmAdministrationDutyInfoInDb);
		tnpmAdministrationDutyInfoRepository.save(tnpmAdministrationDutyInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tnpmAdministrationDutyInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
