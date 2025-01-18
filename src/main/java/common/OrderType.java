/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package common;

/**
 *
 * @author hoang
 */
public enum OrderType {
    ADVANCE ("Đơn đặt trước"),
    IMMIDIATE ("Đơn dùng ngay");
    
    private String orderType;

    private OrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    
    
}
