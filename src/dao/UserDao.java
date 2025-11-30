/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.User;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import model.Admin;
import model.Guest;

/**
 *
 * @author phuoc
 */
public class UserDao {
    
    //-----------------------------------------------------------------------------------
    // SignUp
    // Hàm save => Save a user after registered 
    public static void save(User user) {
        // Xóa 'address' trong phần insert into và phần values
        String query = "insert into user(name, email, mobileNumber, password, securityQuestion, answer, status) values('" + user.getName() + "', '" + user.getEmail() + "', '" + user.getMobileNumber() + "', '" + user.getPassword() + "', '" + user.getSecurityQuestion() + "', '" + user.getAnswer() + "', '" + user.getStatus() + "')";
        DbOperations.setDataOrDelete(query, "Registered Successfully! Wait for Admin Approval!");
    }
    
    //-----------------------------------------------------------------------------------
    // Login
    // SỬA: Logic kiểm tra Role để tạo Admin hoặc Guest
    public static User getUserForLogin(String email, String password) {
        User user = null;
        try {
            String query = "select * from user where email='" + email + "' and password='" + password + "'";
            ResultSet rs = DbOperations.getData(query);
            
            if (rs.next()) {
                String role = rs.getString("role"); // Lấy role từ DB
                
                // --- (Polymorphism) ---
                // Tạo instance cụ thể dựa trên role
                if (role != null && role.equalsIgnoreCase("admin")) {
                    user = new Admin(); 
                } else {
                    user = new Guest(); 
                    // Nếu là Guest thì lấy thêm số lần mua
                    ((Guest)user).setPurchaseCount(rs.getInt("purchase_count"));
                }
                
                // Các thuộc tính chung (kế thừa từ User)
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setMobileNumber(rs.getString("mobileNumber"));
                user.setSecurityQuestion(rs.getString("securityQuestion"));
                user.setAnswer(rs.getString("answer"));
                user.setStatus(rs.getString("status"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return user;
    }
    
    
    //-------------------------------------------------------------------
    // ForgotPassword 
    public static User getSecurityQuestion(String email) {
        User user = null;
        try {
            ResultSet rs = DbOperations.getData("Select * from user where email = '" + email + "'");
            if (rs.next()) {
                // SỬA: Không thể new User() -> dùng new Guest() làm vật chứa dữ liệu tạm
                user = new Guest(); 
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
    
    
    //--------------------------------------------------------------------------------
    // VerifyUsers (Cho Admin duyệt user mới)
    
    public static ArrayList<User> getAllRecords(String email) { 
        ArrayList<User> arrayList = new ArrayList<>(); 
        try{
            ResultSet rs = DbOperations.getData("select * from user where email like '%"+email+"%'");
            while (rs.next()) { 
                // SỬA: Dùng new Guest() thay vì new User()
                User user = new Guest(); 
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setMobileNumber(rs.getString("mobileNumber"));
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

    //--------------------------------------------------------------------------------
    // TÍNH NĂNG MỚI ( OOP Admin/Guest)
    
    // 1. Tăng số lần mua hàng (Dành cho Guest khi thanh toán không giảm giá)
    public static void incrementPurchaseCount(String email) {
        String query = "update user set purchase_count = purchase_count + 1 where email = '" + email + "'";
        DbOperations.setDataOrDelete(query, ""); // Không cần hiện popup thông báo
    }
    
    // 2. Reset số lần mua về 0 (Sau khi Guest đã dùng mã giảm giá)
    public static void resetPurchaseCount(String email) {
        String query = "update user set purchase_count = 0 where email = '" + email + "'";
        DbOperations.setDataOrDelete(query, "");
    }
    
    // 3. Lấy toàn bộ thông tin nhạy cảm (Password, Role...) cho màn hình Admin mới
    public static ArrayList<User> getAllUsersSensitive(String email) {
        ArrayList<User> list = new ArrayList<>();
        try {
            // Thêm điều kiện where email like ...
            ResultSet rs = DbOperations.getData("select * from user where email like '%"+email+"%'");
            while(rs.next()){
                String role = rs.getString("role");
                User user;
                
                // Logic Đa hình (Polymorphism)
                if (role != null && role.equalsIgnoreCase("admin")) {
                    user = new Admin();
                } else {
                    user = new Guest();
                    ((Guest)user).setPurchaseCount(rs.getInt("purchase_count"));
                }
                
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setMobileNumber(rs.getString("mobileNumber"));
                user.setSecurityQuestion(rs.getString("securityQuestion"));
                user.setStatus(rs.getString("status"));
                user.setPassword(rs.getString("password")); // Lấy thêm password
                
                list.add(user);
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return list;
    }
    
    
    // Hàm này dùng để lấy đối tượng User (Admin/Guest) tại trang PlaceOrder
    public static User getUserByEmail(String email) {
        User user = null;
        try {
            ResultSet rs = DbOperations.getData("select * from user where email='" + email + "'");
            if (rs.next()) {
                String role = rs.getString("role");
                
                // Kiểm tra role để khởi tạo đúng lớp con (Admin hoặc Guest)
                if (role != null && role.equalsIgnoreCase("admin")) {
                    user = new Admin();
                } else {
                    user = new Guest();
                    ((Guest)user).setPurchaseCount(rs.getInt("purchase_count"));
                }
                
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setMobileNumber(rs.getString("mobileNumber"));
                user.setStatus(rs.getString("status"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return user;
    }
    
    // ---------------------
    // Change Password 
    public static void changePassword(String email, String oldPassword, String newPassword) { 
        try{
            ResultSet rs = DbOperations.getData("select * from user where email = '"+email+"' and password = '"+oldPassword+"'");
            if(rs.next()){
                update(email,newPassword);
            } else {
                JOptionPane.showMessageDialog(null, "Old Password is wrong");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
}