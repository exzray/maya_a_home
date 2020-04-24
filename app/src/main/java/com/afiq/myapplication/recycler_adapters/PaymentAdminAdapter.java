package com.afiq.myapplication.recycler_adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afiq.myapplication.databinding.ItemPaymentAdminBinding;
import com.afiq.myapplication.models.PaymentModel;
import com.afiq.myapplication.utilities.Database;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class PaymentAdminAdapter extends RecyclerView.Adapter<PaymentAdminAdapter.VH> {

    private static final List<PaymentModel> LIST = new ArrayList<>();

    private PaymentAdminAction action;


    public PaymentAdminAdapter(PaymentAdminAction action) {
        this.action = action;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(ItemPaymentAdminBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.setData(LIST.get(position));
    }

    @Override
    public int getItemCount() {
        return LIST.size();
    }

    public void update(List<PaymentModel> list) {
        LIST.clear();
        LIST.addAll(list);
        notifyDataSetChanged();
    }


    class VH extends RecyclerView.ViewHolder {

        private ItemPaymentAdminBinding binding;


        private VH(@NonNull ItemPaymentAdminBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void setData(PaymentModel data) {
            binding.getRoot().setOnClickListener(v -> action.onClick(data));

            Database.DOC_PROFILE(data.getUserID()).get().addOnCompleteListener(this::profileTaskListener);
        }

        private void profileTaskListener(@NonNull Task<DocumentSnapshot> task) {
        }
    }


    interface PaymentAdminAction {
        void onClick(PaymentModel data);
    }
}
