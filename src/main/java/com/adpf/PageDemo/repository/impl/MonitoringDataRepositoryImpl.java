/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.PageDemo.repository.impl;

import java.util.List;
import java.util.Map;

import com.adpf.PageDemo.entity.monitoringData;
import com.adpf.PageDemo.entity.monitoringStatistics;
import com.adpf.PageDemo.repository.custom.MonitoringDataRepositoryCustom;
import com.adpf.modules.orient.entity.District;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;



public class MonitoringDataRepositoryImpl extends BaseRepository implements MonitoringDataRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.curlid as \"curlid\", tb.video_id as \"videoid\", tb.album_id as \"albumid\", "
			+ "tb.period as \"period\", tb.TVID as \"TVID\", tb.tv_name as \"tvname\", tb.tv_type as \"TvType\", tb.video_name as \"videoname\","
			+ " tb.fangwen_date as \"fangwenDate\", tb.guojia as \"guojia\", tb.shengfen as \"shengfen\", tb.chegnshi as \"chegnshi\", tb.leixing as \"leixing\", "
			+ "tb.media_name as \"mediaName\", tb.complete_url as \"completeurl\" "
			+ "FROM monitoring_data tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM monitoring_data tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_QUERY_CONDITION = "SELECT ms.video_name as \"videoName\" ,ms.statistics_id as \"statisticsId\""
			+ "FROM monitoring_statistics ms "
			+ "WHERE 1 = 1 ";
	private static final String SQL_QUERY_CONDITIONS = "SELECT ms.media_name as \"mediaName\" "
			+ "FROM monitoring_statistics ms "
			+ "WHERE 1 = 1 ";
	private static final String SQL_QUERY_CONDITION_DATA = "SELECT sum(ms.pv) as \"pv\" , sum(ms.uv) as \"uv\" ,DATE_FORMAT(ms.data_date,'%m-%d') as \"dataDate\",ms.media_name as \"mediaName\",ms.video_name as \"videoName\" "
			+ "FROM monitoring_statistics ms "
			+ "WHERE 1 = 1 ";
	
	private static final String SQL_QUERY_CONDITION_DATA_PRO = "SELECT sum(ms.pv) as \"pv\" , sum(ms.uv) as \"uv\" ,ms.media_name as \"mediaName\",ms.video_name as \"videoName\",ms.prov as \"prov\" " 
			+ "FROM monitoring_statistics ms "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(monitoringData monitoringData, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, monitoringData, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(monitoringData monitoringData, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, monitoringData, params);
		return getResultListTotalCount(sqlParams);
	}
	
	@Override
	public List<Map<String, Object>> getQueryMediaName() {
		SqlParams sqlParams = getQueryMediaName(SQL_QUERY_CONDITIONS);
		return getResultList(sqlParams);
}
	@Override
	public List<Map<String, Object>> getQueryCondition(String condition,String selectip,String limit) {
		SqlParams sqlParams ;
		//返回查询条件
		if(condition.equals("false")){
			 sqlParams = getTerritory(SQL_QUERY_CONDITIONS,condition, selectip,limit);
		}else{
			 sqlParams = getTerritory(SQL_QUERY_CONDITION,condition, selectip,limit);
		}
		return getResultList(sqlParams);
}
	@Override
	public List<Map<String, Object>> getStatisticalData(monitoringStatistics monitoringStatistics ){
		SqlParams sqlParams = getTerritorys(SQL_QUERY_CONDITION_DATA,monitoringStatistics);
		return getResultList(sqlParams);
}
	@Override
	public List<Map<String, Object>> statisticalRanKing(monitoringStatistics monitoringStatistics ,String limit ){
		SqlParams sqlParams = statisticalRanKing(SQL_QUERY_CONDITION_DATA,monitoringStatistics,limit);
		return getResultList(sqlParams);
}
	@Override
	public List<Map<String, Object>> statisticalRanKingUV(monitoringStatistics monitoringStatistics  ,String limit){
		SqlParams sqlParams = statisticalRanKingUV(SQL_QUERY_CONDITION_DATA,monitoringStatistics,limit);
		return getResultList(sqlParams);
}
	@Override
	public List<Map<String, Object>> getStatisticalPie(monitoringStatistics monitoringStatistics ){
		SqlParams sqlParams = getTerritorysPie(SQL_QUERY_CONDITION_DATA,monitoringStatistics);
		return getResultList(sqlParams);
}
	@Override
	public List<Map<String, Object>> getStatisticalPiePro(monitoringStatistics monitoringStatistics ){
		SqlParams sqlParams = getTerritorysPiePro(SQL_QUERY_CONDITION_DATA_PRO,monitoringStatistics);
		return getResultList(sqlParams);
}
	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, monitoringData monitoringData, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (monitoringData != null && !StringUtils.isBlank(monitoringData.getTVID())) {
            sqlParams.querySql.append(getLikeSql("tb.TVID", ":TVID"));
			sqlParams.paramsList.add("TVID");
			sqlParams.valueList.add(monitoringData.getTVID());
		}
		if (monitoringData != null && !StringUtils.isBlank(monitoringData.getVideoname())) {
            sqlParams.querySql.append(getLikeSql("tb.video_name", ":videoname"));
			sqlParams.paramsList.add("videoname");
			sqlParams.valueList.add(monitoringData.getVideoname());
		}
        return sqlParams;
	}
	
	private SqlParams getQueryMediaName(String sql){
		SqlParams sqlParams = new SqlParams();
			sqlParams.querySql.append(sql);
			sqlParams.querySql.append(" GROUP BY ms.media_name ");
		
	    return sqlParams;
	}
	
	private SqlParams getTerritory(String sql,String condition,String selectip,String limit){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if(condition.equals("false")){
			sqlParams.querySql.append(" GROUP BY ms.media_name ");
		}else{
			String data="'"+condition.replace(" ", "").replace(",", "','")+"'";
			if(!selectip.equals("false")&&!selectip.isEmpty()){
				sqlParams.querySql.append(" AND ms.video_name  like '%"+selectip+"%'");
			}
			sqlParams.querySql.append(" AND ms.media_name  in ("+data+")");
			sqlParams.querySql.append(" GROUP BY ms.video_name ");
			sqlParams.querySql.append(" LIMIT  "+limit);
		}
		
	    return sqlParams;
	}
	private SqlParams getTerritorys(String sql, monitoringStatistics monitoringStatistics){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (monitoringStatistics != null && !StringUtils.isBlank(monitoringStatistics.getMediaName())) {
			 String data="'"+monitoringStatistics.getMediaName().replace(",", "','")+"'";
			 sqlParams.querySql.append(" and ms.media_name in ("+data.replace(" ", "")+")");
		}
		if (monitoringStatistics != null && !StringUtils.isBlank(monitoringStatistics.getVideoName())) {
			 String data="'"+monitoringStatistics.getVideoName().replace(",", "','")+"'";
			 sqlParams.querySql.append(" and ms.video_name in ("+data+")");
		}
		if (monitoringStatistics != null && !StringUtils.isBlank(monitoringStatistics.getDataDate())) {
            sqlParams.querySql.append(" and ms.data_date BETWEEN "+monitoringStatistics.getDataDate());
		}
		sqlParams.querySql.append(" GROUP BY ms.data_date,ms.media_name,ms.video_name");
        return sqlParams;
	}
	private SqlParams getTerritorysPie(String sql, monitoringStatistics monitoringStatistics){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (monitoringStatistics != null && !StringUtils.isBlank(monitoringStatistics.getMediaName())) {
			 String data="'"+monitoringStatistics.getMediaName().replace(",", "','")+"'";
			 sqlParams.querySql.append(" and ms.media_name in ("+data.replace(" ", "")+")");
		}
		if (monitoringStatistics != null && !StringUtils.isBlank(monitoringStatistics.getVideoName())) {
			 String data="'"+monitoringStatistics.getVideoName().replace(",", "','")+"'";
			 sqlParams.querySql.append(" and ms.video_name in ("+data+")");
		}
		if (monitoringStatistics != null && !StringUtils.isBlank(monitoringStatistics.getDataDate())) {
            sqlParams.querySql.append(" and ms.data_date BETWEEN "+monitoringStatistics.getDataDate());
		}
		sqlParams.querySql.append(" GROUP BY ms.media_name,ms.video_name");
        return sqlParams;
	}
	private SqlParams getTerritorysPiePro(String sql, monitoringStatistics monitoringStatistics){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (monitoringStatistics != null && !StringUtils.isBlank(monitoringStatistics.getMediaName())) {
			 String data="'"+monitoringStatistics.getMediaName().replace(",", "','")+"'";
			 sqlParams.querySql.append(" and ms.media_name in ("+data.replace(" ", "")+")");
		}
		if (monitoringStatistics != null && !StringUtils.isBlank(monitoringStatistics.getVideoName())) {
			 String data="'"+monitoringStatistics.getVideoName().replace(",", "','")+"'";
			 sqlParams.querySql.append(" and ms.video_name in ("+data+")");
		}
		if (monitoringStatistics != null && !StringUtils.isBlank(monitoringStatistics.getDataDate())) {
            sqlParams.querySql.append(" and ms.data_date BETWEEN "+monitoringStatistics.getDataDate());
		}
		sqlParams.querySql.append(" GROUP BY ms.media_name,ms.video_name,ms.prov");
        return sqlParams;
	}
	private SqlParams statisticalRanKing(String sql, monitoringStatistics monitoringStatistics ,String limit){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (monitoringStatistics != null && !StringUtils.isBlank(monitoringStatistics.getMediaName())) {
			sqlParams.querySql.append(" and ms.media_name = '"+monitoringStatistics.getMediaName()+"'");
		}
		if (monitoringStatistics != null && !StringUtils.isBlank(monitoringStatistics.getDataDate())) {
			 sqlParams.querySql.append(" and ms.data_date like '"+monitoringStatistics.getDataDate()+"%'");
		}
		sqlParams.querySql.append(" GROUP BY ms.data_date,ms.media_name,ms.video_name");
		
		sqlParams.querySql.append(" order by pv DESC");
		
		sqlParams.querySql.append(" LIMIT "+limit);
		
        return sqlParams;
	}
	private SqlParams statisticalRanKingUV(String sql, monitoringStatistics monitoringStatistics ,String limit){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (monitoringStatistics != null && !StringUtils.isBlank(monitoringStatistics.getMediaName())) {
			 sqlParams.querySql.append(" and ms.media_name = '"+monitoringStatistics.getMediaName()+"'");
		}
		if (monitoringStatistics != null && !StringUtils.isBlank(monitoringStatistics.getDataDate())) {
            sqlParams.querySql.append(" and ms.data_date like '"+monitoringStatistics.getDataDate()+"%'");
		}
		sqlParams.querySql.append(" GROUP BY ms.data_date,ms.media_name,ms.video_name");
		
		sqlParams.querySql.append(" order by uv DESC");
		
		sqlParams.querySql.append(" LIMIT "+limit);
		
        return sqlParams;
	}
}