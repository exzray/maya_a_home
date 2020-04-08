package com.afiq.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.afiq.myapplication.models.ProgressModel;
import com.afiq.myapplication.models.ProjectModel;
import com.afiq.myapplication.utilities.FirebaseHelper;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class ProjectViewModel extends ViewModel {

    private MutableLiveData<List<ProjectModel>> userProjects;
    private ListenerRegistration registrationUserProjects;

    private MutableLiveData<List<ProgressModel>> userProgress;
    private ListenerRegistration registrationUserProgress;


    @Override
    protected void onCleared() {
        super.onCleared();
        removeUserProjects();
        removeUserProgress();
    }

    public LiveData<List<ProjectModel>> getUserProjects() {
        if (userProjects == null) {
            userProjects = new MutableLiveData<>();
            loadUserProjects(FirebaseHelper.getUserProjectsQuery());
        }

        return userProjects;
    }

    public LiveData<List<ProgressModel>> getUserProgress(CollectionReference reference) {
        removeUserProgress();

        if (userProgress == null) {
            userProgress = new MutableLiveData<>();
            loadUserProgress(reference);
        }

        return userProgress;
    }

    private void loadUserProjects(Query query) {
        registrationUserProjects = query.addSnapshotListener((queryDocumentSnapshots, e) -> {
            List<ProjectModel> list = new ArrayList<>();

            if (queryDocumentSnapshots != null) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments())
                    list.add(ProjectModel.createInstance(snapshot));
            }

            userProjects.setValue(list);
        });
    }

    private void loadUserProgress(CollectionReference reference) {

    }

    private void removeUserProjects() {
        if (registrationUserProjects != null) registrationUserProjects.remove();
    }

    private void removeUserProgress() {
        if (registrationUserProgress != null) registrationUserProgress.remove();
    }
}
