package com.adpf.modules.gdtm.service.hive.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class HiveJDBCConnectionUtils {
	
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String url = "jdbc:hive2://192.168.40.114:10000/adpf_label";
    private static String user = "hive_q";
    private static String password = "5F7vhtO8BS";

    private static Connection conn = null;
    private static Statement stmt = null;
    
    // 加载驱动、创建连接
    public Statement Connection() throws Exception {
        Class.forName(driverName);
        conn = DriverManager.getConnection(url,user,password);
        stmt = conn.createStatement();
        return stmt;
    }
}
