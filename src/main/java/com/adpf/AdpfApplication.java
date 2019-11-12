package com.adpf;

import com.adpf.listen.StartupListener;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 服务启动主函数
 *
 * @author zzd
 */
@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
public class AdpfApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AdpfApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.addListeners(new StartupListener());
        app.run(args);
//	    	WriteTxtUtils.writeTxt();
    }

}
