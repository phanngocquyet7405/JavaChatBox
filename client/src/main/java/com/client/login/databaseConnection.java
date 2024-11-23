package com.client.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnection {
    private static final String USER_DATABASE = "root";
    private static final String DATABASE_PASSWORD = "123456";
    private static final String URL = "jdbc:mysql://localhost:3306/db_chat_box";

    public static Connection getConnection() {
        Connection databaseLink = null;
        try {
            databaseLink = DriverManager.getConnection(URL, USER_DATABASE, DATABASE_PASSWORD);
            System.out.println("Kết nối thành công!");
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại! Vui lòng kiểm tra lại cấu hình cơ sở dữ liệu.");
            e.printStackTrace();
            System.out.println("Lỗi SQLState: " + e.getSQLState());
            System.out.println("Mã lỗi: " + e.getErrorCode());
        }
        return databaseLink;
    }

}

