package baseinfo;

import java.sql.*;
import java.sql.DriverManager;

public class DBtest {
    public static void DBcone(String sql) {
        try {
            Class.forName("com.mysql.jdbc.Driver");     //加载MYSQL JDBC驱动程序
            System.out.println("Success loading Mysql Driver!");
        }
        catch (Exception e) {
            System.out.print("Error loading Mysql Driver!");
            e.printStackTrace();
        }
        try {
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://47.97.179.224:3306/eaglehorn_engine","root","1017~Fulin");
            //连接URL为   jdbc:mysql//服务器地址/数据库名  ，后面的2个参数分别是登陆用户名和密码

            System.out.println("Success connect Mysql server!");
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("select * from t_node_strategy_rel  where node_id in (111,112, 141) and status = 1;");
            //user 为你表的名称
            while (rs.next()) {
                System.out.println(rs.getString("node_id"));
            }
        }
        catch (Exception e) {
            System.out.print("get data error!");
            e.printStackTrace();
        }
    }

}
