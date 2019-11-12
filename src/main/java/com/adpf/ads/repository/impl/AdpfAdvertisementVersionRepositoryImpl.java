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

import com.adpf.ads.entity.AdpfAdvertisementVersion;
import com.adpf.ads.repository.custom.AdpfAdvertisementVersionRepositoryCustom;

public class AdpfAdvertisementVersionRepositoryImpl extends BaseRepository implements AdpfAdvertisementVersionRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.VERSION_ID as \"versionId\", tb.PC_OR_MOBILE as \"pcOrMobile\", tb.VERSION_NAME as \"versionName\", tb.ORIGINALITY_STATE as \"originalityState\", tb.ADPF_DESCRIBE as \"adpfDescribe\", tb.ADPF_EXPOSURE as \"adpfExposure\" "
			+ "FROM adpf_advertisement_version tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM adpf_advertisement_version tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(AdpfAdvertisementVersion adpfAdvertisementVersion, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, adpfAdvertisementVersion, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.VERSION_ID DESC ", " \"versionId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(AdpfAdvertisementVersion adpfAdvertisementVersion, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, adpfAdvertisementVersion, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, AdpfAdvertisementVersion adpfAdvertisementVersion, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (adpfAdvertisementVersion != null && adpfAdvertisementVersion.getPcOrMobile() != null) {
			sqlParams.querySql.append(" AND tb.PC_OR_MOBILE = :pcOrMobile ");
			sqlParams.paramsList.add("pcOrMobile");
			sqlParams.valueList.add(adpfAdvertisementVersion.getPcOrMobile());
		}
		if (adpfAdvertisementVersion != null && adpfAdvertisementVersion.getOriginalityState() != null) {
			sqlParams.querySql.append(" AND tb.ORIGINALITY_STATE = :originalityState ");
			sqlParams.paramsList.add("originalityState");
			sqlParams.valueList.add(adpfAdvertisementVersion.getOriginalityState());
		}
        return sqlParams;
	}
}