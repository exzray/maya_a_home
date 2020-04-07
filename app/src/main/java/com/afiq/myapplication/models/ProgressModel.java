package com.afiq.myapplication.models;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;

public class ProgressModel {

    public enum STATUS {NOTHING, PENDING, SUCCESS, REJECT}

    private DocumentSnapshot snapshot;

    private STATUS status = STATUS.NOTHING;

    private String description = "";
    private String receipt = "";

    private Integer payment = 0;

    private Boolean isActive = false;


    public static ProgressModel createInstance(DocumentSnapshot snapshot) {
        ProgressModel data = snapshot.toObject(ProgressModel.class);

        if (data != null) data.snapshot = snapshot;

        return data;
    }

    @Exclude
    public DocumentSnapshot getSnapshot() {
        return snapshot;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
