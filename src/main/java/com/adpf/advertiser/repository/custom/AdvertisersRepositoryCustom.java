/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.advertiser.repository.custom;

import com.adpf.advertiser.entity.Advertisers;
import org.springframework.data.repository.NoRepositoryBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 自定义实体资源类接口
 *
 * @author
 * @version 1.0
 */
@NoRepositoryBean
public interface AdvertisersRepositoryCustom {

    /**
     * 获取Advertisers列表
     *
     * @param advertisers
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<Map<String, Object>> getList(Advertisers advertisers, Map<String, Object> params, int pageIndex, int pageSize, String userid);

    /**
     * 获取Advertisers列表总数
     *
     * @param advertisers
     * @param params
     * @return
     */
    public int getListCount(Advertisers advertisers, Map<String, Object> params, String userid);

    public int updayBalance(String id, BigDecimal newBalance);

    public List<Map<String, Object>> getStatisList(Map<String, Object> params,int pageIndex, int pageSize);

    public int getStatisCount(Map<String, Object> params);

    List<Map<String,Object>> getListBySeatidAndCallId(Map<String, Object> stringObjectMap);

    public List<Map<String, Object>> getListOfOem();
}