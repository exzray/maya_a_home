package com.afiq.myapplication.models;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class ProgressModel {

    public enum STATUS {NOTHING, PENDING, SUCCESS, REJECT}

    private DocumentSnapshot snapshot;

    private STATUS status = STATUS.NOTHING;

    private String userID = "";
    private String agentID = "";
    private String projectID = "";
    private String description = "";

    private Integer payment = 0;

    private Boolean isActive = false;

    private Date created = new Date();


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

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
