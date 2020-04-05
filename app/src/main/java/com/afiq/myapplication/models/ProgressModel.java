package com.afiq.myapplication.models;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;

public class ProgressModel {

    private DocumentSnapshot snapshot;

    private String description = "";

    private Integer index = 0;
    private Integer payment = 0;

    private Boolean isPay = false;


    public static ProgressModel createInstance(DocumentSnapshot snapshot) {
        ProgressModel data = snapshot.toObject(ProgressModel.class);

        if (data != null) data.snapshot = snapshot;

        return data;
    }

    @Exclude
    public DocumentSnapshot getSnapshot() {
        return snapshot;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Boolean getPay() {
        return isPay;
    }

    public void setPay(Boolean pay) {
        isPay = pay;
    }
}
