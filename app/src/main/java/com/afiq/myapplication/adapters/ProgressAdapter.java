package com.afiq.myapplication.adapters;

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

        ProgressModel data = new ProgressModel();
        data.setDescription("Stage 1");

        ProgressModel data1 = new ProgressModel();
        data1.setActive(true);
        data1.setStatus(ProgressModel.STATUS.REJECT);
        data1.setDescription("Stage 2");

        ProgressModel data2 = new ProgressModel();
        data2.setActive(true);
        data2.setDescription("Stage 3");

        ProgressModel data3 = new ProgressModel();
        data3.setDescription("Stage 4");

        ProgressModel data4 = new ProgressModel();
        data4.setDescription("Stage 5");

        list.add(data);
        list.add(data1);
        list.add(data2);
        list.add(data3);
        list.add(data4);
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
            binding.pay.setVisibility(data.getActive() ? View.VISIBLE : View.INVISIBLE);
            binding.pay.setBackground(ContextCompat.getDrawable(itemView.getContext(), getPayDrawable(data)));

            binding.payment.setText(payment);
            binding.note.setVisibility(data.getActive() ? View.GONE : View.VISIBLE);
        }

        private String getPayText(ProgressModel data) {
            String text = "";

            switch (data.getStatus()) {
                case NOTHING:
                    text = "pay now";
                    break;
                case SUCCESS:
                    text = "paid";
                    break;
                case REJECT:
                    text = "decline";
                    break;
                case PENDING:
                    text = "process";
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
