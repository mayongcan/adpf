/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.issue.service.impl;

import com.adpf.issue.entity.AdvIssue;
import com.adpf.issue.repository.AdvIssueRepository;
import com.adpf.issue.service.AdvIssueService;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.repository.RoleInfoRepository;
import com.gimplatform.core.repository.UserInfoRepository;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdvIssueServiceImpl implements AdvIssueService {
	
    @Autowired
    private AdvIssueRepository advIssueRepository;
	@Autowired
	private RoleInfoRepository roleInfoRepository;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Override
	public JSONObject getList(Pageable page, AdvIssue advIssue, Map<String, Object> params) {

		UserInfo userInfo = (UserInfo) MapUtils.getObject(params, "userInfo");
		List<String> userRoleName = roleInfoRepository.getUserRoleName(userInfo.getUserCode(), userInfo.getOrganizerId());
		if(userRoleName.get(0).equals("代理商")||userRoleName.get(0).equals("二级代理商")){
			params.put("differenceid",userInfo.getUserId());
		}
		String userCode=params.get("advid").toString();
		if(!userCode.equals("")&& userCode!=null){
			List<UserInfo> advLoginid = userInfoRepository.findByUserCode(userCode);
			params.put("advid", advLoginid.get(0).getUserId());
			params.put("differenceid",0);
		}
		//如果是企业登录，覆盖advid
		if(userRoleName.get(0).equals("企业")){
			params.put("advid",userInfo.getUserId());
			params.put("differenceid",0);
		}

		List<Map<String, Object>> list = advIssueRepository.getList(advIssue, params, page.getPageNumber(), page.getPageSize());
		int count = advIssueRepository.getListCount(advIssue, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdvIssue advIssue = (AdvIssue) BeanUtils.mapToBean(params, AdvIssue.class);
		advIssueRepository.save(advIssue);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdvIssue advIssue = (AdvIssue) BeanUtils.mapToBean(params, AdvIssue.class);
		AdvIssue advIssueInDb = advIssueRepository.findOne(advIssue.getId());
		if(advIssueInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(advIssue, advIssueInDb);
		advIssueRepository.save(advIssueInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			advIssueRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
