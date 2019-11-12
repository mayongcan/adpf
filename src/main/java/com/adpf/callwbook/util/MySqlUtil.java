package com.adpf.callwbook.util;

import java.sql.*;

public class MySqlUtil {
    private Connection con;
    private Statement stm;
    private ResultSet rs;


    public MySqlUtil() {
        try {
            // 1.加载驱动
            String driverName = "com.mysql.jdbc.Driver"; // mysql
            // jdbc驱动描述符,实际上就是driver类在包中的完整路径
            Class.forName(driverName);

            // 2.建立与数据库的连接
            String url = "jdbc:mysql://192.168.5.54/venusdb?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull"; // 数据库连接字符串，一般使用统一资源定位符（url）的形式

            String user = "reader"; // 连接数据库时的用户
            String password = "reader@123"; // 连接数据库时的密码
            con = DriverManager.getConnection(url, user, password);

            //3.创建语句对象
            stm = con.createStatement();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    //查询
    public ResultSet executeQueryByTime(String miniTime, String maxTime) throws SQLException {
        PreparedStatement prestmt = null;
        String sql = "SELECT ag.AGENTID as 'seaiId',ag.TELNO as 'callId',SUM(rp.SUMBILLDURATION) as 'callTime',MAX(rp.OFFHKTM) as'maxDate',DATE_FORMAT(OFFHKTM,'%Y-%m-%d') as 'dateTime' "
                + "from agent ag LEFT JOIN rp_bill_count rp on ag.TELNO = rp.ANI "
                + "where rp.ANI and ag.TELNO is NOT null and rp.OFFHKTM > ? and rp.OFFHKTM <= ? GROUP BY ag.AGENTID,dateTime";
        prestmt = con.prepareStatement(sql);
        prestmt.setString(1, miniTime);
        prestmt.setString(2, maxTime);
        rs = prestmt.executeQuery();
        return rs;
    }

    //查询最大日期
    public ResultSet executeMaxTime() throws SQLException {
        PreparedStatement prestmt = null;
        String sql = "SELECT MAX(OFFHKTM)as'maxTime' from rp_bill_count";
        prestmt = con.prepareStatement(sql);
        rs = prestmt.executeQuery();
        return rs;
    }

    /*
     * 4.资源的释放
     */
    public void close() {
        try {
            if (rs != null) {

                rs.close();
            }
            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getStm() {
        return stm;
    }

    public void setStm(Statement stm) {
        this.stm = stm;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }


}
