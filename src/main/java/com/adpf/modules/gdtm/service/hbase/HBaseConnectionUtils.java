package com.adpf.modules.gdtm.service.hbase;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import com.adpf.modules.gdtm.util.PropertiesUtils;

@Configuration
public class HBaseConnectionUtils{
	  
	@Bean
	public HbaseTemplate getHbaseTemplate() throws IOException {
		
		Properties props = PropertiesUtils.load("hbase.properties");
		
		HbaseTemplate hbaseTemplate = new HbaseTemplate();
	    org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
	    configuration = org.apache.hadoop.hbase.HBaseConfiguration.create();
	    configuration.set("hbase.zookeeper.property.clientPort", props.getProperty("hbase.zookeeper.property.clientPort"));
	    configuration.set("hbase.zookeeper.quorum", props.getProperty("hbase.zookeeper.quorum"));
	    hbaseTemplate.setConfiguration(configuration);
	    return hbaseTemplate;
	  }
}
