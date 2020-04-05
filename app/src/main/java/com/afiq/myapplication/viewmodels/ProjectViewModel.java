package com.afiq.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.afiq.myapplication.models.ProjectModel;
import com.afiq.myapplication.utilities.FirebaseHelper;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class ProjectViewModel extends ViewModel {

    private MutableLiveData<List<ProjectModel>> userProjects;
    private ListenerRegistration registrationUserProjects;

    @Override
    protected void onCleared() {
        super.onCleared();
        if (registrationUserProjects != null) registrationUserProjects.remove();
    }

    public LiveData<List<ProjectModel>> getUserProjects() {
        if (userProjects == null) {
            userProjects = new MutableLiveData<>();
            loadData(FirebaseHelper.getUserProjectsQuery());
        }

        return userProjects;
    }

    private void loadData(Query query) {
        registrationUserProjects = query.addSnapshotListener((queryDocumentSnapshots, e) -> {
            List<ProjectModel> list = new ArrayList<>();

            if (queryDocumentSnapshots != null) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments())
                    list.add(ProjectModel.createInstance(snapshot));
            }

            userProjects.setValue(list);
        });
    }
}
