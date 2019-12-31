package com.adpf.tracking.API;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adpf.modules.gdtm.util.StringUtils;
import com.adpf.tracking.entity.Ick;
import com.adpf.tracking.entity.TPromo;
import com.adpf.tracking.repository.IckRepository;
import com.adpf.tracking.repository.TPromoCurrRepository;
import com.adpf.tracking.repository.TPromoRepository;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;

@RestController
@RequestMapping("/api/adpf")
public class restful {
	
	@Autowired
    private IckRepository ickRepository;
	
	@Autowired
	private TPromoRepository TPromoRepository;
	
	@RequestMapping(value="/tk/{id:^[1-9]\\d*$}",method=RequestMethod.GET)
	public JSONObject getList(HttpServletRequest request, @RequestParam Map<String, Object> params,@PathVariable Long id) throws UnsupportedEncodingException{
		System.out.println("................................................");
		JSONObject json = new JSONObject();	
		try {				
			TPromo tPromo = TPromoRepository.findOne(id);
			params.put("promoId",tPromo.getId());
			params.put("channelId",tPromo.getChannelId());
			params.put("agentId", tPromo.getAnentId());
			params.put("appId", tPromo.getAppId());
			String aa = (String) params.get("ua");
		    if(aa!=null){
				 String str = new String(aa.getBytes(), "UTF-8");
				 str = URLDecoder.decode(str, "UTF-8");
				 params.put("ua", str); 
			 }			 
			 params.put("createTime", new Date());
			 Ick ick = (Ick) BeanUtils.mapToBean(params, Ick.class);
			 ickRepository.save(ick);
			 json.put("state", 0);
			 json.put("msg", "SUCCESS");
		} catch (Exception e) {
			// TODO: handle exception
			json.put("state", -1);
			json.put("msg", "FAIL");
			
		}
		
		 
		 return json;
	}
	
	@RequestMapping(value="/gl/{id:^[1-9]\\d*$}",method=RequestMethod.GET)
	public void general(HttpServletRequest request, HttpServletResponse response,@PathVariable Long id,@RequestParam Map<String, Object> params) throws IOException{
		JSONObject json = new JSONObject();		
		TPromo tPromo = TPromoRepository.findOne(id);
		params.put("promoId",tPromo.getId());
		params.put("channelId",tPromo.getChannelId());
		params.put("agentId", tPromo.getAnentId());
		params.put("appId", tPromo.getAppId());
		params.put("createTime", new Date());
		
		//String referrer = request.getHeader("Referer");
        //System.out.println(referrer);
        /*String remoteAddr = request.getRemoteAddr();
        if(remoteAddr.equals("0:0:0:0:0:0:0:1")){
            System.out.println("您的ip地址为：127.0.0.1");
        }else{
            System.out.println("您的ip地址为：" + remoteAddr);
        }*/
		String ip = request.getHeader("x-forwarded-for");
		if(!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
			 int index = ip.indexOf(",");
			 if (index != -1){
				 ip = ip.substring(0, index);
			 }
		}

		
        params.put("ip", ip);
        
        
        String requestHeader = request.getHeader("User-Agent");
        int index_one = requestHeader.indexOf("(");
        String requestBody = requestHeader.substring(index_one+1);
        String userInfo = requestBody.substring(0, requestBody.indexOf(")"));
        String[] userInfoList = userInfo.split(";");
        int length = userInfoList.length;
        String os = userInfoList[0];
        String mobileInfo = userInfoList[length - 1];
        if(os.equals("Windows NT 6.1")){
            System.out.println("您的操作系统为：windows7");
        }else{
            System.out.println("您的操作系统为：" + os);
        }
        int index = mobileInfo.indexOf("/");
        if(index > 0){
            mobileInfo = mobileInfo.substring(0, mobileInfo.indexOf("/") - 5);
            System.out.println("您的手机型号为：" + mobileInfo);
        }
        params.put("ua", mobileInfo);
        //获取推广活动中的落地页
        String url = tPromo.getLandingUrl().trim();
        if(!url.startsWith("http")){
        	url= "http://"+url;
        }
        Ick ick = (Ick) BeanUtils.mapToBean(params, Ick.class);
		ickRepository.save(ick);
		System.out.println("落地页："+url);
		response.sendRedirect(url);

		
	}
	
	

	

}
