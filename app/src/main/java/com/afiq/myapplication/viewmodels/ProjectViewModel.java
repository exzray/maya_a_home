package com.afiq.myapplication.viewmodels;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.afiq.myapplication.models.ProjectModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProjectViewModel extends ViewModel {

    private MutableLiveData<List<ProjectModel>> projects;
    private ListenerRegistration registration;

    @Override
    protected void onCleared() {
        super.onCleared();

        if (registration != null) registration.remove();
    }

    public LiveData<List<ProjectModel>> getProjects(Query query) {
        if (projects == null) {
            projects = new MutableLiveData<>();
            loadData(query);
        }

        return projects;
    }

    private void loadData(Query query) {
        registration = query.addSnapshotListener((queryDocumentSnapshots, e) -> {
            List<ProjectModel> list = new ArrayList<>();

            if (queryDocumentSnapshots != null) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments())
                    list.add(ProjectModel.createInstance(snapshot));
            }

            projects.setValue(list);
        });
    }
}
