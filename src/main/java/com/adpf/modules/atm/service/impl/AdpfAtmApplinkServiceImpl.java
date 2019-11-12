/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.atm.service.impl;

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
import com.adpf.modules.atm.entity.AdpfAtmApplink;
import com.adpf.modules.atm.repository.AdpfAtmApplinkRepository;
import com.adpf.modules.atm.service.AdpfAtmApplinkService;

@Service
public class AdpfAtmApplinkServiceImpl implements AdpfAtmApplinkService {
	
    @Autowired
    private AdpfAtmApplinkRepository adpfAtmApplinkRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfAtmApplink adpfAtmApplink, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfAtmApplinkRepository.getList(adpfAtmApplink, params, page.getPageNumber(), page.getPageSize());
		int count = adpfAtmApplinkRepository.getListCount(adpfAtmApplink, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdpfAtmApplink adpfAtmApplink = (AdpfAtmApplink) BeanUtils.mapToBean(params, AdpfAtmApplink.class);
		adpfAtmApplink.setIsValid(Constants.IS_VALID_VALID);
		adpfAtmApplink.setCreateBy(userInfo.getUserId());
		adpfAtmApplink.setCreateDate(new Date());
		adpfAtmApplinkRepository.save(adpfAtmApplink);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdpfAtmApplink adpfAtmApplink = (AdpfAtmApplink) BeanUtils.mapToBean(params, AdpfAtmApplink.class);
		AdpfAtmApplink adpfAtmApplinkInDb = adpfAtmApplinkRepository.findOne(adpfAtmApplink.getId());
		if(adpfAtmApplinkInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfAtmApplink, adpfAtmApplinkInDb);
		adpfAtmApplinkRepository.save(adpfAtmApplinkInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		//判断是否需要移除
		List<Long> idList = new ArrayList<Long>();
		for (int i = 0; i < ids.length; i++) {
			idList.add(StringUtils.toLong(ids[i]));
		}
		//批量更新（设置IsValid 为N）
		if(idList.size() > 0){
			adpfAtmApplinkRepository.delEntity(Constants.IS_VALID_INVALID, idList);
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
