/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.advertiser.service;

import com.adpf.advertiser.entity.Advertisers;
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
public interface AdvertisersService {

    /**
     * 获取列表
     *
     * @param page
     * @param advertisers
     * @return
     */
    public JSONObject getList(Pageable page, Advertisers advertisers, Map<String, Object> params, UserInfo userInfo);

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


    public JSONObject topUp(Map<String, Object> params, UserInfo userInfo);

    public JSONObject dredge(Map<String, Object> params, UserInfo userInfo);

    public JSONObject getRoleIdByRoleName(Map<String, Object> params);

    public JSONObject issue(Map<String, Object> params, UserInfo userInfo);

    public JSONObject getStatisticsList(Map<String, Object> params,Pageable page);
}
