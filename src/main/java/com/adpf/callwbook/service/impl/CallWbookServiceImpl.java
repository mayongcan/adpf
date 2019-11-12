/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.callwbook.service.impl;

import com.adpf.advertiser.repository.AdvertisersRepository;
import com.adpf.callwbook.entity.CallWbook;
import com.adpf.callwbook.repository.CallWbookRepository;
import com.adpf.callwbook.service.CallWbookService;
import com.adpf.callwbook.util.MySqlUtil;
import com.adpf.wbook.entity.WasteBook;
import com.adpf.wbook.repository.WasteBookRepository;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.repository.RoleInfoRepository;
import com.gimplatform.core.repository.UserInfoRepository;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CallWbookServiceImpl implements CallWbookService {

    @Autowired
    private CallWbookRepository callWbookRepository;

    @Autowired
    private AdvertisersRepository advertisersRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private RoleInfoRepository roleInfoRepository;

    @Autowired
    private WasteBookRepository wasteBookRepository;
    @Override
    public JSONObject getList(Pageable page, CallWbook callWbook, Map<String, Object> params) {

        UserInfo userInfo = (UserInfo) MapUtils.getObject(params, "userInfo");
        List<String> userRoleName = roleInfoRepository.getUserRoleName(userInfo.getUserCode(), userInfo.getOrganizerId());
        if (userRoleName.get(0).equals("代理商") || userRoleName.get(0).equals("二级代理商")) {
            params.put("differenceid", userInfo.getUserId());
        }
        String userCode = params.get("advid").toString();
        if (!userCode.equals("") && userCode != null) {
            List<UserInfo> advLoginid = userInfoRepository.findByUserCode(userCode);
            params.put("advid", advLoginid.get(0).getUserId());
            params.put("differenceid", 0);
        }
        //如果是企业登录，覆盖advid
        if (userRoleName.get(0).equals("企业")) {
            params.put("advid", userInfo.getUserId());
            params.put("differenceid", 0);
        }
        List<Map<String, Object>> list = callWbookRepository.getList(callWbook, params, page.getPageNumber(), page.getPageSize());
        int count = callWbookRepository.getListCount(callWbook, params);

        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("callTime", (new BigDecimal(list.get(i).get("callTime").toString()).divide(new BigDecimal("60"))) + " 分");
            list.get(i).put("callTimeToAll", list.get(i).get("callTime"));
        }
        return RestfulRetUtils.getRetSuccessWithPage(list, count);
    }

    @Override
    public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
        CallWbook callWbook = (CallWbook) BeanUtils.mapToBean(params, CallWbook.class);

        List<Map<String, Object>> listvnb = getVenusdb();
        for (int i = 0; i < listvnb.size(); i++) {
            UserInfo userid = userInfoRepository.getOne(Long.valueOf(listvnb.get(i).get("USERID").toString()));
        }

        callWbookRepository.save(callWbook);
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        CallWbook callWbook = (CallWbook) BeanUtils.mapToBean(params, CallWbook.class);
        CallWbook callWbookInDb = callWbookRepository.findOne(callWbook.getId());
        if (callWbookInDb == null) {
            return RestfulRetUtils.getErrorMsg("51006", "当前编辑的对象不存在");
        }
        //合并两个javabean
        BeanUtils.mergeBean(callWbook, callWbookInDb);
        callWbookRepository.save(callWbookInDb);
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public JSONObject del(String idsList, UserInfo userInfo) {
        String[] ids = idsList.split(",");
        for (int i = 0; i < ids.length; i++) {
            callWbookRepository.delete(StringUtils.toLong(ids[i]));
        }
        return RestfulRetUtils.getRetSuccess();
    }

    //获取外呼数据
    public List<Map<String, Object>> getVenusdb() {
        ResultSet resultSet = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            String maxTime = "";
            String miniTime = "";

            ResultSet resultSet1 = new MySqlUtil().executeMaxTime();
            ResultSetMetaData metaData = resultSet1.getMetaData();
            while (resultSet1.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    maxTime = resultSet1.getObject(i).toString();
                }
            }
            List<Map<String, Object>> dattaTime = callWbookRepository.getMaxTime();
            if (dattaTime.get(0).get("maxTime") == null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                miniTime = df.format(df.parse("2000-01-01 00:00:00"));
            } else {
                miniTime = dattaTime.get(0).get("maxTime").toString();
            }
            resultSet = new MySqlUtil().executeQueryByTime(miniTime, maxTime);
            ResultSetMetaData md = resultSet.getMetaData();
            while (resultSet.next()) { //循环输出结果集
                Map<String, Object> rowData = new HashMap<String, Object>();
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    rowData.put(md.getColumnName(i), resultSet.getObject(i));
                }
                list.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    //将外呼数据插入数据库
    public JSONObject addOrUpday() {
        List<Map<String, Object>> listvnb = getVenusdb();
        if (listvnb == null || listvnb.size() != 0) {
            for (int i = 0; i < listvnb.size(); i++) {
                List<Map<String, Object>> listAdv = advertisersRepository.getListBySeatidAndCallId(listvnb.get(i));
                if (listAdv == null || listAdv.size() == 0) {
                    continue;
                }
                BigDecimal callMoney = (new BigDecimal(listvnb.get(i).get("callTime").toString()).divide(new BigDecimal("60"))).multiply(new BigDecimal(listAdv.get(0).get("costCall").toString()));
                CallWbook callWbook = new CallWbook();
                callWbook.setAdvId(listAdv.get(0).get("userID").toString());
                callWbook.setCallId(listvnb.get(i).get("TELNO").toString());
                callWbook.setSeatId(listvnb.get(i).get("AGENTID").toString());
                callWbook.setCallPrice((callMoney.setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
                callWbook.setCallTime(listvnb.get(i).get("callTime").toString());
                try {
                    callWbook.setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(listvnb.get(i).get("maxDate").toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                callWbookRepository.save(callWbook);

                //更新金额
                BigDecimal finalMoney = new BigDecimal(listAdv.get(0).get("balance").toString()).subtract(callMoney);
                advertisersRepository.udpateBalance(listAdv.get(0).get("advid").toString(), finalMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
                //updayAccounts(Long.parseLong(listAdv.get(0).get("advid").toString()), finalMoney);
                //保存流水
                WasteBook wb = new WasteBook();
                wb.setAdvId(Long.parseLong(listAdv.get(0).get("userID").toString()));
                wb.setDisbursement((callMoney.setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
                wb.setOperator("每日自动更新");
                wb.setOperationTime(new Date());
                wb.setRemark("外呼费用");
                //保存
                wasteBookRepository.save(wb);
            }
        }
        return RestfulRetUtils.getRetSuccess();
    }


}
