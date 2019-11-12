/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.advertiser.service.impl;

import com.adpf.advertiser.entity.Advertisers;
import com.adpf.advertiser.repository.AdvertisersRepository;
import com.adpf.advertiser.service.AdvertisersService;
import com.adpf.issue.entity.AdvIssue;
import com.adpf.issue.repository.AdvIssueRepository;
import com.adpf.seats.entity.AdvidSeatsid;
import com.adpf.seats.repository.AdvidSeatsidRepository;
import com.adpf.wbook.entity.WasteBook;
import com.adpf.wbook.repository.WasteBookRepository;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.RoleInfo;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.repository.RoleInfoRepository;
import com.gimplatform.core.repository.UserInfoRepository;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AdvertisersServiceImpl implements AdvertisersService {

    protected static final Logger logger = LogManager.getLogger(AdvertisersServiceImpl.class);

    @Autowired
    private AdvertisersRepository advertisersRepository;

    //系统模块的用户块
    @Autowired
    private RoleInfoRepository roleInfoRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private WasteBookRepository wasteBookRepository;
    @Autowired
    private AdvidSeatsidRepository advidSeatsidRepository;
    @Autowired
    private AdvIssueRepository advIssueRepository;


    @Override
    public JSONObject getList(Pageable page, Advertisers advertisers, Map<String, Object> params, UserInfo userInfo) {
        String userid = userInfo.getUserId() + "";
        List<Map<String, Object>> list = advertisersRepository.getList(advertisers, params, page.getPageNumber(), page.getPageSize(), userid);
        int count = advertisersRepository.getListCount(advertisers, params, userid);

        for (int i = 0; i < list.size(); i++) {
            UserInfo advLoginid = findByUserCode(list.get(i).get("advLoginid") + "");
            list.get(i).put("advId", advLoginid.getUserId());
            System.out.println(list.get(i));
        }

        return RestfulRetUtils.getRetSuccessWithPage(list, count);
    }

    @Override
    public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
        Advertisers advertisers = (Advertisers) BeanUtils.mapToBean(params, Advertisers.class);
        //a表关联s表查询归属广告主
        advertisers.setDifferenceid(userInfo.getUserId() + "");
        advertisers.setAdvBalance(new BigDecimal("0"));
        //插入数据
        advertisersRepository.save(advertisers);
        //根据账号查询id
        //advLoginid是的登录账号的字段
        UserInfo advLoginid = findByUserCode(params.get("advLoginid").toString());
//        MapUtils.getLong(params)
        Long advRoleId = Long.valueOf(params.get("advertisersRoleid").toString());
        //关联用户角色表
        logger.info("测试数据，用户id：" + advLoginid.getUserId() + "," + "角色id：" + advRoleId);
//        System.out.println("测试数据，用户id："+advLoginid.getUserId()+","+"角色id："+ advRoleId);

        roleInfoRepository.saveUserRole(advLoginid.getUserId(), advRoleId);
        return RestfulRetUtils.getRetSuccess();
    }

    //根据登录账号获取userid
    public UserInfo findByUserCode(String usercode) {
        List<UserInfo> list = userInfoRepository.findByUserCode(usercode);
        if (list == null || list.size() == 0) return null;
        else return list.get(0);
    }

    @Override
    public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        Advertisers advertisers = (Advertisers) BeanUtils.mapToBean(params, Advertisers.class);
        Advertisers advertisersInDb = advertisersRepository.findOne(advertisers.getId());
        UserInfo o = findByUserCode((String) params.get("advLoginid"));
        if (advertisersInDb == null) {
            return RestfulRetUtils.getErrorMsg("51006", "当前编辑的对象不存在");
        }

        String newPassword = (String) params.get("advertisersPassword");
        String password = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        //修改密码
        userInfoRepository.updatePasswordByUserId(password, o.getUserId());
        //advertisers.setDifferenceid(userInfo.getUserId() + "");
        //合并两个javabean
        BeanUtils.mergeBean(advertisers, advertisersInDb);
        //System.out.println(advertisersInDb);
        advertisersRepository.save(advertisersInDb);
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public JSONObject del(String idsList, UserInfo userInfo) {
        String[] ids = idsList.split(",");
        for (int i = 0; i < ids.length; i++) {
            //根据获取登录账号获取id
            Advertisers one = advertisersRepository.findOne(StringUtils.toLong(ids[i]));
            UserInfo advLoginid = findByUserCode(one.getAdvLoginid());
            //getAdvertisersRoleid 获取我自己的表的角色ID
            Long roleId = Long.valueOf(one.getAdvertisersRoleid());

            //删除相应的坐席
            List<Map<String, Object>> listByAdvId = advidSeatsidRepository.getListByAdvId(advLoginid.getUserId().toString());
            for (int j = 0; j < listByAdvId.size(); j++) {
                advidSeatsidRepository.delete(Long.parseLong(listByAdvId.get(j).get("id").toString()));
            }
            //删除数据库数据
            advertisersRepository.delete(StringUtils.toLong(ids[i]));

            //打破中间表
            roleInfoRepository.delUserRole(advLoginid.getUserId(), roleId);

            //删除注册的那张基础信息表
            userInfoRepository.delete(advLoginid.getUserId());

        }
        return RestfulRetUtils.getRetSuccess();
    }

    //充值
    @Override
    public JSONObject topUp(Map<String, Object> params, UserInfo userInfo) {

        String money = params.get("advBalance").toString();
        if (money == null || StringUtils.isBlank(money)) {
            return RestfulRetUtils.getErrorMsg("51007", "输入金额有误");
        }
        //查询出数据库中的金额
        Long id = Long.valueOf(params.get("id").toString());
        Advertisers one = advertisersRepository.getOne(id);
        //账号余额
        BigDecimal myBalance = one.getAdvBalance();
        //充值金额
        BigDecimal myMoney = new BigDecimal(money);
        //相加
        BigDecimal newBalance = myBalance.add(myMoney);
        Advertisers advertisers = (Advertisers) BeanUtils.mapToBean(params, Advertisers.class);
        advertisers.setAdvBalance(newBalance.setScale(2, BigDecimal.ROUND_HALF_UP));
        advertisers.setDifferenceid(userInfo.getUserId().toString());
        //合并两个javabean
        BeanUtils.mergeBean(advertisers, one);
        //再更新
        advertisersRepository.save(advertisers);
        //添加流水
        WasteBook wb = new WasteBook();
        UserInfo u = findByUserCode((String) params.get("advLoginid"));
        //企业id
        wb.setAdvId(u.getUserId());
        wb.setIncome((new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
        wb.setOperator(userInfo.getUserName());
        wb.setOperationTime(new Date());
        wb.setRemark("充值余额");
        //保存
        wasteBookRepository.save(wb);

        return RestfulRetUtils.getRetSuccess();
    }

    //更新金额
    public void updayAccounts(Long id, BigDecimal money) {
        advertisersRepository.udpateBalance(id.toString(), money);
    }

    //开通坐席
    @Override
    public JSONObject dredge(Map<String, Object> params, UserInfo userInfo) {

        Long advid = Long.valueOf(params.get("advid").toString());
        Advertisers adv = advertisersRepository.getOne(advid);
        BigDecimal stseatPrice = new BigDecimal(adv.getAdvCostseat());

        //判断余额是否足够
        if (adv.getAdvBalance().compareTo(stseatPrice) == -1) {
            BigDecimal subtract = adv.getAdvBalance().subtract(stseatPrice);

            return RestfulRetUtils.getErrorMsg("111", "当前广告主余额不足，差" + subtract.abs() + "元");
        }
        //保存席位信息
        AdvidSeatsid advidseatsid = new AdvidSeatsid();
        UserInfo advLoginid = findByUserCode((String) params.get("userCode"));
        String seatsId = params.get("seatsId").toString();

        List<Map<String, Object>> list = advidSeatsidRepository.getListBySeatsId(seatsId);
        if (list != null && list.size() != 0) {
            return RestfulRetUtils.getErrorMsg("222", "当前坐席号已经存在，请重新选输入");
        }
        advidseatsid.setAdvId(advLoginid.getUserId().toString());
        advidseatsid.setSeatsId(seatsId);
        advidSeatsidRepository.save(advidseatsid);
        //更新企业主金额
        updayAccounts(advid, (adv.getAdvBalance().subtract(stseatPrice)).setScale(2, BigDecimal.ROUND_HALF_UP));
        //保存流水
        WasteBook wb = new WasteBook();
        wb.setAdvId(advLoginid.getUserId());
        wb.setDisbursement((new BigDecimal(adv.getAdvCostseat()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
        wb.setOperator(userInfo.getUserName());
        wb.setOperationTime(new Date());
        wb.setRemark("开通席位");
        //保存
        wasteBookRepository.save(wb);
        return RestfulRetUtils.getRetSuccess();
    }

    //获取角色
    @Override
    public JSONObject getRoleIdByRoleName(Map<String, Object> params) {
        String roleName = params.get("roleName").toString();
        Long tenantsId = Long.valueOf(params.get("tenantsId").toString());
        List<RoleInfo> role = roleInfoRepository.findByRoleNameAndTenantsIdAndIsValid(roleName, tenantsId, Constants.IS_VALID_VALID);
        return RestfulRetUtils.getRetSuccess(role);
    }

    //下发
    @Override
    public JSONObject issue(Map<String, Object> params, UserInfo userInfo) {

        Long advid = MapUtils.getLong(params, "advId");
        Advertisers adv = advertisersRepository.getOne(advid);
        //单价
        BigDecimal dataPrice = new BigDecimal(adv.getAvdCostdata());
        //数量
        BigDecimal amount = new BigDecimal(MapUtils.getString(params, "amount"));
        //最终花费
        BigDecimal endMoney = amount.multiply(dataPrice);
        //剩下金额
        BigDecimal reMoney = adv.getAdvBalance().subtract(endMoney);
        //判断余额是否足够
        if (adv.getAdvBalance().compareTo(endMoney) == -1) {

            return RestfulRetUtils.getErrorMsg("111", "当前广告主余额不足，差" + reMoney.abs() + "元");
        }
        UserInfo advLoginid = findByUserCode(MapUtils.getString(params, "userCode"));
        //保存下发数据
        AdvIssue iss = new AdvIssue();
        iss.setAdvId(advLoginid.getUserId() + "");
        iss.setAmount(MapUtils.getLong(params, "amount"));
        iss.setRemark(MapUtils.getString(params, "remark"));
        iss.setCreator(userInfo.getUserName());
        iss.setCreatTime(new Date());
        //System.out.println(iss);
        advIssueRepository.save(iss);
        //修改用户金额
        updayAccounts(advid, reMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
        //添加流水
        WasteBook wb = new WasteBook();
        wb.setAdvId(advLoginid.getUserId());
        wb.setDisbursement((endMoney.setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
        wb.setOperator(userInfo.getUserName());
        wb.setOperationTime(new Date());
        wb.setRemark("数据下发");
        //System.out.println(wb);
        wasteBookRepository.save(wb);

        return RestfulRetUtils.getRetSuccess();
    }

    //获取企业消耗
    @Override
    public JSONObject getStatisticsList(Map<String, Object> params, Pageable page) {

        UserInfo userInfo = (UserInfo) MapUtils.getObject(params, "userInfo");

        List<String> userRoleName = roleInfoRepository.getUserRoleName(userInfo.getUserCode(), userInfo.getOrganizerId());
        if (userRoleName.get(0).equals("代理商") || userRoleName.get(0).equals("二级代理商")) {
            params.put("differenceid", userInfo.getUserId());
        }
        if (userRoleName.get(0).equals("贴牌商")) {
            UserInfo tagent = findByUserCode(params.get("tagentId").toString());
            params.put("differenceid", tagent.getUserId());
        }
        if (userRoleName.get(0).equals("代理商") && params.get("tagentId") != null) {
            UserInfo tagent = findByUserCode(params.get("tagentId").toString());
            params.put("differenceid", tagent.getUserId());
        }
        //如果是贴牌商登录，覆盖advid
//        if(userRoleName.get(0).equals("贴牌商")){
//            params.put("advid",userInfo.getUserId());
//            params.put("differenceid",0);
//        }
        List<Map<String, Object>> list = advertisersRepository.getStatisList(params, page.getPageNumber(), page.getPageSize());
        int count = advertisersRepository.getStatisCount(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);
    }
}
