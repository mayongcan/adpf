/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.Tencent.weChatLogin.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.Tencent.weChatLogin.entity.WechatLoginUserinfo;
import com.adpf.modules.Tencent.weChatLogin.repository.custom.WechatLoginUserinfoRepositoryCustom;
import com.adpf.qtn.entity.QtnWechatInfo;
import com.adpf.qtn.entity.QtnWindowInfo;

public class WechatLoginUserinfoRepositoryImpl extends BaseRepository implements WechatLoginUserinfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.OPENID as \"openid\", tb.NICKNAME as \"nickname\", tb.SEX as \"sex\", tb.COUNTRY as \"country\", tb.PROVINCE as \"province\", tb.CITY as \"city\", tb.HEADIMGURL as \"headimgurl\", tb.TEL as \"tel\" "
			+ "FROM wechat_login_userinfo tb "
			+ "WHERE 1 = 1 ";
	
	private static final String SQL_USER_INFO = "SELECT tb.ID as \"id\", tb.NICKNAME as \"nickname\", tb.SEX as \"sex\", tb.COUNTRY as \"country\", tb.PROVINCE as \"province\", tb.CITY as \"city\", tb.HEADIMGURL as \"headimgurl\", tb.TEL as \"tel\" "
			+ "FROM wechat_login_userinfo tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM wechat_login_userinfo tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(WechatLoginUserinfo wechatLoginUserinfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, wechatLoginUserinfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.OPENID DESC ", " \"openid\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(WechatLoginUserinfo wechatLoginUserinfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, wechatLoginUserinfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, WechatLoginUserinfo wechatLoginUserinfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        return sqlParams;
	}
	
	private SqlParams genOpId(String sql,Map<String, Object> params) {
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(true) {
			sqlParams.querySql.append(" and tb.openid= :openid");
			sqlParams.paramsList.add("openid");
			sqlParams.valueList.add(params.get("openid"));
		}
		return sqlParams;		
	}
	
	private SqlParams genId(String sql,Map<String, Object> params) {
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(true) {
			sqlParams.querySql.append(" and tb.id= :id");
			sqlParams.paramsList.add("id");
			sqlParams.valueList.add(params.get("id"));
		}
		return sqlParams;		
	}
	
	@Override
	public List<Map<String, Object>> findOneByOpenId(Map<String, Object> params) {
		SqlParams sqlParams = genOpId(SQL_USER_INFO, params);
		return getResultList(sqlParams);
	}
	
	@Override
	public List<Map<String, Object>> findOneById(Map<String, Object> params) {
		SqlParams sqlParams = genId(SQL_USER_INFO, params);
		return getResultList(sqlParams);
	}
}