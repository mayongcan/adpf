package com.adpf.modules.gdtm.service.hbase.test;

import java.io.IOException;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import com.adpf.modules.gdtm.util.PropertiesUtils;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class HBaseConnectionUtils {

	public static Connection getConnection() throws IOException {
		Connection connection = ConnectionFactory.createConnection(getConfiguration());
		return connection;
	}

	private static Configuration getConfiguration() throws IOException {

		Properties props = PropertiesUtils.load("hbase.properties");

		Configuration config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.property.clientPort", props.getProperty("hbase.zookeeper.property.clientPort"));
		config.set("hbase.zookeeper.quorum", props.getProperty("hbase.zookeeper.quorum"));
		return config;
	}
}
