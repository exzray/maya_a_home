package com.afiq.myapplication.viewmodels;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.afiq.myapplication.models.PaymentModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class PaymentViewModel extends ViewModel {

    private static final String TAG = "PaymentViewModel";

    private MutableLiveData<PaymentModel> data;
    private ListenerRegistration registration;


    public LiveData<PaymentModel> getData() {
        if (data == null) data = new MutableLiveData<>();
        return data;
    }

    public void start(DocumentReference reference) {
        stop();
        registration = reference.addSnapshotListener(this::listener);
    }

    private void stop() {
        if (registration != null) registration.remove();
    }

    private void listener(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
        if (snapshot == null) {
            Log.i(TAG, "snapshot: null");
            return;
        }
        data.setValue(PaymentModel.createInstance(snapshot));
    }
}
