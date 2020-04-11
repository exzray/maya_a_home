package com.afiq.myapplication.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.afiq.myapplication.models.ProgressModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class ProgressViewModel extends ViewModel {

    private static final String TAG = "ProgressViewModel";

    private MutableLiveData<ProgressModel> data;
    private ListenerRegistration registration;

    @Override
    protected void onCleared() {
        super.onCleared();
        if (registration != null) registration.remove();
    }

    public LiveData<ProgressModel> getData(DocumentReference reference) {
        if (data == null) data = new MutableLiveData<>();
        loadData(reference);
        return data;
    }

    private void loadData(DocumentReference reference) {
        if (registration != null) registration.remove();
        registration = reference.addSnapshotListener(this::documentListener);
    }

    private void documentListener(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        if (snapshot == null) {
            Log.i(TAG, "snapshot: null");
            return;
        }
        data.setValue(ProgressModel.createInstance(snapshot));
    }
}
