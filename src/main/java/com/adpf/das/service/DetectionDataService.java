/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.das.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.adpf.das.entity.DetectionData;

/**
 * 服务类接口
 *
 * @author
 * @version 1.0
 */
public interface DetectionDataService {

    /**
     * 获取列表
     *
     * @param page
     * @param detectionData
     * @return
     */
    public JSONObject getList(Pageable page, DetectionData detectionData, Map<String, Object> params);

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


    public JSONObject loadExcel(ArrayList<Map<String, String>> maps);


    public ArrayList<String> selectAllVid();
}
