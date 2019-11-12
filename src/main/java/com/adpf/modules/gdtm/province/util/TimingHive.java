package com.adpf.modules.gdtm.province.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.adpf.modules.gdtm.province.entity.AdpfProvinceSize;
import com.adpf.modules.gdtm.province.repository.AdpfProvinceSizeRepository;
import com.adpf.modules.gdtm.service.hive.impl.HiveServiceImpl;
import com.adpf.modules.gdtm.service.hive.util.HiveJDBCConnectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;

@Component
public class TimingHive {
	
	protected static final Logger logger = LogManager.getLogger(HiveServiceImpl.class);
	
	private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    
    @Autowired
    private AdpfProvinceSizeRepository adpfProvinceSizeRepository;
	
//	@Scheduled(cron="0/10 * * * * ?")
	public JSONObject countData() throws Exception {
		//hive连接池
		HiveJDBCConnectionUtils connection = new HiveJDBCConnectionUtils();
		stmt = connection.Connection();
    	JSONObject json = new JSONObject();
    	//统计省份
    	String sql = "SELECT province,count(1) as size from adpf_imei_tel_imeitranslate_province WHERE length(province)=3 and province like '8%' GROUP BY province";
		Map<String,Object> params = new HashMap<>();
		Map<String,Object> params1 = new HashMap<>();
		logger.info("查询省份"+ sql);
	    rs = stmt.executeQuery(sql);
	    Date date = new Date();
	    String s = "";
	    s = DateFormat.getDateInstance().format(date);
        logger.info("查询中.................................");
		while (rs.next()) {
			//获取时间
			params.put("province", rs.getString("province"));
			params.put("size", rs.getString("size"));
			params.put("date", s);
		    System.out.println(params);
			//查询表中最新时间
		    AdpfProvinceSize adpfProvinceSize = (AdpfProvinceSize) BeanUtils.mapToBean(params, AdpfProvinceSize.class);
		    List<Map<String,Object>> list = adpfProvinceSizeRepository.getDate(adpfProvinceSize, params);
		    System.out.println(list);
		    //判断表里面是否有数据
		    if(list.size()!=0) {
		    	String id = list.get(0).get("id").toString();
		        String province = list.get(0).get("province").toString();
			    params1.put("id", id);
			    params1.put("province", province);
				params1.put("size", rs.getString("size"));
				System.out.println("params1"+params1);
				
				AdpfProvinceSize adpfProvinceSize2 = (AdpfProvinceSize) BeanUtils.mapToBean(params1, AdpfProvinceSize.class);
				AdpfProvinceSize adpfProvinceSizeInDb = adpfProvinceSizeRepository.findOne(adpfProvinceSize2.getId());	
				if(adpfProvinceSizeInDb == null){
					return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
				}
				//合并两个javabean
				BeanUtils.mergeBean(adpfProvinceSize2, adpfProvinceSizeInDb);
				adpfProvinceSizeRepository.save(adpfProvinceSizeInDb);
			    logger.info("更新数据");
		    }
	        //最新时间和当天时间不等就插入
		    if(list.size()==0){	    	
		        //插入信息
		    	AdpfProvinceSize adpfProvinceSize2 = (AdpfProvinceSize) BeanUtils.mapToBean(params, AdpfProvinceSize.class);
		        adpfProvinceSizeRepository.save(adpfProvinceSize2);
				logger.info("数据为空，插入");
		    }
		}
		if ( rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
	    return json;
	}
}
