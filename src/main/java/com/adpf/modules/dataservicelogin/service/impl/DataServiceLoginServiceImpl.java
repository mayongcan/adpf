/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.dataservicelogin.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.dataservicelogin.service.DataServiceLoginService;
import com.adpf.modules.gdtm.util.MD5Util;
import com.adpf.modules.dataservicelogin.entity.DataServiceLogin;
import com.adpf.modules.dataservicelogin.repository.DataServiceLoginRepository;

@Service
public class DataServiceLoginServiceImpl implements DataServiceLoginService {
	
    @Autowired
    private DataServiceLoginRepository dataServiceLoginRepository;

	@Override
	public JSONObject getList(Pageable page, DataServiceLogin dataServiceLogin, Map<String, Object> params) {
		List<Map<String, Object>> list = dataServiceLoginRepository.getList(dataServiceLogin, params, page.getPageNumber(), page.getPageSize());
		int count = dataServiceLoginRepository.getListCount(dataServiceLogin, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params) {
		String password = (String)params.get("password");
		String passMd5 = MD5Util.MD5(password+"adpf");
		params.put("password", passMd5);
	    DataServiceLogin dataServiceLogin = (DataServiceLogin) BeanUtils.mapToBean(params, DataServiceLogin.class);
		dataServiceLoginRepository.save(dataServiceLogin);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        DataServiceLogin dataServiceLogin = (DataServiceLogin) BeanUtils.mapToBean(params, DataServiceLogin.class);
		DataServiceLogin dataServiceLoginInDb = dataServiceLoginRepository.findOne(dataServiceLogin.getId());
		if(dataServiceLoginInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(dataServiceLogin, dataServiceLoginInDb);
		dataServiceLoginRepository.save(dataServiceLoginInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			dataServiceLoginRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject userInfo(Map<String, Object> params) {
		List<Map<String, Object>> list = dataServiceLoginRepository.getUserInfo((String)params.get("username"));
		//用户名查询为空
		if(null==list || list.isEmpty())
			return RestfulRetUtils.getErrorMsg("100005", "用户名错误");
		return RestfulRetUtils.getRetSuccess(list);
	}

	@Override
	public JSONObject register(Map<String, Object> params) {
		String username = (String)params.get("username");
		if(null==username)
			return RestfulRetUtils.getErrorMsg("100009", "用户名为空,请输入用户名！");
		List<Map<String, Object>> list = dataServiceLoginRepository.getUserInfo(username);
		//用户不为空
		if(!list.isEmpty())
			return RestfulRetUtils.getErrorMsg("100007", "用户名已存在");
		String password = (String)params.get("password");
		//密码和确定密码不一致
		if(null==password || null==(String)params.get("password1"))
			return RestfulRetUtils.getErrorMsg("100010", "密码为空");
		if(!params.get("password").equals(params.get("password1")))
			return RestfulRetUtils.getErrorMsg("100008", "密码不一致");
		//username+password 生成唯一appkey
		params.put("appkey", MD5Util.MD5(username+password));
		String passMd5 = MD5Util.MD5(password+"adpf");
		params.put("password", passMd5);
	    DataServiceLogin dataServiceLogin = (DataServiceLogin) BeanUtils.mapToBean(params, DataServiceLogin.class);
		dataServiceLoginRepository.save(dataServiceLogin);
		return RestfulRetUtils.getRetSuccess();
	}

}
