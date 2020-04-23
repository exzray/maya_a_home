package com.afiq.myapplication.utilities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Database {

    // collection path
    private static final String COLLECTION_USER = "mobile/user/documents";
    private static final String COLLECTION_PROJECT = "mobile/project/documents";
    private static final String COLLECTION_PROGRESS = "mobile/progress/documents";
    private static final String COLLECTION_PAYMENT = "mobile/payment/documents";

    // field static value
    private static final String FIELD_USER_ID = "userID";
    private static final String FIELD_AGENT_ID = "agentID";
    private static final String FIELD_PROJECT_ID = "projectID";


    // basic setup
    private static FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    private static FirebaseFirestore getFirestore() {
        return FirebaseFirestore.getInstance();
    }

    // user information
    public static FirebaseUser getUser() {
        assert getAuth().getCurrentUser() != null;
        return getAuth().getCurrentUser();
    }


    // collection
    public static Query queryUserProjectList() {
        return getFirestore()
                .collection(COLLECTION_PROJECT)
                .whereEqualTo(FIELD_USER_ID, getUser().getUid());
    }

    public static Query queryAgentProjectList() {
        return getFirestore()
                .collection(COLLECTION_PROJECT)
                .whereEqualTo(FIELD_AGENT_ID, getUser().getUid());
    }

    public static Query queryUserProgressList() {
        return getFirestore()
                .collection(COLLECTION_PROGRESS)
                .whereEqualTo(FIELD_USER_ID, getUser().getUid());
    }

    public static Query queryProgressList(String project_id) {
        return getFirestore()
                .collection(COLLECTION_PROGRESS)
                .whereEqualTo(FIELD_PROJECT_ID, project_id);
    }


    // document
    public static DocumentReference DOC_PROFILE(String uid) {
        return getFirestore()
                .document(COLLECTION_USER + "/" + uid);
    }

    public static DocumentReference DOC_PROJECT(String project_id) {
        return getFirestore()
                .document(COLLECTION_PROJECT + "/" + project_id);
    }

    public static DocumentReference DOC_PROGRESS(String progress_id) {
        return getFirestore()
                .document(COLLECTION_PROGRESS + "/" + progress_id);
    }

    public static DocumentReference DOC_PAYMENT(String payment_id) {
        return getFirestore()
                .document(COLLECTION_PAYMENT + "/" + payment_id);
    }
}
