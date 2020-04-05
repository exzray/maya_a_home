package com.afiq.myapplication.viewmodels;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.afiq.myapplication.models.ProfileModel;
import com.afiq.myapplication.utilities.FirebaseHelper;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class ProfileViewModel extends ViewModel implements EventListener<DocumentSnapshot> {

    private MutableLiveData<ProfileModel> data;
    private ListenerRegistration registration;


    @Override
    protected void onCleared() {
        super.onCleared();
        if (registration != null) registration.remove();
    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
        if (snapshot == null) return;
        data.setValue(ProfileModel.createInstance(snapshot));
    }

    public LiveData<ProfileModel> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
            loadData(FirebaseHelper.getUserProfile());
        }

        return data;
    }

    private void loadData(DocumentReference reference) {
        registration = reference.addSnapshotListener(this);
    }
}
