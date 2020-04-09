package com.afiq.myapplication.utilities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FirebaseHelper {

    private static final String COLLECTION_USERS = "mobile/user/documents";
    private static final String COLLECTION_PROJECTS = "mobile/project/documents";
    private static final String COLLECTION_PROGRESSES = "mobile/progress/documents";

    private static final String FIELD_APPLICANT_ID = "applicantID";


    public static FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getUser() {
        assert getAuth().getCurrentUser() != null;
        return getAuth().getCurrentUser();
    }

    private static FirebaseFirestore getFirestore() {
        return FirebaseFirestore.getInstance();
    }

    public static Query getUserProjectsQuery() {
        return getFirestore()
                .collection(COLLECTION_PROJECTS)
                .whereEqualTo(FIELD_APPLICANT_ID, getUser().getUid())
                .orderBy("created");
    }

    public static Query getUserProgressesQuery() {
        return getFirestore()
                .collection(COLLECTION_PROGRESSES)
                .whereEqualTo(FIELD_APPLICANT_ID, getUser().getUid());
    }

    public static DocumentReference getUserProfile() {
        return FirebaseFirestore.getInstance().document(COLLECTION_USERS + "/" + getUser().getUid());
    }
}
