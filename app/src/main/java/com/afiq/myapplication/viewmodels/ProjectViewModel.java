package com.afiq.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.afiq.myapplication.models.ProjectModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ListenerRegistration;

public class ProjectViewModel extends ViewModel {

    private MutableLiveData<ProjectModel> data;
    private ListenerRegistration listener;


    @Override
    protected void onCleared() {
        super.onCleared();
        stopListener();
    }

    public LiveData<ProjectModel> getData(DocumentReference reference) {
        if (data == null) data = new MutableLiveData<>();
        startListener(reference);

        return data;
    }

    private void startListener(DocumentReference reference) {
        stopListener();
        listener = reference
                .addSnapshotListener((snapshot, e) -> {
                    if (snapshot == null) data.setValue(null);
                    else data.setValue(ProjectModel.createInstance(snapshot));
                });
    }

    private void stopListener() {
        if (listener != null) listener.remove();
    }
}
