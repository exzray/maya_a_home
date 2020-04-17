package com.afiq.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.afiq.myapplication.models.ProjectModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProjectListViewModel extends ViewModel {

    private MutableLiveData<List<ProjectModel>> data;
    private ListenerRegistration registration;


    @Override
    protected void onCleared() {
        super.onCleared();
        stop();
    }

    public LiveData<List<ProjectModel>> getData(Query query) {
        if (data == null) data = new MutableLiveData<>();
        start(query);

        return data;
    }

    private void start(Query query) {
        stop();
        registration = query.addSnapshotListener(this::listener);
    }

    private void stop() {
        if (registration != null) registration.remove();
    }

    private void listener(QuerySnapshot snapshots, FirebaseFirestoreException e) {
        if (snapshots != null) {
            List<ProjectModel> list = new ArrayList<>();

            for (DocumentSnapshot snapshot : snapshots) {
                ProjectModel data = ProjectModel.createInstance(snapshot);

                if (data != null) list.add(data);
            }
            data.setValue(list);
        }
    }
}
