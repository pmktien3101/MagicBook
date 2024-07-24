/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author MSI PC
 */
public class DatabaseUtils {
    private static String URL = "jdbc:sqlserver://localhost:1433;database=MBProject;trustServerCertificate=true;";
    private static String USER= "sa";
    private static String PASSWORD = "12345";
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connect success");
        return conn;
    }
}
