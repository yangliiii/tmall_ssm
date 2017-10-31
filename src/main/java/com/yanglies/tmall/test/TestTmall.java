package com.yanglies.tmall.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * lies, please leave something
 *
 * @author lies
 * @Createdon 2017/10/31 15:43.
 * @ProjectName tmall_ssm
 */
public class TestTmall {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(Connection c = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/tmall_ssm?useUnicode=true&characterEncoding=utf8",
                        "root", "admin");
            Statement s = c.createStatement();)
        {
            for (int i = 0; i < 10; i++) {
                String sqlFormat = "insert into category values (null,'测试分类%d')";
                String sql = String.format(sqlFormat,i);
                s.execute(sql);
            }
            System.out.println("已经创建好10条测试数据！");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
