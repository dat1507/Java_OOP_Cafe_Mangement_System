/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
/**
 *
 * @author phuoc
 */

//Java ----(DriverManager)---> MySQL Driver ----> Database "cms"
public class ConnectionProvider {
    
    // Hàm getCon() return 1 Connection 
    public static Connection getCon(){
        try{
            // Tải driver MySQL vào bộ nhớ 
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // getConnection() Phương thức yêu cầu Java tạo kết nối đến Database bằng giao thức JDBC 
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms?useSSl=false","root","123456");
            return con;
        }
        catch(Exception e) { 
            return null;
        }
    }
}
