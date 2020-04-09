package com.afiq.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.afiq.myapplication.models.ProgressModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class ProgressListViewModel extends ViewModel {

    private MutableLiveData<List<ProgressModel>> data;
    private ListenerRegistration listener;


    @Override
    protected void onCleared() {
        super.onCleared();
        stopListener();
    }

    public LiveData<List<ProgressModel>> getData(Query query) {
        if (data == null) data = new MutableLiveData<>();
        startListener(query);

        return data;
    }

    private void startListener(Query query) {
        stopListener();
        listener = query
                .addSnapshotListener((snapshots, e) -> {
                    List<ProgressModel> list = new ArrayList<>();

                    if (snapshots != null) {
                        for (DocumentSnapshot snapshot : snapshots.getDocuments())
                            list.add(ProgressModel.createInstance(snapshot));
                    }

                    data.setValue(list);
                });
    }

    private void stopListener() {
        if (listener != null) listener.remove();
    }
}
