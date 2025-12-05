/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import javax.swing.JOptionPane;
import java.sql.*;

/**
 *
 * @author phuoc
 */

// used Encapsulation to hide the complex JDBC connection and Statement creation.
// The rest of the app just calls setDataOrDelete() without worrying about how the database actually connects


// Class này tạo 1 hàm tiện ích ( ultily function ) để thực hiện các lệnh SQL dạng UPDATE 
// như INSERT, UPDATE, DELETE, và có thể hiển thị kết quả ngay trên giao diện


public class DbOperations {
    
    // Đây là phương thức static => Gọi trực tiếp, k cần tạo đối tượng, VD: DbOperations.setDataOrDelete("INSERT INTO ...", "Thêm thành công");
    public static void setDataOrDelete(String Query, String msg){
        try {
            // Tạo 1 object thuộc class Connection ( nằm trong import java.sql.* ) 
            // Object ( class Connection ) đại diện cho 1 active connection giữa Java program và Database 
            // gọi hàm getCon() từ ConnectionProvider => trả về đối tượng Connection để làm việc với Database
            Connection con = ConnectionProvider.getCon();
            
            // Tạo ra 1 Statement. Statement cho phép gửi câu lệnh SQL đến MySQL
            Statement st = con.createStatement();
            
            // executeUpdate dùng cho INSERT, UPDATE, DELETE, CREATE TABLE. 
            // KHÔNG DÙNG CHO SELECT ( Nếu dùng SELECT => ResultSet và executeQuery() ) 
            st.executeUpdate(Query);
            
            // nếu msg ko rỗng => hiện popup thông báo thành công 
            if(!msg.equals(""))
                JOptionPane.showMessageDialog(null, msg);
        }
        
        // Nếu xảy ra lỗi SQL (sai cú pháp, tên bảng sai, database không chạy…), nó sẽ hiển thị popup báo lỗi.
        catch ( Exception e ) { 
            JOptionPane.showMessageDialog(null, e, "Message", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Hàm getData dùng để lấy Data từ Database ( MySQL ) => thực hiện lệnh SQL dạng SELECT
    public static ResultSet getData(String query) { 
        try {
            Connection con = ConnectionProvider.getCon(); 
            Statement st = con.createStatement(); 
            ResultSet rs = st.executeQuery(query);
            return rs; 
        }
        catch ( Exception e ) { 
            JOptionPane.showMessageDialog(null, e, "Message", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
