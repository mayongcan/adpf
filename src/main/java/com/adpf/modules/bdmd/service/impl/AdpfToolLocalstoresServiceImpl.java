/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.bdmd.service.impl;

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
import com.adpf.modules.bdmd.service.AdpfToolLocalstoresService;
import com.adpf.modules.bdmd.entity.AdpfToolLocalstores;
import com.adpf.modules.bdmd.repository.AdpfToolLocalstoresRepository;

@Service
public class AdpfToolLocalstoresServiceImpl implements AdpfToolLocalstoresService {
	
    @Autowired
    private AdpfToolLocalstoresRepository adpfToolLocalstoresRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfToolLocalstores adpfToolLocalstores, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfToolLocalstoresRepository.getList(adpfToolLocalstores, params, page.getPageNumber(), page.getPageSize());
		int count = adpfToolLocalstoresRepository.getListCount(adpfToolLocalstores, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdpfToolLocalstores adpfToolLocalstores = (AdpfToolLocalstores) BeanUtils.mapToBean(params, AdpfToolLocalstores.class);
		adpfToolLocalstoresRepository.save(adpfToolLocalstores);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdpfToolLocalstores adpfToolLocalstores = (AdpfToolLocalstores) BeanUtils.mapToBean(params, AdpfToolLocalstores.class);
		AdpfToolLocalstores adpfToolLocalstoresInDb = adpfToolLocalstoresRepository.findOne(adpfToolLocalstores.getStoreId());
		if(adpfToolLocalstoresInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfToolLocalstores, adpfToolLocalstoresInDb);
		adpfToolLocalstoresRepository.save(adpfToolLocalstoresInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfToolLocalstoresRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
