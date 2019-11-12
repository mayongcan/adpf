/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.service.impl;

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

import com.adpf.tracking.service.TPromoService;
import com.adpf.tracking.entity.TPromo;
import com.adpf.tracking.repository.TPromoRepository;

@Service
public class TPromoServiceImpl implements TPromoService {
	
    @Autowired
    private TPromoRepository tPromoRepository;

	@Override
	public JSONObject getList(Pageable page, TPromo tPromo, Map<String, Object> params) {
		List<Map<String, Object>> list = tPromoRepository.getList(tPromo, params, page.getPageNumber(), page.getPageSize());
		int count = tPromoRepository.getListCount(tPromo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    TPromo tPromo = (TPromo) BeanUtils.mapToBean(params, TPromo.class);
	    tPromo.setCreateTime(new Date());
	    String id =tPromoRepository.getValueId();
	    String channelType = tPromoRepository.getChannelType(tPromo.getChannelName());
	    if("通用接口上报".equals(channelType)){
	    	tPromo.setClickUrl(params.get("clickUrl")+"/api/adpf/tk/"+id+"?os={OS}&ip={IP}&idfa={IDFA}&imei={IMEI}&imei_md5={IMEI_MD5}&androidid={ANDROIDID}&androidid_md5={ANDROIDID_MD5}&mac={MAC}&ua={UA}&callback={CALLBACK}");
	    }else if ("通用短链".equals(channelType)) {
	    	tPromo.setClickUrl(params.get("clickUrl")+"/api/adpf/gl/"+id);
		}	    
		tPromoRepository.save(tPromo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        TPromo tPromo = (TPromo) BeanUtils.mapToBean(params, TPromo.class);
		TPromo tPromoInDb = tPromoRepository.findOne(tPromo.getId());
		if(tPromoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(tPromo, tPromoInDb);
		tPromoRepository.save(tPromoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			tPromoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
