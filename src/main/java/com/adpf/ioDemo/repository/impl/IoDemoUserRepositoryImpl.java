/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.ioDemo.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.ioDemo.entity.IoDemoUser;
import com.adpf.ioDemo.repository.custom.IoDemoUserRepositoryCustom;

public class IoDemoUserRepositoryImpl extends BaseRepository implements IoDemoUserRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.mingcheng as \"mingcheng\", tb.shiping as \"shiping\", tb.shiming as \"shiming\", tb.yonghu_id as \"yonghuId\", tb.shuge_id as \"shugeId\", tb.start_time as \"startTime\", tb.end_time as \"endTime\", tb.fangwen_data as \"fangwenData\", tb.fangwen_date as \"fangwenDate\", tb.guojia as \"guojia\", tb.shengfen as \"shengfen\", tb.chegnshi as \"chegnshi\", tb.leixing as \"leixing\", tb.libao as \"libao\", tb.xingbie as \"xingbie\", tb.nicheng as \"nicheng\", tb.weixin_sheng as \"weixinSheng\", tb.nianling as \"nianling\", tb.openid as \"openid\", tb.banben as \"banben\", tb.qudao as \"qudao\", tb.shebeipinpai as \"shebeipinpai\", tb.shebeixinghao as \"shebeixinghao\", tb.yunyingshang as \"yunyingshang\", tb.caozuxitong as \"caozuxitong\", tb.xitongbanben as \"xitongbanben\" "
			+ "FROM io_demo_user tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM io_demo_user tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(IoDemoUser ioDemoUser, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, ioDemoUser, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(IoDemoUser ioDemoUser, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, ioDemoUser, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, IoDemoUser ioDemoUser, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (ioDemoUser != null && !StringUtils.isBlank(ioDemoUser.getMingcheng())) {
            sqlParams.querySql.append(getLikeSql("tb.mingcheng", ":mingcheng"));
			sqlParams.paramsList.add("mingcheng");
			sqlParams.valueList.add(ioDemoUser.getMingcheng());
		}
		if (ioDemoUser != null && !StringUtils.isBlank(ioDemoUser.getShugeId())) {
            sqlParams.querySql.append(getLikeSql("tb.shuge_id", ":shugeId"));
			sqlParams.paramsList.add("shugeId");
			sqlParams.valueList.add(ioDemoUser.getShugeId());
		}
        return sqlParams;
	}
}