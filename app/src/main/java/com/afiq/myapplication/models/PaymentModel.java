package com.afiq.myapplication.models;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class PaymentModel {

    private DocumentSnapshot snapshot;

    private String userID = "";
    private String agentID = "";
    private String projectID = "";
    private String reason = "";
    private String receipt = "";
    private String reference = "";
    private String description = "";

    private Integer transfer = 0;

    private Boolean accepted = false;

    private Date created = new Date();


    public static PaymentModel createInstance(DocumentSnapshot snapshot) {
        PaymentModel data = snapshot.toObject(PaymentModel.class);

        if (data != null) data.snapshot = snapshot;

        return data;
    }

    @Exclude
    public DocumentSnapshot getSnapshot() {
        return snapshot;
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTransfer() {
        return transfer;
    }

    public void setTransfer(Integer transfer) {
        this.transfer = transfer;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
