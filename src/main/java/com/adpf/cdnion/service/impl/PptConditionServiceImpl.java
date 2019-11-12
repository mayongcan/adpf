/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.cdnion.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.adpf.cdnion.service.PptConditionService;
import com.adpf.cdnion.entity.PptCondition;
import com.adpf.cdnion.repository.PptConditionRepository;

import static com.gimplatform.core.utils.RestfulRetUtils.getRetSuccess;

@Service
public class PptConditionServiceImpl implements PptConditionService {

    @Autowired
    private PptConditionRepository pptConditionRepository;

    @Override
    public JSONObject getList(Pageable page, PptCondition pptCondition, Map<String, Object> params) {
        List<Map<String, Object>> list = pptConditionRepository.getList(pptCondition, params, page.getPageNumber(), page.getPageSize());
        int count = pptConditionRepository.getListCount(pptCondition, params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);
    }

    @Override
    public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
        PptCondition pptCondition = (PptCondition) BeanUtils.mapToBean(params, PptCondition.class);
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        pptCondition.setUserName(userInfo.getUserName());
        pptCondition.setCreateTime(nowTime);
        pptConditionRepository.save(pptCondition);
        return getRetSuccess();
    }

    @Override
    public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        PptCondition pptCondition = (PptCondition) BeanUtils.mapToBean(params, PptCondition.class);
        PptCondition pptConditionInDb = pptConditionRepository.findOne(pptCondition.getId());
        if (pptConditionInDb == null) {
            return RestfulRetUtils.getErrorMsg("51006", "当前编辑的对象不存在");
        }
        //合并两个javabean
        BeanUtils.mergeBean(pptCondition, pptConditionInDb);
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        pptCondition.setUserName(userInfo.getUserName());
        pptCondition.setCreateTime(nowTime);
        pptConditionRepository.save(pptConditionInDb);
        return getRetSuccess();
    }

    @Override
    public JSONObject del(String idsList, UserInfo userInfo) {
        String[] ids = idsList.split(",");
        for (int i = 0; i < ids.length; i++) {
            pptConditionRepository.delete(StringUtils.toLong(ids[i]));
        }
        return getRetSuccess();
    }

    @Override
    public JSONObject getQuerybyid(String id) {
        List<Map<String, Object>> listByid = pptConditionRepository.getListByid(id);

        return getRetSuccess(listByid);
    }

}
