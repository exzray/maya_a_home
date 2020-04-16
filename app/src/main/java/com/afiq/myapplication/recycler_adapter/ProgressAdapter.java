package com.afiq.myapplication.recycler_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.afiq.myapplication.R;
import com.afiq.myapplication.databinding.ItemProgressBinding;
import com.afiq.myapplication.models.ProgressModel;

import java.util.ArrayList;
import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.VH> {

    private final ActionItemProgress actionItemProgress;
    private final List<ProgressModel> list;

    public ProgressAdapter(ActionItemProgress action) {
        this.actionItemProgress = action;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(ItemProgressBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(List<ProgressModel> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    class VH extends RecyclerView.ViewHolder {

        private ItemProgressBinding binding;


        private VH(ItemProgressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void setData(ProgressModel data) {
            String payment = "RM" + data.getPayment();

            binding.description.setText(data.getDescription());

            binding.pay.setText(getPayText(data));
            binding.pay.setOnClickListener(v -> actionItemProgress.onClick(data));
            binding.pay.setVisibility(data.getActive() ? View.VISIBLE : View.GONE);
            binding.pay.setBackground(ContextCompat.getDrawable(itemView.getContext(), getPayDrawable(data)));

            binding.payment.setText(payment);
            binding.note.setVisibility(data.getActive() ? View.GONE : View.VISIBLE);
        }

        private String getPayText(ProgressModel data) {
            String text = "";

            switch (data.getStatus()) {
                case NOTHING:
                    text = "Upload";
                    break;
                case SUCCESS:
                    text = "Approved";
                    break;
                case REJECT:
                    text = "decline";
                    break;
                case PENDING:
                    text = "in-process";
                    break;
            }

            return text;
        }

        private int getPayDrawable(ProgressModel data) {
            int drawable = 0;

            switch (data.getStatus()) {
                case NOTHING:
                case REJECT:
                    drawable = R.drawable.button_unpay;
                    break;
                case SUCCESS:
                case PENDING:
                    drawable = R.drawable.button_paid;
                    break;
            }

            return drawable;
        }
    }


    public interface ActionItemProgress {

        void onClick(ProgressModel data);
    }
}
