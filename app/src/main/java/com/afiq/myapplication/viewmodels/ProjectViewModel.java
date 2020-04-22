package com.afiq.myapplication.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.afiq.myapplication.models.ProjectModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class ProjectViewModel extends ViewModel {

    private static final String TAG = "ProjectViewModel";

    private MutableLiveData<ProjectModel> data;
    private ListenerRegistration listener;


    @Override
    protected void onCleared() {
        super.onCleared();
        stop();
    }

    public LiveData<ProjectModel> getData() {
        if (data == null) data = new MutableLiveData<>();
        return data;
    }

    public void start(DocumentReference reference) {
        stop();
        listener = reference.addSnapshotListener(this::listener);
    }

    private void stop() {
        if (listener != null) listener.remove();
    }

    private void listener(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        if (snapshot == null) {
            Log.i(TAG, "snapshot: null");
            return;
        }
        data.setValue(ProjectModel.createInstance(snapshot));
    }
}
