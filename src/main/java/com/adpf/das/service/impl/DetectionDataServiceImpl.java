/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.das.service.impl;

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

import com.adpf.das.service.DetectionDataService;
import com.adpf.das.entity.DetectionData;
import com.adpf.das.repository.DetectionDataRepository;

@Service
public class DetectionDataServiceImpl implements DetectionDataService {

    @Autowired
    private DetectionDataRepository detectionDataRepository;

    @Override
    public JSONObject getList(Pageable page, DetectionData detectionData, Map<String, Object> params) {
        List<Map<String, Object>> list = detectionDataRepository.getList(detectionData, params, page.getPageNumber(), page.getPageSize());
        int count = detectionDataRepository.getListCount(detectionData, params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);
    }

    @Override
    public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
        DetectionData detectionData = (DetectionData) BeanUtils.mapToBean(params, DetectionData.class);
        detectionDataRepository.save(detectionData);
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        DetectionData detectionData = (DetectionData) BeanUtils.mapToBean(params, DetectionData.class);
        DetectionData detectionDataInDb = detectionDataRepository.findOne(detectionData.getId());
        if (detectionDataInDb == null) {
            return RestfulRetUtils.getErrorMsg("51006", "当前编辑的对象不存在");
        }
        //合并两个javabean
        BeanUtils.mergeBean(detectionData, detectionDataInDb);
        detectionDataRepository.save(detectionDataInDb);
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public JSONObject del(String idsList, UserInfo userInfo) {
        String[] ids = idsList.split(",");
        for (int i = 0; i < ids.length; i++) {
            detectionDataRepository.delete(StringUtils.toLong(ids[i]));
        }
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public JSONObject loadExcel(ArrayList<Map<String, String>> maps) {
        List<Map<String, Object>> allVid = detectionDataRepository.getAllVid();
        String videoId = "video_id";
        List voIdlist = new ArrayList<String>();

        for (int i = 0; i < allVid.size(); i++) {
            voIdlist.add(allVid.get(i).get(videoId));
        }
        List  detectionDatas = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {
            DetectionData o = (DetectionData) BeanUtils.mapToBean(maps.get(i), DetectionData.class);
            //不可重复上传
            if (voIdlist.contains(o.getVideoId())){
              //return RestfulRetUtils.getErrorMsg("10010","不能重复提交");
                //如果vid相同直接跳过本次循环
                continue;
            }
            //去重
            List<Map<String, Object>> listByVid = detectionDataRepository.getListByVid(o.getVideoId());
            if(null == listByVid || listByVid.size() ==0 ){
                detectionDatas.add(o);
            }
            detectionDataRepository.save(detectionDatas);
        }
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public ArrayList<String> selectAllVid() {
        return null;
    }


}
