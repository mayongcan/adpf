/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.atm.service.impl;

import java.util.Date;
import java.text.DecimalFormat;
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
import com.gimplatform.core.utils.RedisUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;
import com.adpf.modules.atm.entity.AdpfAtmAppdefine;
import com.adpf.modules.atm.repository.AdpfAtmAppdefineRepository;
import com.adpf.modules.atm.service.AdpfAtmAppdefineService;

@Service
public class AdpfAtmAppdefineServiceImpl implements AdpfAtmAppdefineService {

	@Autowired
	private AdpfAtmAppdefineRepository adpfAtmAppdefineRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfAtmAppdefine adpfAtmAppdefine, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfAtmAppdefineRepository.getList(adpfAtmAppdefine, params,
				page.getPageNumber(), page.getPageSize());
		int count = adpfAtmAppdefineRepository.getListCount(adpfAtmAppdefine, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
		AdpfAtmAppdefine adpfAtmAppdefine = (AdpfAtmAppdefine) BeanUtils.mapToBean(params, AdpfAtmAppdefine.class);
		adpfAtmAppdefine.setIsValid(Constants.IS_VALID_VALID);
		adpfAtmAppdefine.setCount(0L);
		adpfAtmAppdefine.setCreateBy(userInfo.getUserId());
		adpfAtmAppdefine.setCreateDate(new Date());
		adpfAtmAppdefineRepository.save(adpfAtmAppdefine);
		DecimalFormat df = new DecimalFormat("00000000");
		adpfAtmAppdefine.setCode(df.format(adpfAtmAppdefine.getId()));
		adpfAtmAppdefineRepository.save(adpfAtmAppdefine);

		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
		AdpfAtmAppdefine adpfAtmAppdefine = (AdpfAtmAppdefine) BeanUtils.mapToBean(params, AdpfAtmAppdefine.class);
		AdpfAtmAppdefine adpfAtmAppdefineInDb = adpfAtmAppdefineRepository.findOne(adpfAtmAppdefine.getId());
		if (adpfAtmAppdefineInDb == null) {
			return RestfulRetUtils.getErrorMsg("51006", "当前编辑的对象不存在");
		}
		// 合并两个javabean
		BeanUtils.mergeBean(adpfAtmAppdefine, adpfAtmAppdefineInDb);
		adpfAtmAppdefineRepository.save(adpfAtmAppdefineInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		// 判断是否需要移除
		List<Long> idList = new ArrayList<Long>();
		for (int i = 0; i < ids.length; i++) {
			idList.add(StringUtils.toLong(ids[i]));
		}
		// 批量更新（设置IsValid 为N）
		if (idList.size() > 0) {
			adpfAtmAppdefineRepository.delEntity(Constants.IS_VALID_INVALID, idList);
		}
		return RestfulRetUtils.getRetSuccess();
	}

	/**
	 * 返回流水号
	 * 
	 * @return
	 */
	private String getSerial() {
		// 生成受理编号（4位流水）从redis中获取
		Long appealSerial = StringUtils.toLong(RedisUtils.getObject("ADPF_ATM_APPCODE_SERIAL"), null);
		if (appealSerial == null)
			appealSerial = 0L;
		if (appealSerial >= 9999)
			appealSerial = 0L;
		RedisUtils.setObject("ADPF_ATM_APPCODE_SERIAL", appealSerial + 1, 0);
		DecimalFormat df = new DecimalFormat("000000");
		return df.format(appealSerial);
	}

}
