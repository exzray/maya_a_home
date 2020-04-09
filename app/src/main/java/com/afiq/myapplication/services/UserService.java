package com.afiq.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.afiq.myapplication.models.ProfileModel;
import com.afiq.myapplication.models.ProjectModel;
import com.afiq.myapplication.utilities.Database;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

public class UserService extends Service {

    private MutableLiveData<ProfileModel> profile;
    private MutableLiveData<List<ProjectModel>> projects;

    private ListenerRegistration listenerProfile;
    private ListenerRegistration listenerProjects;

    private IBinder binder = new UserBinder();


    @Override
    public void onDestroy() {
        super.onDestroy();

        stopProfile();
        stopProjects();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public LiveData<ProfileModel> getProfile() {
        if (profile == null) profile = new MutableLiveData<>();

        if (listenerProfile == null) {
            listenerProfile = Database
                    .refProfile()
                    .addSnapshotListener((snapshot, e) -> {
                        if (snapshot == null) return;

                        ProfileModel data = ProfileModel.createInstance(snapshot);
                        profile.setValue(data);
                    });
        }
        return profile;
    }

    public LiveData<List<ProjectModel>> getProjects() {
        if (projects == null) projects = new MutableLiveData<>();

        if (listenerProjects == null) {
            listenerProjects = Database
                    .queryUserProjectList()
                    .addSnapshotListener((queryDocumentSnapshots, e) -> {
                        if (queryDocumentSnapshots == null) return;

                        List<ProjectModel> list = new ArrayList<>();

                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments())
                            list.add(ProjectModel.createInstance(snapshot));

                        projects.setValue(list);
                    });
        }

        return projects;
    }

    private void stopProfile() {
        if (listenerProfile != null) {
            listenerProfile.remove();
            listenerProfile = null;
        }
    }

    private void stopProjects() {
        if (listenerProjects != null) {
            listenerProjects.remove();
            listenerProjects = null;
        }
    }


    public class UserBinder extends Binder {

        public UserService getService() {
            return UserService.this;
        }
    }
}

