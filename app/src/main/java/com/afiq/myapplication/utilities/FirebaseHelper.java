package com.afiq.myapplication.utilities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FirebaseHelper {

    private static final String COLLECTION_USERS = "users";
    private static final String COLLECTION_PROJECTS = "projects";

    private static final String FIELD_APPLICANT_ID = "applicantID";


    public static FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getUser() {
        assert getAuth().getCurrentUser() != null;
        return getAuth().getCurrentUser();
    }

    public static FirebaseFirestore getFirestore() {
        return FirebaseFirestore.getInstance();
    }

    public static Query getUserProjectsQuery() {
        return getFirestore()
                .collection(COLLECTION_PROJECTS)
                .whereEqualTo(FIELD_APPLICANT_ID, getUser().getUid());
    }

    public static DocumentReference getUserProfile() {
        return FirebaseFirestore.getInstance().document(COLLECTION_USERS + "/" + getUser().getUid());
    }
}
