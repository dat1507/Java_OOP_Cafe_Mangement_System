/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author phuoc
 */

// Định nghĩa đối tượng User, bao gồm các thuộc tính (attributes): id, name, email, ...
public abstract class User {
    private int id; 
    private String name; 
    private String email; 
    private String mobileNumber; 
    private String password; 
    private String securityQuestion; 
    private String answer; 
    private String status; 
    
    // abstract method
    public abstract int calculateFinalBill(int totalBill);
    // ---------------------
    
    // Default Constructor - Set status = "false" ngay khi tạo object
    public User() {
        this.status = "false"; // Giá trị mặc định
    }
    
    // Constructor 
    public User(String name, String email, String mobileNumber, String address, 
                String password, String securityQuestion, String answer) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.answer = answer;
        this.status = "false"; // Mặc định chờ approval
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
