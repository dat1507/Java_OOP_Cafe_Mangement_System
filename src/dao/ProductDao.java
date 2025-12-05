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
        String query = "insert into product(name, category, price, image) values('" 
                + product.getName() + "','" 
                + product.getCategory() + "','" 
                + product.getPrice() + "','" 
                + product.getImage() + "')";
        DbOperations.setDataOrDelete(query, "Product Added Successfully");
    }
    
    // Lấy tất cả sản phẩm để hiển thị lên bảng
    public static ArrayList<Product> getAllRecords() {
        ArrayList<Product> arrayList = new ArrayList<>();
        try {
            ResultSet rs = DbOperations.getData("select * from product");
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getString("price"));
                product.setImage(rs.getString("image")); // Lấy thêm ảnh
                arrayList.add(product);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return arrayList;
    }
    
    // -----------------------------------------------------------------------------------------
    // --- View Edit Delete Product ---
    // Cập nhật sản phẩm (Có cập nhật ảnh)
    public static void update(Product product) {
        String query = "update product set name='" + product.getName() 
                + "',category='" + product.getCategory() 
                + "',price='" + product.getPrice() 
                + "',image='" + product.getImage() 
                + "' where id='" + product.getId() + "'";
        DbOperations.setDataOrDelete(query, "Product Updated Successfully");
    }
    
    
    // Method Overloading for Delete Product Function 
    // 2 methods with the same name, but different signatures
    //  xóa theo ID (Nhận tham số int)
    public static void delete(int id) {
        String query = "delete from product where id = '" + id + "'";
        DbOperations.setDataOrDelete(query, "Product Deleted Successfully via ID");
    }

    // 2. Hàm xóa theo Tên (Nhận tham số String) -> Overloading
    // Cùng tên hàm "delete", nhưng khác kiểu tham số đầu vào (String vs int)
    public static void delete(String name) {
        String query = "delete from product where name = '" + name + "'";
        DbOperations.setDataOrDelete(query, "Product Deleted Successfully via Name");
    }
    
    // Hàm MỚI: Lấy chi tiết 1 sản phẩm theo ID (Dùng để hiển thị ảnh khi click vào bảng)
    public static Product getProductById(String id) {
        Product product = new Product();
        try {
            ResultSet rs = DbOperations.getData("select * from product where id='" + id + "'");
            while (rs.next()) {
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getString("price"));
                product.setImage(rs.getString("image"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return product;
    }
    
    // -----------------------------------------------------------------------------------------
    // Place Order Page
    
    // Lấy 1 list tất cả Product có category đc chọn từ Database ( parameter là category đc chọn ) 
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
    
    // Trả về danh sách các Product có tên chứa chuỗi nhập vào và cùng category, nhưng mỗi product chỉ có setName().
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
    
    // Tìm một sản phẩm duy nhất, dựa trên tên chính xác (name = '...').
    public static Product getProductByname(String name) {
        Product product = new Product();
        try {
            ResultSet rs = DbOperations.getData("select * from product where name='" + name + "'");
            while (rs.next()) {
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getString("price"));
                
                // --- THÊM DÒNG NÀY ---
                product.setImage(rs.getString("image")); 
                // ---------------------
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return product;
    }
}
