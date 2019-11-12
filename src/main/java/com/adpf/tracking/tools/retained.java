package com.adpf.tracking.tools;



import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.adpf.tracking.repository.TPromoCurrRepository;
import com.adpf.tracking.repository.TRetainedRepository;

@Component
public class retained {
	
	@Autowired
	private TRetainedRepository TRetainedRepository;
	
	@Autowired
	private TPromoCurrRepository TPromoCurrRepository;
	
	@Scheduled(cron = "0 0 23 * * ?")
	public void insert(){
		
		System.out.println("..........................进入定时程序，操作数据库");
		TRetainedRepository.insertRe();
		System.out.println("..........................进入定时程序，执行insert()，操作数据库");
		TRetainedRepository.updateRe();
		System.out.println("..........................进入定时程序执行updateRe()，操作数据库");
	}
	
	
	//每天23点56分删除统计表空白数据
	@Scheduled(cron = "0 56 23 * * ?")
	public void delEveryDay(){
		
		System.out.println("..........................进入定时程序，操作数据库删除数据");
		TPromoCurrRepository.deleteEveyrDay();
		
	}
	
	
	
	
	@Scheduled(cron = "0 0/5 * * * ?")
	public void statistical(){
		
		

		//获取统计时的归因时间,设置归因时间和归因推广id
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime = df.format(new Date());
		System.out.println(ctime);
		TPromoCurrRepository.updateAppStartup(ctime);
		System.out.println("...............................1");
		TPromoCurrRepository.updateAppRegister(ctime);
		System.out.println("...............................2");
		TPromoCurrRepository.updateAppLogin(ctime);
		System.out.println("...............................3");
		TPromoCurrRepository.updateAppPay(ctime);
		System.out.println("...............................4");
		//统计各数据，插入推广统计表
		TPromoCurrRepository.insertPromoCurr(ctime);
		System.out.println("...............................5");
		TPromoCurrRepository.updatePromoClick(ctime);
		System.out.println("...............................6");
		TPromoCurrRepository.updatePromoActive(ctime);
		System.out.println("...............................7");
		TPromoCurrRepository.updatePromoRegister(ctime);
		System.out.println("...............................8");
		TPromoCurrRepository.updatePromoLogin(ctime);
		System.out.println("...............................9");
		TPromoCurrRepository.updatePromoNewPay(ctime);
		System.out.println("...............................10");
		TPromoCurrRepository.updatePromoAllPay(ctime);
		System.out.println("...............................11");

	}
	
	

}
