/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
// Lấy đối tượng Category từ package model ( thường gồm id và name ) 
import model.Category;
import java.util.ArrayList;
import java.sql.*;
// JOptionPane dùng để hiển thị cửa sổ thông báo (popup) khi có lỗi xảy ra 
import javax.swing.JOptionPane;

/**
 *
 * @author phuoc
 */

// Giao tiếp với Database để thực hiện các thao thác save, view, delete
public class CategoryDao {
    
    // Lấy input là 1 đối tượng (Object) Category 
    public static void save(Category category){
        // Tạo query insert vào cột name trong table Category trong Database 
        // Sử dụng getter getName() của đối tượng Category
        String query = "insert into category (name) values('"+category.getName()+"')";
        DbOperations.setDataOrDelete(query, "Category Added Successfully");
    }
    
    
    // Sử dụng Class ArrayList có sẵn trong thư viện java.util
    // tạo 1 danh sách có tên arrayList chứa các Category Object => hàm trả về danh sách đó
    public static ArrayList<Category> getAllRecords() { 
        ArrayList<Category> arrayList = new ArrayList<>();
        try{
            // Lấy tất cả dữ liệu từ table category => lưu vào ResultSet rs
            ResultSet rs = DbOperations.getData("select * from category");
            // Với mỗi dòng thuộc rs => Tạo một đối tượng Category mới và gán dữ liệu từ ResultSet
            // => add Category đó vào arrayList
            while (rs.next()) { 
                Category category = new Category(); 
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                arrayList.add(category);
            }
        }
        catch(Exception e){
            // Hiển thị lỗi nếu xảy ra vấn đề khi truy vấn
            JOptionPane.showMessageDialog(null, e);
        }
        return arrayList;
    }
    
    public static void delete(String id) { 
        String query = "delete from category where id = '"+id+"'";
        DbOperations.setDataOrDelete(query, "Category Deleted Successfully");
    }
}
