package com.afiq.myapplication.recycler_adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afiq.myapplication.models.PaymentModel;

import java.util.ArrayList;
import java.util.List;

public class PaymentAdminAdapter extends RecyclerView.Adapter<PaymentAdminAdapter.VH> {

    private static final List<PaymentModel> LIST = new ArrayList<>();


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class VH extends RecyclerView.ViewHolder {

        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
