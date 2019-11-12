/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.Tencent.weChatLogin.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.Tencent.weChatLogin.entity.WechatLoginUserinfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface WechatLoginUserinfoRepositoryCustom {

	/**
	 * 获取WechatLoginUserinfo列表
	 * @param wechatLoginUserinfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(WechatLoginUserinfo wechatLoginUserinfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取WechatLoginUserinfo列表总数
	 * @param wechatLoginUserinfo
	 * @param params
	 * @return
	 */
	public int getListCount(WechatLoginUserinfo wechatLoginUserinfo, Map<String, Object> params);
	
	public List<Map<String, Object>> findOneByOpenId(Map <String,Object> params);
	
	public List<Map<String, Object>> findOneById(Map <String,Object> params);
}