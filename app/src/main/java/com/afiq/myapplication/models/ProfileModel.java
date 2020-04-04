package com.afiq.myapplication.models;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class ProfileModel {

    private DocumentSnapshot snapshot;

    private String name = "";
    private String email = "";
    private String contact = "";
    private String address = "";

    private Boolean isStaff = false;
    private Boolean isAdmin = false;

    private Date joined = new Date();


    public static ProfileModel createInstance(DocumentSnapshot snapshot) {
        ProfileModel data = snapshot.toObject(ProfileModel.class);

        if (data != null) data.snapshot = snapshot;

        return data;
    }

    @Exclude
    public DocumentSnapshot getSnapshot() {
        return snapshot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getStaff() {
        return isStaff;
    }

    public void setStaff(Boolean staff) {
        isStaff = staff;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Date getJoined() {
        return joined;
    }

    public void setJoined(Date joined) {
        this.joined = joined;
    }
}
