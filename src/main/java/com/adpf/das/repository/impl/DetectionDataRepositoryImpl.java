/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.das.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.das.entity.DetectionData;
import com.adpf.das.repository.custom.DetectionDataRepositoryCustom;

public class DetectionDataRepositoryImpl extends BaseRepository implements DetectionDataRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.curlid as \"curlid\", tb.video_id as \"videoId\", tb.album_id as \"albumId\", tb.period as \"period\", tb.TVID as \"tvid\", tb.tv_name as \"tvName\", tb.tv_type as \"tvType\", tb.video_name as \"videoName\", tb.media_name as \"mediaName\", tb.complete_url as \"completeUrl\" "
			+ "FROM detection_data tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM detection_data tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_ALLVoId = "SELECT video_id from detection_data";

	private static final String SQL_GET_LIST_BYVID = "SELECT tb.id as \"id\", tb.curlid as \"curlid\", tb.video_id as \"videoId\", tb.album_id as \"albumId\", tb.period as \"period\", tb.TVID as \"tvid\", tb.tv_name as \"tvName\", tb.tv_type as \"tvType\", tb.video_name as \"videoName\", tb.media_name as \"mediaName\", tb.complete_url as \"completeUrl\" "
            + "FROM detection_data tb ";

	public List<Map<String, Object>> getList(DetectionData detectionData, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, detectionData, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(DetectionData detectionData, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, detectionData, params);
		return getResultListTotalCount(sqlParams);
	}


	public List<Map<String, Object>> getAllVid() {
	    //查詢所有video的值
	    SqlParams sqlParams = getAllVid(SQL_GET_ALLVoId);
		return getResultList(sqlParams);
	}

    @Override
    public List<Map<String, Object>> getListByVid(String vid) {

        SqlParams sqlParams = getListByVoid(SQL_GET_LIST_BYVID,vid);
        return getResultList(sqlParams);
    }

    /**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, DetectionData detectionData, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (detectionData != null && !StringUtils.isBlank(detectionData.getTvName())) {
            sqlParams.querySql.append(getLikeSql("tb.tv_name", ":tvName"));
			sqlParams.paramsList.add("tvName");
			sqlParams.valueList.add(detectionData.getTvName());
		}
		if (detectionData != null && !StringUtils.isBlank(detectionData.getMediaName())) {
            sqlParams.querySql.append(getLikeSql("tb.media_name", ":mediaName"));
			sqlParams.paramsList.add("mediaName");
			sqlParams.valueList.add(detectionData.getMediaName());
		}
        return sqlParams;
	}

    /**
     * 生成查詢vidoeid
     * @param sql
     * @return
     */
	private SqlParams getAllVid(String sql){
        SqlParams sqlParam = new SqlParams();
        sqlParam.querySql.append(sql);
        return sqlParam;
	}

    /**
     * 根据video_id查询列表
     * @param sql
     * @param
     * @return
     */
    private SqlParams getListByVoid(String sql,String vid){
        SqlParams sqlParams = new SqlParams();
        sql=sql+"where tb.video_id=\""+(vid)+"\"";
        sqlParams.querySql.append(sql);
        return sqlParams;
    }

}