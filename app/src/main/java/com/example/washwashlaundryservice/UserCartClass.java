package com.example.washwashlaundryservice;

public class UserCartClass {

    public String userID, cartID, washerOption, dryerOption, foldOption, status;
    public int total;

    public UserCartClass() {
    }

    public UserCartClass(String userID, String cartID, String washerOption, String dryerOption, String foldOption, int total, String status) {
        this.userID = userID;
        this.cartID = cartID;
        this.washerOption = washerOption;
        this.dryerOption = dryerOption;
        this.foldOption = foldOption;
        this.total = total;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getWasherOption() {
        return washerOption;
    }

    public void setWasherOption(String washerOption) {
        this.washerOption = washerOption;
    }

    public String getDryerOption() {
        return dryerOption;
    }

    public void setDryerOption(String dryerOption) {
        this.dryerOption = dryerOption;
    }

    public String getFoldOption() {
        return foldOption;
    }

    public void setFoldOption(String foldOption) {
        this.foldOption = foldOption;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
