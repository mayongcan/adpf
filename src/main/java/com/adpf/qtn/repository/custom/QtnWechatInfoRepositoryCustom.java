/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.qtn.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.qtn.entity.QtnWechatInfo;
import com.alibaba.fastjson.JSONObject;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface QtnWechatInfoRepositoryCustom {

	/**
	 * 获取QtnWechatInfo列表
	 * @param qtnWechatInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(QtnWechatInfo qtnWechatInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取QtnWechatInfo列表总数
	 * @param qtnWechatInfo
	 * @param params
	 * @return
	 */
	public int getListCount(QtnWechatInfo qtnWechatInfo, Map<String, Object> params);
	
	public List<Map<String, Object>> findOneByOpenId(Map <String,Object> params);
}