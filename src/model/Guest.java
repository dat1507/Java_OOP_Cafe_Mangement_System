/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author phuoc
 */
public class Guest extends User {
    // Thuộc tính riêng của Guest
    private int purchaseCount;

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    // --- OVERRIDING ---
    // Thực hiện logic giảm giá cho Guest
    @Override
    public int calculateFinalBill(int totalBill) {
        // Nếu mua từ 10 lần trở lên -> Giảm 5%
        if (this.purchaseCount >= 10) {
            return (int) (totalBill * 0.95);
        }
        // Nếu chưa đủ -> Trả nguyên giá
        return totalBill;
    }
}
