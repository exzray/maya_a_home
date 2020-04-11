package com.afiq.myapplication.utilities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class Interaction {

    public static final String APPLICATION_NAME = "com.afiq.myapplication";

    public static final String SHARED_SAVED_USER_EMAIL = "saveUserEmail";

    public static final String EXTRA_STRING_TITLE = "title";
    public static final String EXTRA_STRING_MESSAGE = "message";
    public static final String EXTRA_STRING_AGENT_ID = "agentID";
    public static final String EXTRA_STRING_PROJECT_ID = "projectID";
    public static final String EXTRA_STRING_PROGRESS_ID = "progressID";
    public static final String EXTRA_BOOLEAN_PROFILE_EXIST = "profileExist";


    public static void nextEnd(AppCompatActivity activity, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);
        activity.finish();
    }

    public static void next(AppCompatActivity activity, Class<?> blueprint) {
        Intent intent = new Intent(activity, blueprint);

        activity.startActivity(intent);
    }
}
