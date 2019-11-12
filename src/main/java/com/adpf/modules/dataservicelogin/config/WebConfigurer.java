package com.adpf.modules.dataservicelogin.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


@Configuration
public class WebConfigurer extends WebMvcConfigurerAdapter{
	
//	@Autowired
//	private LoginInterceptor loginInterceptor;

	@Bean
    public SecurityInterceptor getSecurityInterceptor(){
        return  new SecurityInterceptor();
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getSecurityInterceptor()).addPathPatterns("/api/adpf/datalogin/**").excludePathPatterns("/api/adpf/datalogin/getKaptchaCode","/api/adpf/datalogin/checkKaptchaCode","/api/adpf/datalogin/getUser","/api/adpf/datalogin/register");
		super.addInterceptors(registry);
	}
	
	 private class SecurityInterceptor extends HandlerInterceptorAdapter {
	        @Override
	        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws IOException{
	            HttpSession session = request.getSession();
	            if(session.getAttribute("username")!=null){
	            	System.out.println("1................");
	                return  true;
	            }
	            System.out.println("2................");
	            return false;
	        }
	   }
}
