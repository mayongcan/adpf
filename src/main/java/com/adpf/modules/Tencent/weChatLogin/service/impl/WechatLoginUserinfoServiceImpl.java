/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.Tencent.weChatLogin.service.impl;

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

import com.adpf.modules.Tencent.weChatLogin.service.WechatLoginUserinfoService;
import com.adpf.modules.Tencent.weChatLogin.entity.WechatLoginUserinfo;
import com.adpf.modules.Tencent.weChatLogin.repository.WechatLoginUserinfoRepository;

@Service
public class WechatLoginUserinfoServiceImpl implements WechatLoginUserinfoService {
	
    @Autowired
    private WechatLoginUserinfoRepository wechatLoginUserinfoRepository;

	@Override
	public JSONObject getList(Pageable page, WechatLoginUserinfo wechatLoginUserinfo, Map<String, Object> params) {
		List<Map<String, Object>> list = wechatLoginUserinfoRepository.getList(wechatLoginUserinfo, params, page.getPageNumber(), page.getPageSize());
		int count = wechatLoginUserinfoRepository.getListCount(wechatLoginUserinfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    WechatLoginUserinfo wechatLoginUserinfo = (WechatLoginUserinfo) BeanUtils.mapToBean(params, WechatLoginUserinfo.class);
		wechatLoginUserinfoRepository.save(wechatLoginUserinfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        WechatLoginUserinfo wechatLoginUserinfo = (WechatLoginUserinfo) BeanUtils.mapToBean(params, WechatLoginUserinfo.class);
		WechatLoginUserinfo wechatLoginUserinfoInDb = wechatLoginUserinfoRepository.findOne(wechatLoginUserinfo.getId());
		if(wechatLoginUserinfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(wechatLoginUserinfo, wechatLoginUserinfoInDb);
		wechatLoginUserinfoRepository.save(wechatLoginUserinfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}
	
	@Override
	public JSONObject queryUser(Map<String, Object> params) {
		List<Map<String, Object>> list = wechatLoginUserinfoRepository.findOneByOpenId(params);
		WechatLoginUserinfo wechatLoginUserinfoSave = null;
		if(list.size()==0) {
			WechatLoginUserinfo wechatLoginUserinfo = (WechatLoginUserinfo) BeanUtils.mapToBean(params, WechatLoginUserinfo.class);
			wechatLoginUserinfoSave = wechatLoginUserinfoRepository.save(wechatLoginUserinfo);
//			String userInfo = JSONObject.toJSONString(wechatLoginUserinfoSave);
//			json = JSONObject.parseObject(userInfo);
		}else {
			return RestfulRetUtils.getRetSuccess(list);
		}
		return RestfulRetUtils.getRetSuccess(wechatLoginUserinfoSave);
	}
	
	@Override
	public JSONObject queryID(Map<String, Object> params) {
		List<Map<String, Object>> list = wechatLoginUserinfoRepository.findOneById(params);
		if(list.size()==0){
			return RestfulRetUtils.getErrorMsg("500021", "为空");
		}
		return RestfulRetUtils.getRetSuccess(list);
	}
	
	@Override
	public JSONObject userInfo(Map<String, Object> params) {
        WechatLoginUserinfo wechatLoginUserinfo = (WechatLoginUserinfo) BeanUtils.mapToBean(params, WechatLoginUserinfo.class);
		WechatLoginUserinfo wechatLoginUserinfoInDb = wechatLoginUserinfoRepository.findOne(wechatLoginUserinfo.getId());
		if(wechatLoginUserinfoInDb == null){
			System.out.println("kong");
		}else{
			System.out.println(wechatLoginUserinfoInDb);
		}
		return RestfulRetUtils.getRetSuccess(wechatLoginUserinfoInDb);
	}
	

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			wechatLoginUserinfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
