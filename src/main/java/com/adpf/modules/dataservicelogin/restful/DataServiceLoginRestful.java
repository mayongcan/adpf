/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.dataservicelogin.restful;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.adpf.modules.dataservicelogin.entity.DataServiceLogin;
import com.adpf.modules.dataservicelogin.service.DataServiceLoginService;
import com.adpf.modules.gdtm.util.MD5Util;

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/adpf/datalogin")
public class DataServiceLoginRestful {

	protected static final Logger logger = LogManager.getLogger(DataServiceLoginRestful.class);
    
    @Autowired
    private DataServiceLoginService dataServiceLoginService;
    
    @Resource
    private Producer kaptchaProducer;

    /**
     * 配置参数
     * @return
     */
    @Bean(name = "kaptchaProducer")
    public DefaultKaptcha getKaptchaBean() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.border.color", "248,249,251");
        properties.setProperty("kaptcha.border.thickness", "1");
        properties.setProperty("kaptcha.background.clear.from", "248,249,251");
        properties.setProperty("kaptcha.background.clear.to", "248,249,251");
        properties.setProperty("kaptcha.textproducer.font.color", "88,88,88");
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        properties.setProperty("kaptcha.image.width", "125");
        properties.setProperty("kaptcha.image.height", "35");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.char.length", "5");
        properties.setProperty("kaptcha.textproducer.font.names", "Arial");
        properties.setProperty("kaptcha.textproducer.font.size", "22");
        properties.setProperty("kaptcha.textproducer.char.space", "8");
        properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    
	/**
	 * 用于记录打开日志
	 * @param request
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public JSONObject index(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}
	
	 /**
     * 获取验证码图片
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("getKaptchaCode")
    public ModelAndView getKaptchaCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        
        // 生成验证码文本
        String capText = kaptchaProducer.createText();
        System.out.println(capText);
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        logger.info("生成验证码内容:" + capText);
        // 利用生成的字符串构建图片
        BufferedImage bi = kaptchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);

        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    /**
     * 前端输入的验证码与生成的对比
     * @param request
     * @param response
     * @param kaptchaCode
     */
    @RequestMapping("checkKaptchaCode")
    public void checkKaptchaCode(HttpServletRequest request, HttpServletResponse response, @RequestParam("kaptchaCode") String kaptchaCode) {
        logger.info("页面输入验证码:" + kaptchaCode);

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        String generateCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String result = "";
        if (generateCode != null && generateCode.equals(kaptchaCode.toLowerCase())) {
            result = "{\"code\":\"success\"}";
        } else {
            result = "{\"code\":\"failure\"}";
        }
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            logger.error("检查验证码失败", e);
        }
        out.print(result);
        out.flush();
    }

	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getList",method=RequestMethod.GET)
	public JSONObject getList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
//			UserInfo userInfo = SessionUtils.getUserInfo();
//			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
//			else {
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
				DataServiceLogin dataServiceLogin = (DataServiceLogin)BeanUtils.mapToBean(params, DataServiceLogin.class);				
				json = dataServiceLoginService.getList(pageable, dataServiceLogin, params);
//			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 登录
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/getUser",method=RequestMethod.POST)
	public JSONObject getUserInfo(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		JSONObject json = new JSONObject();
		String username = (String)params.get("username");
		try{
			if(null==username)
				return RestfulRetUtils.getErrorMsg("100009", "用户名为空,请输入用户名！");
			json = dataServiceLoginService.userInfo(params);
			//用户名错误
			if("100005".equals(json.get("RetCode")))
				return json;
			//密码对比
			String password = json.getJSONArray("RetData").getJSONObject(0).get("password").toString();	
			if(!password.equals(MD5Util.MD5((String)params.get("password")+"adpf")))
				return RestfulRetUtils.getErrorMsg("100006", "密码错误");	
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51002","登录失败");
			logger.error(e.getMessage(), e);
		}
		String appkey = json.getJSONArray("RetData").getJSONObject(0).get("appkey").toString();
		HttpSession sessoin=request.getSession();
		sessoin.setAttribute("username", username);
		sessoin.setAttribute("appkey", appkey);
		System.out.println(sessoin.getId());
		return json;
	}
	
	/**
	 * 注册
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public JSONObject register(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			json = dataServiceLoginService.register(params);
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51002","注册失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 新增信息
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public JSONObject add(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
//			UserInfo userInfo = SessionUtils.getUserInfo();
//			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
//			else {
				json = dataServiceLoginService.add(params);
//			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51002","新增信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 编辑信息
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public JSONObject edit(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = dataServiceLoginService.edit(params, userInfo);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51003","编辑信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 删除信息
	 * @param request
	 * @param idsList
	 * @return
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public JSONObject del(HttpServletRequest request,@RequestBody String idsList){
		JSONObject json = new JSONObject();
		try {
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = dataServiceLoginService.del(idsList, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
}
