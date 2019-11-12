/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.advertiser.repository.impl;

import com.adpf.advertiser.entity.Advertisers;
import com.adpf.advertiser.repository.custom.AdvertisersRepositoryCustom;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import org.apache.commons.collections4.MapUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class AdvertisersRepositoryImpl extends BaseRepository implements AdvertisersRepositoryCustom {

    private static final String SQL_GET_LIST = "SELECT tb.id as \"id\", tb.advertisers_name as \"advertisersName\", tb.advertisers_contacts as \"advertisersContacts\", tb.advertisers_iphone as \"advertisersIphone\", tb.adv_loginid as \"advLoginid\", tb.advertisers_password as \"advertisersPassword\", tb.advertisers_dataprice as \"advertisersDataprice\", tb.advertisers_state as \"advertisersState\", tb.advertisers_roleid as \"advertisersRoleid\", tb.differenceid as \"differenceid\",tb.avd_costdata as \"avdCostdata\", tb.adv_costcall as \"advCostcall\", tb.adv_costseat as \"advCostseat\", tb.adv_balance as \"advBalance\" "

            + "FROM advertisers tb "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
            + "FROM advertisers tb "
            + "WHERE 1 = 1 ";


    private static final String SQL_GET_STATIS_LIST = "SELECT tb.id as \"id\",wb.adv_id as \"advId\",tb.advertisers_name as \"advName\", tb.adv_costseat as \"costSeat\", cast(sum(case WHEN wb.remark = '开通席位' THEN wb.disbursement ELSE 0 END)as decimal(15,2)) as \"totalSeat\", tb.avd_costdata as \"costData\", cast(sum(case WHEN wb.remark = '数据下发' THEN wb.disbursement ELSE 0 END)as decimal(15,2)) as \"totalData\", tb.adv_costcall as \"costCall\", cast(sum(case WHEN wb.remark = '外呼费用' THEN wb.disbursement ELSE 0 END)as decimal(15,2)) as \"totalCall\", cast(sum(wb.disbursement)as decimal(15,2)) as \"totalDisbursement\", cast(sum(wb.income)as decimal(15,2)) as \"totalIncome\" "
            + "from advertisers tb LEFT JOIN sys_user_info u on tb.adv_loginid = u.USER_CODE LEFT JOIN waste_book wb on u.USER_ID =wb.adv_id "
            + "WHERE 1 = 1 ";


    private static final String SQL_GET_STATIS_COUNT = "SELECT count(1) as \"count\" "
            + "from advertisers tb LEFT JOIN sys_user_info u on tb.adv_loginid = u.USER_CODE LEFT JOIN waste_book wb on u.USER_ID =wb.adv_id "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_LIST_SEATID = "SELECT ad.adv_costcall as \"costCall\",u.USER_ID as \"userID\",ad.id as\"advid\",ad.adv_balance as \"balance\" "
            + "from advertisers ad LEFT JOIN sys_user_info u on ad.adv_loginid = u.USER_CODE LEFT JOIN advid_seatsid st on u.USER_ID = st.adv_id "
            + "WHERE 1 = 1 ";


    private static final String SQL_UPDAY_BALANCE = "UPDATE advertisers set  ";

    public List<Map<String, Object>> getList(Advertisers advertisers, Map<String, Object> params, int pageIndex, int pageSize, String userid) {
        //生成查询条件
        SqlParams sqlParams = genListWhere(SQL_GET_LIST, advertisers, params, userid);
        //添加分页和排序
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.id DESC ", " \"id\" DESC ");
        return getResultList(sqlParams);
    }

    public int getListCount(Advertisers advertisers, Map<String, Object> params, String userid) {
        //生成查询条件
        SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, advertisers, params, userid);
        return getResultListTotalCount(sqlParams);
    }


    //统计报表列表
    @Override
    public List<Map<String, Object>> getStatisList(Map<String, Object> params, int pageIndex, int pageSize) {

        SqlParams sqlParams = getStatisWhere(SQL_GET_STATIS_LIST, params);
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " wb.adv_id DESC ", " \"advId\" DESC ");
        return getResultList(sqlParams);
    }

    //生成统计表查询条件
    @Override
    public int getStatisCount(Map<String, Object> params) {
        SqlParams sqlParams = getStatisWhere(SQL_GET_STATIS_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getListBySeatidAndCallId(Map<String, Object> stringObjectMap) {

        SqlParams sqlParams = getLIstBySeatId(SQL_GET_LIST_SEATID, stringObjectMap);

        return getResultList(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getListOfOem() {
        SqlParams sqlParams = getListofoem(SQL_GET_LIST);
        return getResultList(sqlParams);
    }

    @Override
    public int updayBalance(String id, BigDecimal newBalance) {

        SqlParams sqlParams = updayBalanceById(SQL_UPDAY_BALANCE, id, newBalance);
        return getResultListUnionAllTotalCount(sqlParams);
    }

    /**
     * 生成查询条件
     *
     * @param sql
     * @param params
     * @return
     */
    private SqlParams genListWhere(String sql, Advertisers advertisers, Map<String, Object> params, String userid) {
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append(sql);
        if (advertisers != null && !StringUtils.isBlank(advertisers.getAdvertisersName())) {
            sqlParams.querySql.append(getLikeSql("tb.advertisers_name", ":advertisersName"));
            sqlParams.paramsList.add("advertisersName");
            sqlParams.valueList.add(advertisers.getAdvertisersName());
        }
        if (userid != null && !StringUtils.isBlank(userid)) {
            sqlParams.querySql.append("and tb.differenceid = :userid");
            sqlParams.paramsList.add("userid");
            sqlParams.valueList.add(userid);
        }

        return sqlParams;
    }

    /**
     * 生成查询条件
     *
     * @param sql
     * @param params
     * @return
     */
    private SqlParams getStatisWhere(String sql, Map<String, Object> params) {
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append(sql);
        if (MapUtils.getString(params, "differenceid") != null && !StringUtils.isBlank(MapUtils.getString(params, "differenceid"))) {
            sqlParams.querySql.append("and tb.differenceid = :differenceid");
            sqlParams.paramsList.add("differenceid");
            sqlParams.valueList.add(MapUtils.getString(params, "differenceid"));
         if (!StringUtils.isBlank(MapUtils.getString(params, "operationTimeBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "operationTimeEnd"))) {
            sqlParams.querySql.append(" AND wb.operation_time between :operationTimeBegin AND :operationTimeEnd ");
            sqlParams.paramsList.add("operationTimeBegin");
            sqlParams.paramsList.add("operationTimeEnd");
            sqlParams.valueList.add(MapUtils.getString(params, "operationTimeBegin"));
            sqlParams.valueList.add(MapUtils.getString(params, "operationTimeEnd"));
            }
            sqlParams.querySql.append(" GROUP BY wb.adv_id");
        }

        return sqlParams;
    }

    //根据seatId查询
    private SqlParams getLIstBySeatId(String sql, Map<String, Object> params) {
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append(sql);
        if (MapUtils.getString(params, "AGENTID") != null && !StringUtils.isBlank(MapUtils.getString(params, "AGENTID"))) {
            sqlParams.querySql.append("and st.seats_id = :AGENTID");
            sqlParams.paramsList.add("AGENTID");
            sqlParams.valueList.add(MapUtils.getString(params, "AGENTID"));
            sqlParams.querySql.append(" GROUP BY u.USER_ID");
        }

        return sqlParams;
    }

    /**
     * 更新余额
     *
     * @param sql
     * @return
     */
    private SqlParams updayBalanceById(String sql, String id, BigDecimal newBalance) {
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append(sql);
        if (newBalance != null) {
            sqlParams.querySql.append("adv_balance = :balance");
            sqlParams.paramsList.add("balance");
            sqlParams.valueList.add(newBalance);
        }
        if (id != null && !StringUtils.isBlank(id)) {
            sqlParams.querySql.append("where id = :userid");
            sqlParams.paramsList.add("userid");
            sqlParams.valueList.add(id);
        }
        return sqlParams;
    }

    private SqlParams getListofoem(String sql) {
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append(sql);
        return sqlParams;
    }

}
