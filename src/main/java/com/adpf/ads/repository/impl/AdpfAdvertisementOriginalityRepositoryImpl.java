/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.ads.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.ads.entity.AdpfAdvertisementOriginality;
import com.adpf.ads.repository.custom.AdpfAdvertisementOriginalityRepositoryCustom;

public class AdpfAdvertisementOriginalityRepositoryImpl extends BaseRepository implements AdpfAdvertisementOriginalityRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ORIGINALITY_ID as \"originalityId\", tb.ORIGINALITY_STATE as \"originalityState\", tb.CENTENT as \"centent\", tb.IMG_URL as \"imgUrl\", tb.MV_URL as \"mvUrl\", tb.WIDTH as \"width\", tb.HEIGHT as \"height\", tb.ORGINALITY_NAME as \"orginalityName\" "
			+ "FROM adpf_advertisement_originality tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_advertisement_originality tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(AdpfAdvertisementOriginality adpfAdvertisementOriginality, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfAdvertisementOriginality, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ORIGINALITY_ID DESC ", " \"originalityId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfAdvertisementOriginality adpfAdvertisementOriginality, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfAdvertisementOriginality, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfAdvertisementOriginality adpfAdvertisementOriginality, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (adpfAdvertisementOriginality != null && adpfAdvertisementOriginality.getOriginalityState() != null) {
			sqlParams.querySql.append(" AND tb.ORIGINALITY_STATE = :originalityState ");
			sqlParams.paramsList.add("originalityState");
			sqlParams.valueList.add(adpfAdvertisementOriginality.getOriginalityState());
		}
		if (adpfAdvertisementOriginality != null && !StringUtils.isBlank(adpfAdvertisementOriginality.getOrginalityName())) {
            sqlParams.querySql.append(getLikeSql("tb.ORGINALITY_NAME", ":orginalityName"));
			sqlParams.paramsList.add("orginalityName");
			sqlParams.valueList.add(adpfAdvertisementOriginality.getOrginalityName());
		}
        return sqlParams;
	}
}