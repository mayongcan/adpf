package com.adpf.conf;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages={"com.adpf"})
@EntityScan(basePackages={"com.adpf"})
@ComponentScan(basePackages = {"com.adpf"})
public class ScanConfiguration {

}
