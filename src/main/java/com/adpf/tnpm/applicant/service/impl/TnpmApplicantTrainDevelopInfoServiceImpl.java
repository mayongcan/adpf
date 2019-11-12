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
import com.adpf.tnpm.applicant.service.TnpmApplicantJoinPartyPeopleInfoService;
import com.adpf.tnpm.applicant.service.TnpmApplicantTrainDevelopInfoService;
import com.adpf.tnpm.applicant.entity.TnpmApplicantTrainDevelopInfo;
import com.adpf.tnpm.applicant.repository.TnpmApplicantTrainDevelopInfoRepository;

@Service
public class TnpmApplicantTrainDevelopInfoServiceImpl implements TnpmApplicantTrainDevelopInfoService {
	
    @Autowired
    private TnpmApplicantTrainDevelopInfoRepository tnpmTrainDevelopInfoRepository;
    

	@Override
	public JSONObject getList(Pageable page, TnpmApplicantTrainDevelopInfo tnpmTrainDevelopInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = tnpmTrainDevelopInfoRepository.getList(tnpmTrainDevelopInfo, params, page.getPageNumber(), page.getPageSize());
		int count = tnpmTrainDevelopInfoRepository.getListCount(tnpmTrainDevelopInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TnpmApplicantTrainDevelopInfo tnpmTrainDevelopInfo = (TnpmApplicantTrainDevelopInfo) BeanUtils.mapToBean(params, TnpmApplicantTrainDevelopInfo.class);
		tnpmTrainDevelopInfoRepository.save(tnpmTrainDevelopInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TnpmApplicantTrainDevelopInfo tnpmTrainDevelopInfo = (TnpmApplicantTrainDevelopInfo) BeanUtils.mapToBean(params, TnpmApplicantTrainDevelopInfo.class);
		TnpmApplicantTrainDevelopInfo tnpmTrainDevelopInfoInDb = tnpmTrainDevelopInfoRepository.findOne(tnpmTrainDevelopInfo.getDevelopId());
		if(tnpmTrainDevelopInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tnpmTrainDevelopInfo, tnpmTrainDevelopInfoInDb);
		TnpmApplicantTrainDevelopInfo info =tnpmTrainDevelopInfoRepository.save(tnpmTrainDevelopInfoInDb);
		return RestfulRetUtils.getRetSuccess(info);
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tnpmTrainDevelopInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
