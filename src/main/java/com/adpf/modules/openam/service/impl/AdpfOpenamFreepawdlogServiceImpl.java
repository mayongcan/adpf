/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.openam.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;
import com.adpf.modules.openam.entity.AdpfOpenamFreepawdlog;
import com.adpf.modules.openam.repository.AdpfOpenamFreepawdlogRepository;
import com.adpf.modules.openam.restful.AdpfOpenamFreepawdlogRestful;
import com.adpf.modules.openam.service.AdpfOpenamFreepawdlogService;

@Service
public class AdpfOpenamFreepawdlogServiceImpl implements AdpfOpenamFreepawdlogService {

	protected static final Logger logger = LogManager.getLogger(AdpfOpenamFreepawdlogServiceImpl.class);

	@Autowired
	private AdpfOpenamFreepawdlogRepository adpfOpenamFreepawdlogRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfOpenamFreepawdlog adpfOpenamFreepawdlog, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfOpenamFreepawdlogRepository.getList(adpfOpenamFreepawdlog, params,
				page.getPageNumber(), page.getPageSize());
		int count = adpfOpenamFreepawdlogRepository.getListCount(adpfOpenamFreepawdlog, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);
	}

	@Override
	public JSONObject add(Map<String, Object> params, JSONObject jobect, UserInfo userInfo) {
		AdpfOpenamFreepawdlog adpfOpenamFreepawdlog = (AdpfOpenamFreepawdlog) BeanUtils.mapToBean(params,
				AdpfOpenamFreepawdlog.class);
		String code = MapUtils.getString(params, "code", "");
		AdpfOpenamFreepawdlog tmp = adpfOpenamFreepawdlogRepository.getByCode(code);
		if (tmp != null) {
			tmp.setMobile(jobect.getString("mobileName"));
			tmp.setNickName(jobect.getString("nickName"));
			tmp.setOwner(jobect.getString("owner"));
			tmp.setOpenId(jobect.getString("openId"));
			tmp.setUserIconUrl(jobect.getString("userIconUrl"));
			if (userInfo == null) {
				tmp.setOrganizerId(112l);
			} else {
				tmp.setOrganizerId(userInfo.getOrganizerId());
			}
			tmp.setResult(jobect.getString("result"));
			if (!"0".equals(jobect.getString("result"))) {
				tmp.setStatus("3");
			} else {
				tmp.setStatus("2");
			}
			logger.info("===");
			logger.info(tmp.toString());
			adpfOpenamFreepawdlogRepository.save(tmp);
		} else {
			adpfOpenamFreepawdlog.setCreateDate(new Date());
			adpfOpenamFreepawdlog.setMobile(jobect.getString("mobileName"));
			adpfOpenamFreepawdlog.setNickName(jobect.getString("nickName"));
			adpfOpenamFreepawdlog.setOwner(jobect.getString("owner"));
			adpfOpenamFreepawdlog.setOpenId(jobect.getString("openId"));
			adpfOpenamFreepawdlog.setUserIconUrl(jobect.getString("userIconUrl"));
			if (userInfo == null) {
				adpfOpenamFreepawdlog.setOrganizerId(112l);
			} else {
				adpfOpenamFreepawdlog.setOrganizerId(userInfo.getOrganizerId());
			}
//			adpfOpenamFreepawdlog.setOrganizerId(userInfo.getOrganizerId());
			adpfOpenamFreepawdlog.setResult(jobect.getString("result"));
			adpfOpenamFreepawdlogRepository.save(adpfOpenamFreepawdlog);
		}

//		adpfOpenamFreepawdlog.setStatus("1");

		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject addIm(Map<String, Object> params, JSONObject jobect, UserInfo userInfo) {
		AdpfOpenamFreepawdlog adpfOpenamFreepawdlog = (AdpfOpenamFreepawdlog) BeanUtils.mapToBean(params,
				AdpfOpenamFreepawdlog.class);
		adpfOpenamFreepawdlog.setCreateDate(new Date());
		adpfOpenamFreepawdlog.setMobile(jobect.getString("mobileName"));
		adpfOpenamFreepawdlog.setNickName(jobect.getString("nickName"));
		adpfOpenamFreepawdlog.setOwner(jobect.getString("owner"));
		adpfOpenamFreepawdlog.setOpenId(jobect.getString("openId"));
		adpfOpenamFreepawdlog.setUserIconUrl(jobect.getString("userIconUrl"));
		adpfOpenamFreepawdlog.setOrganizerId(Long.valueOf(MapUtils.getString(params, "account", "")));
		adpfOpenamFreepawdlog.setResult(jobect.getString("result"));
		adpfOpenamFreepawdlogRepository.save(adpfOpenamFreepawdlog);

		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
		AdpfOpenamFreepawdlog adpfOpenamFreepawdlog = (AdpfOpenamFreepawdlog) BeanUtils.mapToBean(params,
				AdpfOpenamFreepawdlog.class);
		AdpfOpenamFreepawdlog adpfOpenamFreepawdlogInDb = adpfOpenamFreepawdlogRepository
				.findOne(adpfOpenamFreepawdlog.getId());
		if (adpfOpenamFreepawdlogInDb == null) {
			return RestfulRetUtils.getErrorMsg("51006", "当前编辑的对象不存在");
		}
		// 合并两个javabean
		BeanUtils.mergeBean(adpfOpenamFreepawdlog, adpfOpenamFreepawdlogInDb);
		adpfOpenamFreepawdlogRepository.save(adpfOpenamFreepawdlogInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			adpfOpenamFreepawdlogRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

	/**
	 * 更新电话号码
	 * 
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject udpateMobile(String mobile, String aesCacheKey) {

		adpfOpenamFreepawdlogRepository.udpateMobile(mobile, aesCacheKey);
		return RestfulRetUtils.getRetSuccess();

	}

	@Override
	public JSONObject getReportList(AdpfOpenamFreepawdlog adpfOpenamFreepawdlog, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfOpenamFreepawdlogRepository.getReportList(adpfOpenamFreepawdlog, params);
		return RestfulRetUtils.getRetSuccess(list);
	}

	/**
	 * 根据code获取用户信息
	 * 
	 * @param params
	 * @param code
	 * @return
	 */
	public AdpfOpenamFreepawdlog getByCode(String code) {

		AdpfOpenamFreepawdlog tmp = adpfOpenamFreepawdlogRepository.getByCode(code);
		return tmp;

	}

}
