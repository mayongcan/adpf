package com.adpf.tracking.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;


import com.adpf.tracking.entity.TApp;
import com.adpf.tracking.repository.TAppRepository;
import com.gimplatform.core.utils.RedisUtils;

public class RedisKey implements ApplicationRunner {
	
	
	@Autowired
	private TAppRepository tAppRepository; 
	
	
	public void writeKey(){
	List<TApp>apps = tAppRepository.findAll();
	 for(int i = 0; i <= apps.size(); i++){
		String key = apps.get(i).getAppKey();
		String appId = apps.get(i).getId().toString();
		RedisUtils.set(appId, key, 0);
	 }
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		writeKey();
	}

	
}
