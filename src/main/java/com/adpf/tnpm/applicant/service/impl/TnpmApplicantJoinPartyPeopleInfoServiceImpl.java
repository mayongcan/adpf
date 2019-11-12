/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.applicant.service.impl;

import java.util.Date;
import java.io.File;
import java.math.BigDecimal;
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
import com.adpf.tnpm.applicant.entity.TnpmApplicantAdministrationDutyInfo;
import com.adpf.tnpm.applicant.entity.TnpmApplicantFamilyInfo;
import com.adpf.tnpm.applicant.entity.TnpmApplicantJoinPartyPeopleInfo;
import com.adpf.tnpm.applicant.entity.TnpmApplicantRewardsInfo;
import com.adpf.tnpm.applicant.entity.TnpmApplicantTrainDevelopInfo;
import com.adpf.tnpm.applicant.repository.TnpmApplicantAdministrationDutyInfoRepository;
import com.adpf.tnpm.applicant.repository.TnpmApplicantFamilyInfoRepository;
import com.adpf.tnpm.applicant.repository.TnpmApplicantJoinPartyPeopleInfoRepository;
import com.adpf.tnpm.applicant.repository.TnpmApplicantRewardsInfoRepository;
import com.adpf.tnpm.applicant.repository.TnpmApplicantTrainDevelopInfoRepository;

@Service
public class TnpmApplicantJoinPartyPeopleInfoServiceImpl implements TnpmApplicantJoinPartyPeopleInfoService {
	
    @Autowired
    private TnpmApplicantJoinPartyPeopleInfoRepository tnpmJoinPartyPeopleInfoRepository;
    
    @Autowired
    private TnpmApplicantTrainDevelopInfoRepository tnpmTrainDevelopInfoRepository; 

    @Autowired
    private TnpmApplicantFamilyInfoRepository tnpmFamilyInfoRepository ;
    
    @Autowired
    private TnpmApplicantAdministrationDutyInfoRepository tnpmAdministrationDutyInfoRepository;
    
    @Autowired
    private TnpmApplicantRewardsInfoRepository tnpmRewardsInfoRepository;
    
	@Override
	public JSONObject getList(Pageable page, TnpmApplicantJoinPartyPeopleInfo tnpmJoinPartyPeopleInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = tnpmJoinPartyPeopleInfoRepository.getList(tnpmJoinPartyPeopleInfo, params, page.getPageNumber(), page.getPageSize());
		int count = tnpmJoinPartyPeopleInfoRepository.getListCount(tnpmJoinPartyPeopleInfo, params);
		
		for(Map<String,Object> map : list) {
			String num =(String)map.get("infoCompletion");
			int number = Integer.valueOf(num.replace("%","").trim());
			if(number != 100) {
				number =20;
				params.put("peopleId", map.get("peopleId"));
				//判断扩展信息是否添加了
				if( map.get("nativePlace") != null || !"".equals((String)map.get("nativePlace"))){
					number +=16;
				}
				//判断职务是否存在
				if(tnpmAdministrationDutyInfoRepository.getList(new TnpmApplicantAdministrationDutyInfo(), params, 0,1).size() != 0) {
					number += 16;
				}
				//判断奖惩是否存在
				if(tnpmRewardsInfoRepository.getList(new TnpmApplicantRewardsInfo(), params, 0,1).size() != 0) {
					number += 16;
				}
				//判断家庭成员是否存在
				if(tnpmFamilyInfoRepository.getList(new TnpmApplicantFamilyInfo(), params, 0,1).size() != 0) {
					number += 16;
				}
				if(tnpmTrainDevelopInfoRepository.getList(new TnpmApplicantTrainDevelopInfo(), params, 0,1).size() != 0) {
					number += 16;
				}
				
				map.put("infoCompletion",number+"%");
				
				TnpmApplicantJoinPartyPeopleInfo info1= (TnpmApplicantJoinPartyPeopleInfo) BeanUtils.mapToBean(map, TnpmApplicantJoinPartyPeopleInfo.class);
				BigDecimal peopleId =(BigDecimal)map.get("peopleId");
				TnpmApplicantJoinPartyPeopleInfo info2 = tnpmJoinPartyPeopleInfoRepository.findOne(peopleId.longValue());
				//合并两个javabean
				BeanUtils.mergeBean(info1, info2);
				TnpmApplicantJoinPartyPeopleInfo info = tnpmJoinPartyPeopleInfoRepository.save(info2);
				
			}
		}
		list = tnpmJoinPartyPeopleInfoRepository.getList(tnpmJoinPartyPeopleInfo, params, page.getPageNumber(), page.getPageSize());
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TnpmApplicantJoinPartyPeopleInfo tnpmJoinPartyPeopleInfo = (TnpmApplicantJoinPartyPeopleInfo) BeanUtils.mapToBean(params, TnpmApplicantJoinPartyPeopleInfo.class);
		TnpmApplicantJoinPartyPeopleInfo info =tnpmJoinPartyPeopleInfoRepository.save(tnpmJoinPartyPeopleInfo);
		return RestfulRetUtils.getRetSuccess(info);
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TnpmApplicantJoinPartyPeopleInfo tnpmJoinPartyPeopleInfo = (TnpmApplicantJoinPartyPeopleInfo) BeanUtils.mapToBean(params, TnpmApplicantJoinPartyPeopleInfo.class);
		TnpmApplicantJoinPartyPeopleInfo tnpmJoinPartyPeopleInfoInDb = tnpmJoinPartyPeopleInfoRepository.findOne(tnpmJoinPartyPeopleInfo.getPeopleId());
		if(tnpmJoinPartyPeopleInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tnpmJoinPartyPeopleInfo, tnpmJoinPartyPeopleInfoInDb);
		TnpmApplicantJoinPartyPeopleInfo info = tnpmJoinPartyPeopleInfoRepository.save(tnpmJoinPartyPeopleInfoInDb);
		return RestfulRetUtils.getRetSuccess(info);
	}

	@Override
	public JSONObject del(Long peopleId, UserInfo userInfo) {
		tnpmJoinPartyPeopleInfoRepository.delEntity(peopleId);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject updateImg(Map<String, Object> params, UserInfo userInfo) {
		TnpmApplicantJoinPartyPeopleInfo tnpmJoinPartyPeopleInfo = (TnpmApplicantJoinPartyPeopleInfo) BeanUtils.mapToBean(params, TnpmApplicantJoinPartyPeopleInfo.class);
		TnpmApplicantJoinPartyPeopleInfo tnpmJoinPartyPeopleInfoInDb = tnpmJoinPartyPeopleInfoRepository.findOne(tnpmJoinPartyPeopleInfo.getPeopleId());
		if(StringUtils.isNotBlank(tnpmJoinPartyPeopleInfoInDb.getImage())) {
			File file =new File((String) params.get("uploadFilePath")+tnpmJoinPartyPeopleInfoInDb.getImage());
			file.delete();
		}
		BeanUtils.mergeBean(tnpmJoinPartyPeopleInfo, tnpmJoinPartyPeopleInfoInDb);
		TnpmApplicantJoinPartyPeopleInfo info = tnpmJoinPartyPeopleInfoRepository.save(tnpmJoinPartyPeopleInfoInDb);
		return RestfulRetUtils.getRetSuccess(info);
	}

	@Override
	public JSONObject getDetails(Map<String, Object> params, UserInfo userInfo) {
		Integer peopleId = (Integer)params.get("peopleId");
		TnpmApplicantJoinPartyPeopleInfo info =tnpmJoinPartyPeopleInfoRepository.findOne(peopleId.longValue());
		Map<String,Object> map = BeanUtils.beanToMap(info);
		List<Map<String,Object>> list =tnpmTrainDevelopInfoRepository.getList(new TnpmApplicantTrainDevelopInfo(), params, 0, 1);
		if(list.size() == 1) {
			map.put("develop", list.get(0));
		}
		return RestfulRetUtils.getRetSuccess(map);
	}

}
