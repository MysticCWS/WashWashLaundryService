package com.example.washwashlaundryservice;

public class UserHistoryClass {

    String userID, historyID, washerOption, dryerOption, foldOption, totalLaundry, paymentMethod, date, otpMachine;

    public UserHistoryClass() {
    }

    public UserHistoryClass(String userID, String historyID, String washerOption, String dryerOption, String foldOption, String totalLaundry, String paymentMethod, String date, String otpMachine) {
        this.userID = userID;
        this.historyID = historyID;
        this.washerOption = washerOption;
        this.dryerOption = dryerOption;
        this.foldOption = foldOption;
        this.totalLaundry = totalLaundry;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.otpMachine = otpMachine;
    }

    public String getOtpMachine() {
        return otpMachine;
    }

    public void setOtpMachine(String otpMachine) {
        this.otpMachine = otpMachine;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getHistoryID() {
        return historyID;
    }

    public void setHistoryID(String historyID) {
        this.historyID = historyID;
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

    public String getTotalLaundry() {
        return totalLaundry;
    }

    public void setTotalLaundry(String totalLaundry) {
        this.totalLaundry = totalLaundry;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
