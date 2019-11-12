package com.adpf.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 配置验证过滤
 * 
 * @author zzd
 *
 */
@Configuration
@EnableWebSecurity
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.anonymous().disable().authorizeRequests().anyRequest().authenticated().and().csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 配置不拦截的API
		web.ignoring().antMatchers("/api/adpf/druidMonitor/**", "/api/adpf/web/**", "/api/adpf/inf/**","/api/adpf/userInfo/**","/api/adpf/wechat/**","/api/adpf/transfer/**","/api/adpf/dataServer/**","/api/adpf/datalogin/**","/api/adpf/webSocket/**","/api/adpf/weChatUser/**","/api/adpf/release/ThePublic/**","/api/adpf/qtn/window/getAllWindowInfo");
	}
}
