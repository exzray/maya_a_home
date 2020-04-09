package com.afiq.myapplication.models;

public class PaymentModel {

    private String reason = "";
    private String receipt = "";


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }
}
