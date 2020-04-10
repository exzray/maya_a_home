package com.afiq.myapplication.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.afiq.myapplication.App;
import com.afiq.myapplication.R;
import com.afiq.myapplication.models.ProfileModel;
import com.afiq.myapplication.models.ProgressModel;
import com.afiq.myapplication.models.ProjectModel;
import com.afiq.myapplication.utilities.Database;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

public class UserService extends Service {

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
                            if (change.getType() == DocumentChange.Type.MODIFIED)
                                showProjectNotification(change.getDocument());
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

    private void stopProgress() {
        if (listenerProgress != null) {
            listenerProgress.remove();
            listenerProgress = null;
        }
    }

    private void showProjectNotification(DocumentSnapshot snapshot) {
        NotificationManager manager = getSystemService(NotificationManager.class);
        assert manager != null;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, App.NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_extension);
        builder.setContentTitle("Project Notification");
        builder.setContentText(ProjectModel.createInstance(snapshot).getLabel());
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        manager.notify(1, builder.build());
    }


    public class UserBinder extends Binder {

        public UserService getService() {
            return UserService.this;
        }
    }
}

