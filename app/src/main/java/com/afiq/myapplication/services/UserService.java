package com.afiq.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.afiq.myapplication.broadcasts.NotificationReceiver;
import com.afiq.myapplication.models.ProfileModel;
import com.afiq.myapplication.models.ProgressModel;
import com.afiq.myapplication.models.ProjectModel;
import com.afiq.myapplication.utilities.Database;
import com.afiq.myapplication.utilities.Interaction;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserService extends Service {

    private static final String TAG = "UserService";

    private MutableLiveData<ProfileModel> profile;
    private MutableLiveData<List<ProjectModel>> projects;
    private MutableLiveData<List<ProgressModel>> progress;

    private ListenerRegistration listenerProfile;
    private ListenerRegistration listenerProjects;
    private ListenerRegistration listenerProgress;

    private IBinder binder = new UserBinder();


    @Override
    public void onDestroy() {
        super.onDestroy();

        stopProfile();
        stopProjects();
        stopProgress();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startProfile();
        startProjects();
        startProgress();
        return START_STICKY;
    }

    public LiveData<ProfileModel> getProfile() {
        return profile;
    }

    private void startProfile() {
        if (profile == null) profile = new MutableLiveData<>();
        if (listenerProfile == null) {
            listenerProfile = Database
                    .refProfile()
                    .addSnapshotListener((snapshot, e) -> {
                        if (snapshot == null) return;

                        ProfileModel data = ProfileModel.createInstance(snapshot);

                        if (profile != null) profile.setValue(data);
                    });
        }
    }

    private void stopProfile() {
        if (listenerProfile != null) {
            listenerProfile.remove();
            listenerProfile = null;
        }
    }

    public LiveData<List<ProjectModel>> getProjects() {
        return projects;
    }

    private void startProjects() {
        if (projects == null) projects = new MutableLiveData<>();
        if (listenerProjects == null) {
            listenerProjects = Database
                    .queryUserProjectList()
                    .addSnapshotListener((queryDocumentSnapshots, e) -> {
                        if (queryDocumentSnapshots == null) return;

                        List<ProjectModel> list = new ArrayList<>();

                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments())
                            list.add(ProjectModel.createInstance(snapshot));

                        for (DocumentChange change : queryDocumentSnapshots.getDocumentChanges()) {
                            ProjectModel data = change.getDocument().toObject(ProjectModel.class);

                            if (change.getType() == DocumentChange.Type.MODIFIED)

                                broadcastNotification("Project Notification", data.getLabel());
                        }

                        if (projects != null) projects.setValue(list);
                    });
        }
    }

    private void stopProjects() {
        if (listenerProjects != null) {
            listenerProjects.remove();
            listenerProjects = null;
        }
    }

    private void startProgress() {
        if (progress == null) progress = new MutableLiveData<>();
        if (listenerProgress == null)
            listenerProgress = Database
                    .queryUserProgressList()
                    .addSnapshotListener(this::progressListener);
    }

    private void stopProgress() {
        if (listenerProgress != null) {
            listenerProgress.remove();
            listenerProgress = null;
        }
    }

    private void progressListener(QuerySnapshot snapshots, FirebaseFirestoreException e) {
        if (snapshots == null) {
            Log.i(TAG, "progress: null");
            return;
        }

        for (DocumentChange change : snapshots.getDocumentChanges()) {
            switch (change.getType()) {
                case MODIFIED:
                    broadcastNotification("Progress Notification", "Your receive update from developer");
                    break;
                case REMOVED:
                    break;
            }
        }

        if (e != null) Log.i(TAG, "" + e.getMessage());
    }

    private void broadcastNotification(String title, String message) {
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra(Interaction.EXTRA_STRING_TITLE, title);
        intent.putExtra(Interaction.EXTRA_STRING_MESSAGE, message);

        sendBroadcast(intent);
    }


    public class UserBinder extends Binder {

        public UserService getService() {
            return UserService.this;
        }
    }
}

