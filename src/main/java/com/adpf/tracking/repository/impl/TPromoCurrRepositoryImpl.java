/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tracking.repository.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.tracking.entity.TPromoCurr;
import com.adpf.tracking.repository.custom.TPromoCurrRepositoryCustom;
import com.alibaba.druid.sql.ast.statement.SQLForeignKeyImpl.On;

public class TPromoCurrRepositoryImpl extends BaseRepository implements TPromoCurrRepositoryCustom{

	/*private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.ods_time as \"odsTime\", tb.promo_id as \"promoId\", tb.channel_id as \"channelId\", tb.agent_id as \"agentId\", tb.app_id as \"appId\", tb.click as \"click\", tb.click_valid as \"clickValid\", tb.click_distinct as \"clickDistinct\", tb.click_distinct_day as \"clickDistinctDay\", tb.active_distinct as \"activeDistinct\", tb.dau as \"dau\", tb.active_register as \"activeRegister\", tb.register_distinct as \"registerDistinct\", tb.register_distinct_day as \"registerDistinctDay\", tb.login_day as \"loginDay\", tb.range_fee as \"rangeFee\", tb.new_fee as \"newFee\", tb.pay_device as \"payDevice\", tb.total_fee as \"totalFee\", tb.total_pay_device as \"totalPayDevice\"  "			
			+ "FROM t_promo_curr tb "
			+ "WHERE 1 = 1 ";*/
	
	/*private static final String SQL_GET_LIST ="SELECT DATE(tb.ods_time) as \"odsTime\",tb.promo_id as \"promoId\", tb.channel_id as \"channelId\", tb.agent_id as \"agentId\", tb.app_id as \"appId\",sum(tb.click ) as \"click\",sum(tb.click_valid) as \"clickValid\", sum(tb.click_distinct) as \"clickDistinct\", sum(tb.click_distinct_day) as \"clickDistinctDay\", sum(tb.active_distinct) as \"activeDistinct\",sum(tb.active_register) as \"activeRegister\",sum(tb.register_distinct) as \"registerDistinct\", sum(tb.register_distinct_day) as \"registerDistinctDay\", sum(tb.login_day) as \"loginDay\", sum(tb.range_fee) as \"rangeFee\", sum(tb.new_fee) as \"newFee\", sum(tb.pay_device) as \"payDevice\", sum(tb.total_fee) as \"totalFee\", sum(tb.total_pay_device) as \"totalPayDevice\" "
	+ " (SELECT COUNT(1) FROM t_click tcl left join t_app_login tal on tcl.idfa = tal.idfa and tcl.imei = tal.imei where tcl.create_time = DATE_SUB(tal.create_time,INTERVAL 1 DAY) ) as \"D1\", "
	+ " (SELECT COUNT(1) FROM t_click tcl left join t_app_login tal on tcl.idfa = tal.idfa and tcl.imei = tal.imei where tcl.create_time = DATE_SUB(tal.create_time,INTERVAL 2 DAY) ) as \"D2\", "
	+ " (SELECT COUNT(1) FROM t_click tcl left join t_app_login tal on tcl.idfa = tal.idfa and tcl.imei = tal.imei where tcl.create_time = DATE_SUB(tal.create_time,INTERVAL 7 DAY) ) as \"D7\", "
	+ " (SELECT COUNT(1) FROM t_click tcl left join t_app_login tal on tcl.idfa = tal.idfa and tcl.imei = tal.imei where tcl.create_time = DATE_SUB(tal.create_time,INTERVAL 30 DAY) ) as \"D30\", "
	+ " FROM t_promo_curr tb "
	+ " WHERE 1 = 1 ";*/
	
	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" from " +
			"(SELECT promo_id,channel_id,app_id,DATE_FORMAT(ods_time, '%y-%m-%d') AS 'odsTime',SUM(click) as 'click',sum(click_valid) as 'click_valid',SUM(click_distinct) as 'click_distinct',SUM(click_distinct_day) as 'click_distinct_day',\n" +
			"SUM(active_distinct) AS 'active_distinct',SUM(active_register) as \"active_register\",SUM(register_distinct)as 'register_distinct',SUM(register_distinct_day)as 'register_distinct_day',SUM(login_day)as 'login_day',\n" +
			"SUM(new_fee)as 'new_fee',SUM(pay_device)as 'pay_device',SUM(total_fee)as 'total_fee',SUM(total_pay_device)as 'total_pay_device' FROM t_promo_curr GROUP BY promo_id,odsTime) tb\n" +
			"LEFT JOIN (SELECT count(CASE WHEN login_count = 1 THEN TRUE ELSE NULL END) AS 'd1',promo_id,DATE_FORMAT(last_login_time, '%y-%m-%d') AS 'time',login_count FROM t_retained trd LEFT JOIN t_click tck ON trd.ip = tck.ip AND trd.imei = tck.imei GROUP BY promo_id,time,trd.login_count) tt\n" +
			"ON tb.promo_id = tt.promo_id AND tb.odsTime = tt.time "
			+"where 1=1 ";
		
	
	private static final String SQL_GET_LIST ="SELECT tt.time ,tt.promo_id,tt.agent_id,tt.channel_id,SUM(tt.sum_click) AS \"click\",SUM(tt.sum_click_valid) AS \"clickValid\",SUM(tt.sum_click_distinct) AS \"clickDistinct\",SUM(tt.sum_click_distinct_day) AS \"clickDistinctDay\"," 
			+"SUM(tt.sum_active_distinct) as \"activeDistinct\",SUM(tt.sum_dau) as \"dau\",SUM(tt.sum_active_register) as \"activeRegister\",SUM(tt.sum_register_distinct) as \"registerDistinct\",SUM(tt.sum_register_distinct_day) as \"registerDistinctDay\",SUM(tt.sum_login_day) as \"loginDay\",SUM(tt.sum_range_fee) as \"rangeFee\",SUM(tt.sum_new_fee) as \"newFee\",SUM(tt.sum_pay_device) as \"payDevice\",SUM(tt.sum_total_fee) as \"totalFee\",SUM(tt.sum_total_pay_device) as \"totalPayDevice\",SUM(ttd.D1) as \"d1\",SUM(ttd.D2) as \"d2\",SUM(ttd.D7) as \"d7\" "
			+"FROM (SELECT date(ods_time) AS \"time\",promo_id,channel_id,agent_id,app_id,sum(click) AS \"sum_click\",sum(click_valid) AS \"sum_click_valid\",sum(click_distinct) AS \"sum_click_distinct\",sum(click_distinct_day) AS \"sum_click_distinct_day\",sum(active_distinct) AS \"sum_active_distinct\", "
			+"sum(dau) AS \"sum_dau\",sum(active_register) AS \"sum_active_register\",sum(register_distinct) AS \"sum_register_distinct\",sum(register_distinct_day) AS \"sum_register_distinct_day\",sum(login_day) AS \"sum_login_day\",sum(range_fee) AS \"sum_range_fee\","
			+ "sum(new_fee) AS \"sum_new_fee\",sum(pay_device) AS \"sum_pay_device\",sum(total_fee) AS \"sum_total_fee\",sum(total_pay_device) AS \"sum_total_pay_device\" "
			+"FROM t_promo_curr GROUP BY app_id) tt LEFT JOIN"
			+"(SELECT app_id,count(CASE WHEN login_count = 1 THEN TRUE ELSE NULL END) AS \"D1\",count(CASE WHEN login_count = 2 THEN TRUE ELSE NULL END) AS \"D2\",count(CASE WHEN login_count = 7 THEN TRUE ELSE NULL END) AS \"D7\" "
			+"FROM t_retained GROUP BY app_id ) ttd ON tt.app_id = ttd.app_id "
			+"where 1=1 ";
	
	private static final String SQL_GET_PROMO_CURR = "SELECT tb.promo_id as \"promoId\",tb.channel_id as \"channelId\",tb.agent_id as \"agentId\",tb.app_id as \"appId\",tb.odsTime as \"odsTime\",sum(tb.click) as \"click\",sum(tb.click_valid) as \"clickValid\",sum(tb.click_distinct) as \"clickDistinct\" ,sum(tb.click_distinct_day) AS \"clickDistinctDay\",sum(tb.active_distinct) as \"activeDistinct\" ,sum(tb.active_register) as \"activeRegister\",sum(tb.register_distinct) as \"registerDistinct\",sum(tb.register_distinct_day) as \"registerDistinctDay\",sum(tb.login_day) as \"loginDay\",sum(tb.new_fee) as \"newFee\",sum(tb.pay_device) as \"payDevice\",sum(tb.total_fee) as \"totalFee\",sum(tb.total_pay_device) as \"totalPayDevice\",sum(tt.d1) as \"D1\",sum(tt.d2) as \"D2\",sum(tt.d7) as \"D7\",sum(tt.d30) as \"D30\",tpo.name as \"promoName\",tcl.name as \"channelName\",tat.name as \"agentName\",tap.name as \"appName\" FROM \n" +
			"(SELECT promo_id,channel_id,agent_id,app_id,DATE_FORMAT(ods_time, '%y-%m-%d') AS 'odsTime',SUM(click) as 'click',sum(click_valid) as 'click_valid',SUM(click_distinct) as 'click_distinct',SUM(click_distinct_day) as 'click_distinct_day',\n" +
			"SUM(active_distinct) AS 'active_distinct',SUM(active_register) as \"active_register\",SUM(register_distinct)as 'register_distinct',SUM(register_distinct_day)as 'register_distinct_day',SUM(login_day)as 'login_day',\n" +
			"SUM(new_fee)as 'new_fee',SUM(pay_device)as 'pay_device',SUM(total_fee)as 'total_fee',SUM(total_pay_device)as 'total_pay_device' FROM t_promo_curr GROUP BY promo_id,odsTime) tb\n" +
			"LEFT JOIN (SELECT count(CASE WHEN login_count = 1 THEN TRUE ELSE NULL END) AS 'd1',count(CASE WHEN login_count = 2 THEN TRUE ELSE NULL END) AS 'd2',count(CASE WHEN login_count = 7 THEN TRUE ELSE NULL END) AS 'd7',count(CASE WHEN login_count = 30 THEN TRUE ELSE NULL END) AS 'd30',promo_id,DATE_FORMAT(last_login_time, '%y-%m-%d') AS 'time',login_count FROM t_retained trd LEFT JOIN t_click tck ON trd.ip = tck.ip AND trd.imei = tck.imei GROUP BY promo_id,time,trd.login_count) tt\n" +
			"ON tb.promo_id = tt.promo_id AND tb.odsTime = tt.time LEFT JOIN t_promo tpo ON tb.promo_id = tpo.id LEFT JOIN t_channelurl tcl ON tb.channel_id = tcl.id LEFT JOIN t_agent tat ON tb.agent_id = tat.id LEFT JOIN t_app tap ON tb.app_id = tap.id  where 1=1 ";
	
	
	
	
	
	//注收比
	 private static final String SQL_GET_INCOME = "SELECT tb.click as \"click\",tb.click_distinct as \"clickDistinct\", tb.click_distinct_day as \"clickDistinctDay\",tb.active_distinct as \"activeDistinct\",tb.register_distinct as \"registerDistinct\","
			 + "(SELECT COUNT(DISTINCT ta.imei,ta.idfa ) FROM t_app_pay ta) as \"pay\" ,"
			 + "";
	 
	 private  static final String SQL_GET_WHERE = "select min(tb.create_time) as \"createTime\",tap.name as \"appName\",tap.app_key as \"appKey\",tcl.name as \"channelName\",tpo.name as \"promoName\",min(tas.when) as \"startTime\"  "			 
			 +"FROM t_click tb LEFT JOIN t_app tap ON tb.app_id = tap.id "
			 +"LEFT JOIN t_channel tcl ON tb.channel_id = tcl.id "
			 +"LEFT JOIN t_promo tpo ON tb.promo_id = tpo.id "
			 +"LEFT JOIN t_app_startup tas ON (case when tas.app_type = 'Android' then tas.imei = tb.imei else tas.idfa = tb.idfa end) "
			 +"WHERE 1=1 ";
	
	public List<Map<String, Object>> getList(TPromoCurr tPromoCurr, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_PROMO_CURR, tPromoCurr, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, null, " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(TPromoCurr tPromoCurr, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, tPromoCurr, params);
		
		return getResultListTotalCount(sqlParams);
	}
	
	@Override
	public List<Map<String, Object>> getEquipmentFrom(Map<String, Object> params) {
		// TODO Auto-generated method stub
		SqlParams sqlParams = genEquipmentWhere(SQL_GET_WHERE, params);
		return getResultList(sqlParams);
	}
	
	

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, TPromoCurr tPromoCurr, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		
		/*if (tPromoCurr != null && tPromoCurr.getAppId() != null) {
			sqlParams.querySql.append(" AND tt.app_id = :appId ");
			sqlParams.paramsList.add("appId");
			sqlParams.valueList.add(tPromoCurr.getAppId());
		}*/
		
		if (!StringUtils.isBlank(MapUtils.getString(params, "createDateBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "createDateEnd"))) {
            sqlParams.querySql.append(" AND odsTime between :createDateBegin AND :createDateEnd ");
            sqlParams.paramsList.add("createDateBegin");
            sqlParams.paramsList.add("createDateEnd");
            sqlParams.valueList.add(MapUtils.getString(params, "createDateBegin") + " 00:00:00");
            sqlParams.valueList.add(MapUtils.getString(params, "createDateEnd") + " 23:59:59");
        }
		
		if(!StringUtils.isBlank(MapUtils.getString(params, "dimensions"))){
			if(MapUtils.getString(params, "dimensions").equals("odsTime")){
				sqlParams.querySql.append("group by odsTime having 1 = 1");
			}else if(MapUtils.getString(params, "dimensions").equals("promoId")){
				sqlParams.querySql.append("group by promoId having 1 = 1");
			}else if (MapUtils.getString(params, "dimensions").equals("channelId")) {
				sqlParams.querySql.append("group by channelId having 1 = 1");
			}
			
			//sqlParams.paramsList.add("odsTime");
			//sqlParams.valueList.add(MapUtils.getString(params, "odsTime"));
			
			
		}
		
		
		
		if (tPromoCurr != null && tPromoCurr.getPromoId() != null) {
            //sqlParams.querySql.append(getLikeSql("tt.promo_id", ":promoId"));
			sqlParams.querySql.append("tb.promo_id = :promoId ");
			sqlParams.paramsList.add("promoId");
			sqlParams.valueList.add(tPromoCurr.getPromoId());
		}
		if (tPromoCurr != null && tPromoCurr.getChannelId() != null) {
			sqlParams.querySql.append(" AND tt.channel_id = :channelId ");
			sqlParams.paramsList.add("channelId");
			sqlParams.valueList.add(tPromoCurr.getChannelId());
		}
		if (tPromoCurr != null && tPromoCurr.getAgentId() != null) {
			sqlParams.querySql.append(" AND tt.agent_id = :agentId ");
			sqlParams.paramsList.add("agentId");
			sqlParams.valueList.add(tPromoCurr.getAgentId());
		}
		
        return sqlParams;
	}
	
	private SqlParams genEquipmentWhere(String sql, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		 
		if(!StringUtils.isBlank(MapUtils.getString(params, "equipmen"))){
			sqlParams.querySql.append(" AND tb.imei = :equipmen or tb.idfa = :equipmen");
			sqlParams.paramsList.add("equipmen");
			sqlParams.valueList.add(params.get("equipmen"));
		}
		
		
		return sqlParams;
	}

	
	
	
}