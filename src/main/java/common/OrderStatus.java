/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package common;

/**
 *
 * @author hoang
 */
public enum OrderStatus {
    SINGLE ("Đơn riêng lẻ"),
    MERGED ("Đơn đã gộp");
    
    private String orderStatus;

    private OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    
}
