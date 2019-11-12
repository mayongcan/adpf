package com.adpf.modules.openam.config;

public class LoginboxConfig {
	// 公共参数
	// TODO 合作方申请的appId
	public static final String APP_ID = "8013417803";
	// TODO 合作方申请的appId对应的appSecret
	public static final String APP_SECRET = "9CZ6BIyADWeiwZ4foVRDPuYfPRU98nMc";
	// TODO 合作方自定义
	public static final String RETURN_URL = "http://www.test.com";
	public static final String FORMAT = "redirect";

	// Web登录框参数
	public static final String UNIFY_ACCOUNT_LOGIN_URL = "https://open.e.189.cn/api/logbox/oauth2/web/unifyAccountLogin.do";
	public static final int CLIENT_TYPE_WEB = 10010;
	public static final String VERSION_WEB = "v1.5";

	// WAP登录框参数
	public static final String AUTO_LOGIN_URL = "http://open.e.189.cn/api/logbox/oauth2/wap/autoLogin.do";
	public static final int CLIENT_TYPE_WAP = 20100;
	public static final String VERSION_WAP = "v1.1";
	// TODO 可不传
	public static final String LOGIN_TYPE = "";
	// TODO 可不传
	public static final String QA_URL = "";
	// TODO 可不传
	public static final String OTHER_LOGIN_URL = "";

	public static final String CHARSET = "UTF-8";
}