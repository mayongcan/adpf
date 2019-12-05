package com.adpf.tracking.API;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;

import org.apache.commons.collections.MapUtils;
import org.apache.hadoop.hive.serde.serdeConstants;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adpf.tracking.entity.Ick;
import com.adpf.tracking.entity.TApp;
import com.adpf.tracking.entity.TAppLogin;
import com.adpf.tracking.entity.TAppOrder;
import com.adpf.tracking.entity.TAppPay;
import com.adpf.tracking.entity.TAppRegister;
import com.adpf.tracking.entity.TAppStartup;
import com.adpf.tracking.repository.TAppLoginRepository;
import com.adpf.tracking.repository.TAppOrderRepository;
import com.adpf.tracking.repository.TAppPayRepository;
import com.adpf.tracking.repository.TAppRegisterRepository;
import com.adpf.tracking.repository.TAppRepository;
import com.adpf.tracking.repository.TAppStartupRepository;
import com.adpf.tracking.tools.AESTool;
import com.adpf.tracking.tools.util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;

@RestController
@RequestMapping("/api/adpf/cmo")
@Configuration
@PropertySource(value = {"classpath:app.properties"})
public class open {
	
    @Autowired
    private TAppStartupRepository TAppStartupRepository;
    @Autowired
    private TAppRegisterRepository TAppRegisterRepository;
    @Autowired
    private TAppLoginRepository TAppLoginRepository;
    @Autowired
    private TAppPayRepository TAppPayRepository;
    @Autowired
    private TAppOrderRepository TAppOrderRepository;
    @Autowired
    private TAppRepository TAppRepository;
    
//    @Value("${app.secretkey}")
//    private String secretkey;
	
	@RequestMapping(value="/getAppInfo",method=RequestMethod.POST)	
	public JSONObject getAppInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
		
		JSONObject json = new JSONObject();
		//String dnContext = util.AESDncode("0123456789012345", MapUtils.getString(params, "context"));
		try {
			String what = MapUtils.getString(params, "what");
			System.out.println("........"+what);			
			String secretkey = TAppRepository.findOne(MapUtils.getLong(params, "appid")).getAppKey();
			System.out.println("........"+secretkey);
			System.out.println(MapUtils.getString(params, "context"));
			//String dnContext = util.AESDncode(secretkey.trim(), MapUtils.getString(params, "context"));
			byte[] decryptFrom = AESTool.parseHexStr2Byte(MapUtils.getString(params, "context"));
			byte[] decryptResult = AESTool.decrypt(decryptFrom, secretkey.trim());
			String dnContext = new String(decryptResult);
			System.out.println(dnContext);
			Map<String, Object> contextMap = JSONObject.parseObject(dnContext);
			Map<String, Object>mapEntity = new HashMap<String, Object>();
			
			
			mapEntity.put("when1", params.get("when"));
			mapEntity.put("appId", params.get("appid"));
			mapEntity.put("appKey", params.get("appkey"));
			mapEntity.put("appType", params.get("apptype"));
			mapEntity.put("androidId",contextMap.get("_androidid"));
			mapEntity.put("idfa",contextMap.get("_idfa"));
			mapEntity.put("ip",contextMap.get("_ip"));
			mapEntity.put("imei",contextMap.get("_imei"));
			mapEntity.put("accountId",contextMap.get("_accountid"));
			mapEntity.put("createTime",new Date());
			if("startup".equals(what)){
				System.out.println("进入。。。。。。。。。。。。。。。");
				//TAppStartup tAppStartup = new TAppStartup();	
				TAppStartup tAppStartup =(TAppStartup) BeanUtils.mapToBean(mapEntity,TAppStartup.class );
				System.out.println("................."+tAppStartup);
				TAppStartupRepository.save(tAppStartup);
				
			}else if ("register".equals(what)) {
				TAppRegister tAppRegister = (TAppRegister)BeanUtils.mapToBean(mapEntity, TAppRegister.class);
				TAppRegisterRepository.save(tAppRegister);
				
			}else if("login".equals(what)){
				TAppLogin tAppLogin = (TAppLogin)BeanUtils.mapToBean(mapEntity, TAppLogin.class);
				TAppLoginRepository.save(tAppLogin);
				
			}else if("pay".equals(what)){
				mapEntity.put("orderId", contextMap.get("_orderid"));
				mapEntity.put("payType", contextMap.get("_paytype"));
				mapEntity.put("currencyType", contextMap.get("_currencytype"));
				mapEntity.put("free", contextMap.get("_fee"));
				TAppPay tAppPay = (TAppPay)BeanUtils.mapToBean(mapEntity, TAppPay.class);
				TAppPayRepository.save(tAppPay);			
			}else if("order".equals(what)) {
				mapEntity.put("orderId", contextMap.get("_orderid"));
				mapEntity.put("payType", contextMap.get("_paytype"));
				//mapEntity.put("currencyType", contextMap.get("_currencytype"));
				mapEntity.put("fee", contextMap.get("_fee"));
				TAppOrder tAppOrder =(TAppOrder) BeanUtils.mapToBean(mapEntity, TAppOrder.class);
				TAppOrderRepository.save(tAppOrder);
			}else {
				
			}
			json.put("status", 0);
		} catch (Exception e) {
			// TODO: handle exception
			json.put("status", "fail");
			
		}
	
		return json;
		
	}
	@RequestMapping(value="/getAppInfo1",method=RequestMethod.POST)	
	public JSONObject getAppInfo1(HttpServletRequest request){
		TAppStartup tAppStartup = new TAppStartup();
		tAppStartup.setAccountId("123");
		TAppStartupRepository.save(tAppStartup);
		return null;
	}

}
