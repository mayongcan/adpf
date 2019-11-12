/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.oem.service.impl;

import com.adpf.advertiser.repository.AdvertisersRepository;
import com.adpf.oem.entity.AdvertisingOem;
import com.adpf.oem.repository.AdvertisingOemRepository;
import com.adpf.oem.service.AdvertisingOemService;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.RoleInfo;
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
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdvertisingOemServiceImpl implements AdvertisingOemService {

    @Autowired
    private AdvertisingOemRepository advertisingOemRepository;
    @Autowired
    private RoleInfoRepository roleInfoRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private AdvertisersRepository advertisersRepository;
    @Override
    public JSONObject getList(Pageable page, AdvertisingOem advertisingOem, Map<String, Object> params) {

        UserInfo userInfo = (UserInfo) MapUtils.getObject(params, "userInfo");
        List<String> userRoleName = roleInfoRepository.getUserRoleName(userInfo.getUserCode(), userInfo.getOrganizerId());
        if(userRoleName.get(0).equals("贴牌商")){
            params.put("creator",null);
        }else{
            params.put("creator",userInfo.getUserId());
        }
        List<Map<String, Object>> list = advertisingOemRepository.getList(advertisingOem, params, page.getPageNumber(), page.getPageSize());
        int count = advertisingOemRepository.getListCount(advertisingOem, params);
        List liatadd=new ArrayList<Map<String, Object>>();
        for (int i = 0; i <list.size() ; i++) {
           list.get(i).put("pid",0);
            List<Map<String, Object>> listOfOem = advertisersRepository.getListOfOem();
            for (int j = 0; j <listOfOem.size() ; j++) {
                String oemLoginid = findByUserCode(list.get(i).get("oemLoginid").toString()).getUserId().toString();
                if(oemLoginid.equals(listOfOem.get(j).get("differenceid"))){
                    listOfOem.get(j).put("pid",list.get(i).get("id"));
                    listOfOem.get(j).put("oemName",listOfOem.get(j).get("advertisersName"));
                    listOfOem.get(j).put("oemLinkman",listOfOem.get(j).get("advertisersContacts"));
                    listOfOem.get(j).put("oemPhone",listOfOem.get(j).get("advertisersIphone"));
                    listOfOem.get(j).put("oemLoginid",listOfOem.get(j).get("advLoginid"));
                    listOfOem.get(j).put("oemPassword",listOfOem.get(j).get("advertisersPassword"));
                    listOfOem.get(j).put("oemName",listOfOem.get(j).get("advertisersName"));
                    listOfOem.get(j).put("oemState",listOfOem.get(j).get("advertisersState"));
                    listOfOem.get(j).put("oemRole",listOfOem.get(j).get("advertisersRoleid"));
                    listOfOem.get(j).put("creator",listOfOem.get(j).get("differenceid"));

                    liatadd.add(listOfOem.get(j));
                }
            }
        }
        list.addAll(liatadd);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);
    }

    //根据登录账号获取userid
    public UserInfo findByUserCode(String usercode) {
        List<UserInfo> list = userInfoRepository.findByUserCode(usercode);
        if (list == null || list.size() == 0) return null;
        else return list.get(0);
    }



    @Override
    public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
        AdvertisingOem advertisingOem = (AdvertisingOem) BeanUtils.mapToBean(params, AdvertisingOem.class);
        //插入数据
        advertisingOem.setCreator(userInfo.getUserId()+"");
        advertisingOemRepository.save(advertisingOem);
        //根据账号查询id
        UserInfo oemLoginid = findByUserCode((String) params.get("oemLoginid"));
        Long oemRoleId = Long.valueOf(params.get("oemRole").toString());
        //关联用户角色表
        roleInfoRepository.saveUserRole(oemLoginid.getUserId(), oemRoleId);
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdvertisingOem advertisingOem = (AdvertisingOem) BeanUtils.mapToBean(params, AdvertisingOem.class);
        AdvertisingOem advertisingOemInDb = advertisingOemRepository.findOne(advertisingOem.getId());

        UserInfo o = findByUserCode((String) params.get("oemLoginid"));
        if (advertisingOemInDb == null) {
            return RestfulRetUtils.getErrorMsg("51006", "当前编辑的对象不存在");
        }

        String newPassword = (String) params.get("oemPassword");
        String password = DigestUtils.md5DigestAsHex(newPassword.getBytes());

        //修改密码
        userInfoRepository.updatePasswordByUserId(password,o.getUserId());
        advertisingOem.setCreator(userInfo.getUserId()+"");
        BeanUtils.mergeBean(advertisingOem, advertisingOemInDb);
        advertisingOemRepository.save(advertisingOemInDb);
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public JSONObject del(String idsList, UserInfo userInfo) {
        String[] ids = idsList.split(",");
        for (int i = 0; i < ids.length; i++) {

            //根据获取登录账号获取id
            AdvertisingOem one = advertisingOemRepository.findOne(StringUtils.toLong(ids[i]));
            UserInfo oemLoginid = findByUserCode(one.getOemLoginid());

            Long roleId = Long.valueOf(one.getOemRole());
            //根据分配的roleid获取所有角色
//           List<Map<String, Object>> roleList = advertisingOemRepository.getRoleList(one.getOemRole());
            //删除数据库数据
            advertisingOemRepository.delete(StringUtils.toLong(ids[i]));
            //打破中间表
            roleInfoRepository.delUserRole(oemLoginid.getUserId(),roleId);
            //删除注册表
            userInfoRepository.delete(oemLoginid.getUserId());
        }
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

    //获取角色名称
    @Override
    public JSONObject getRoleNameByRoleid(Map<String, Object> params) {
        Long roleid = MapUtils.getLong(params, "roleid");
        RoleInfo one = roleInfoRepository.findOne(roleid);
        Map<String, Object> stringObjectMap = BeanUtils.beanToMap(one);
        List list=new ArrayList<Map<String, Object>>();
        list.add(stringObjectMap);
        return RestfulRetUtils.getRetSuccess(list);
    }

}
