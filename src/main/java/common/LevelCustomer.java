/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package common;

/**
 *
 * @author hoang
 */
public enum LevelCustomer {
    NEW("Khách háng mới"),
    POTENTIAL("Khách hàng tiềm năng"),
    VIP("Khách hàng VIP");

    private String levelCustomer;

    private LevelCustomer(String levelCustomer) {
        this.levelCustomer = levelCustomer;
    }

    public String getLevelCustomer() {
        return levelCustomer;
    }

    public void setLevelCustomer(String levelCustomer) {
        this.levelCustomer = levelCustomer;
    }

}
