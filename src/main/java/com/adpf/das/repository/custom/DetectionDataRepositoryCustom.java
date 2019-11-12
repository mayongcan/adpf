/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.das.repository.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.das.entity.DetectionData;

/**
 * 自定义实体资源类接口
 *
 * @author
 * @version 1.0
 */
@NoRepositoryBean
public interface DetectionDataRepositoryCustom {

    /**
     * 获取DetectionData列表
     *
     * @param detectionData
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<Map<String, Object>> getList(DetectionData detectionData, Map<String, Object> params, int pageIndex, int pageSize);

    /**
     * 获取DetectionData列表总数
     *
     * @param detectionData
     * @param params
     * @return
     */
    public int getListCount(DetectionData detectionData, Map<String, Object> params);

    public  List<Map<String, Object>>  getAllVid();

    List<Map<String, Object>> getListByVid(String vid);
}