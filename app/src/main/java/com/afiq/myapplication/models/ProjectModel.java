package com.afiq.myapplication.models;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Date;

public class ProjectModel {

    private DocumentSnapshot snapshot;

    private String label = "";
    private String userID = "";
    private String agentID = "";

    private Integer totalCost = 0;
    private Integer totalPay = 0;

    private Date created = new Date();


    public static ProjectModel createInstance(DocumentSnapshot snapshot) {
        ProjectModel data = snapshot.toObject(ProjectModel.class);

        if (data != null) data.snapshot = snapshot;

        return data;
    }


    @Exclude
    public DocumentSnapshot getSnapshot() {
        return snapshot;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(Integer totalPay) {
        this.totalPay = totalPay;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
