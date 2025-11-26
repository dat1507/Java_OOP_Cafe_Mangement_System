/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.User;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author phuoc
 */
public class UserDao {

    // Hàm save ( Signup Page ) => Save a user after registered 
    public static void save(User user) {
        String query = "insert into user(name, email, mobileNumber, address, password, securityQuestion, answer, status) values('" + user.getName() + "', '" + user.getEmail() + "', '" + user.getMobileNumber() + "', '" + user.getAddress() + "', '" + user.getPassword() + "', '" + user.getSecurityQuestion() + "', '" + user.getAnswer() + "', '" + user.getStatus() + "')";
        DbOperations.setDataOrDelete(query, "Registered Successfully! Wait for Admin Approval!");
    }

    // Hàm Login ( Được sử dụng trong Login Page ) lấy email và password từ user, sau đó tìm trong database xem có email và password nào trùng không, nếu có sẽ return user đó
    // nếu ko thì sẽ hiện thông báo qua showMessageDialog
    public static User login(String email, String password) {
        User user = null;
        try {
            ResultSet rs = DbOperations.getData("select * from user where email='" + email + "' and password='" + password + "'");

            if (rs != null && rs.next()) { // rs.next() di chuyển con trỏ sang dòng đầu tiên của kết quả, nếu tồn tại dòng dữ liệu => return true, else return false
                user = new User(); // Constructor đã set status = "false"

                // Lấy status từ database (có thể null)
                String dbStatus = rs.getString("status");

                // Nếu database trả về null, giữ nguyên "false" từ constructor
                // Nếu có giá trị, override giá trị từ database
                if (dbStatus != null && !dbStatus.isEmpty()) {
                    user.setStatus(dbStatus.trim());
                }
                // Nếu dbStatus = null, user.status vẫn giữ "false" từ constructor

                // Set các field khác
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setMobileNumber(rs.getString("mobileNumber"));
                user.setAddress(rs.getString("address"));
                user.setSecurityQuestion(rs.getString("securityQuestion"));
                user.setAnswer(rs.getString("answer"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return user;
    }

    public static User getSecurityQuestion(String email) {
        User user = null;
        try {
            ResultSet rs = DbOperations.getData("Select * from user where email = '" + email + "'");
            // Kiểm tra rs != null trước khi sử dụng
            if (rs != null && rs.next()) {
                user = new User();
                user.setSecurityQuestion(rs.getString("securityQuestion"));
                user.setAnswer(rs.getString("answer"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return user;
    }

    public static void update(String email, String newPassword) {
        String query = "update user set password = '" + newPassword + "' where email = '" + email + "' ";
        DbOperations.setDataOrDelete(query, "Password Changed Successfully");
    }
    
    public static ArrayList<User> getAllRecords(String email) { 
        ArrayList<User> arrayList = new ArrayList<>(); 
        try{
            ResultSet rs = DbOperations.getData("select * from user where email like '%"+email+"%'");
            while (rs.next()) { 
                User user = new User(); 
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setMobileNumber(rs.getString("mobileNumber"));
                user.setAddress(rs.getString("address"));
                user.setSecurityQuestion(rs.getString("securityQuestion"));
                user.setStatus(rs.getString("status"));
                arrayList.add(user);
            }
        }
        catch(Exception e) { 
            JOptionPane.showMessageDialog(null, e);
        }
        return arrayList;
    }
    
    public static void changeStatus(String email, String status) { 
        String query = "update user set status = '"+status+"' where email = '"+email+"'";
        DbOperations.setDataOrDelete(query, "Status Changed Successfully");
    }

}
