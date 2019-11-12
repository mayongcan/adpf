/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.callwbook.service;

import com.adpf.callwbook.entity.CallWbook;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * 服务类接口
 *
 * @author
 * @version 1.0
 */
public interface CallWbookService {

    /**
     * 获取列表
     *
     * @param page
     * @param callWbook
     * @return
     */
    public JSONObject getList(Pageable page, CallWbook callWbook, Map<String, Object> params);

    /**
     * 新增
     *
     * @param params
     * @param userInfo
     * @return
     */
    public JSONObject add(Map<String, Object> params, UserInfo userInfo);

    /**
     * 编辑
     *
     * @param params
     * @param userInfo
     * @return
     */
    public JSONObject edit(Map<String, Object> params, UserInfo userInfo);

    /**
     * 删除
     *
     * @param idsList
     * @param userInfo
     * @return
     */
    public JSONObject del(String idsList, UserInfo userInfo);

    public JSONObject addOrUpday();

}