/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Product; 
import java.sql.*;
/**
 *
 * @author phuoc
 */
public class ProductDao {
    
    // --- AddNewProduct ---
    // static method save giống trong CategoryDao, lấy input là 1 Product Object 
    public static void save(Product product) { 
        String query = "insert into product(name, category, price) values('"+product.getName()+"', '"+product.getCategory()+"', '"+product.getPrice()+"')";
        DbOperations.setDataOrDelete(query, "Product Added Successfully!");
    }
    
    // static method getAllRecords giống CategoryDao, tạo list các đối tượng Product
    public static ArrayList<Product> getAllRecords () { 
        ArrayList<Product> arrayList = new ArrayList<>(); 
        try{
            // Lấy dữ liệu từ table product, lưu vào rs 
            ResultSet rs = DbOperations.getData("select * from product");
            while (rs.next()){ 
                // Với mỗi dòng trong rs => Tạo một đối tượng Product mới và gán dữ liệu từ ResultSet
                Product product = new Product(); 
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getString("price")); 
                // thêm Product đó vào arraylist
                arrayList.add(product); 
            }
        }
        catch (Exception e) {
            // Hiển thị lỗi nếu xảy ra vấn đề khi truy vấn
            JOptionPane.showMessageDialog(null, e);
        }
        // method trả về arrayList
        return arrayList; 
    }
    
    // -----------------------------------------------------------------------------------------
    // --- View Edit Delete Product ---
    public static void update(Product product){ 
        String query = "update product set name = '"+product.getName()+"', category = '"+product.getCategory()+"', price = '"+product.getPrice()+"' where id = '"+product.getId()+"'";
        DbOperations.setDataOrDelete(query, "Product Updated Successfully");
    }
    
    public static void delete(String id) { 
        String query = "delete from product where id = '"+id+"'";
        DbOperations.setDataOrDelete(query, "Product Deleted Successfully");
    }
    
    
    // -----------------------------------------------------------------------------------------
    // Place Order Page
    public static ArrayList<Product> getAllRecordsByCategory(String category) { 
        ArrayList<Product> arrayList = new ArrayList<>();
        try{
            ResultSet rs = DbOperations.getData("select * from product where category = '"+category+"'");
            while(rs.next()) { 
                Product product = new Product(); 
                product.setName(rs.getString("name"));
                arrayList.add(product);
            }
        }
        catch(Exception e) { 
            JOptionPane.showMessageDialog(null,e);
        }
        return arrayList; 
    }
    
    public static ArrayList<Product> filterProductByName(String name, String category) { 
        ArrayList<Product> arrayList = new ArrayList<>();
        try{
            ResultSet rs = DbOperations.getData("select * from product where name like '%"+name+"%' and category = '"+category+"'");
            while(rs.next()) { 
                Product product = new Product(); 
                product.setName(rs.getString("name"));
                arrayList.add(product);
            }
        }
        catch(Exception e) { 
            JOptionPane.showMessageDialog(null,e);
        }
        return arrayList; 
    }
}
