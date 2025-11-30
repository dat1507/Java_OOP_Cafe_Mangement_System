/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author phuoc
 */
public class Admin extends User {
    // --- OVERRIDING (Ghi đè) ---
    // Admin => free ! 
    @Override
    public int calculateFinalBill(int totalBill) {
        totalBill = 0; 
        return totalBill; 
    }
}
