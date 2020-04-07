package com.afiq.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        data.setDescription("Start project  dasd asd asdas das das dasd asd asd asdas d as");

        ProgressModel data1 = new ProgressModel();
        data1.setDescription("Outer layout finish");

        ProgressModel data2 = new ProgressModel();
        data2.setDescription("Outer layout finish");

        list.add(data);
        list.add(data1);
        list.add(data2);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(ItemProgressBinding.inflate(LayoutInflater.from(parent.getContext())), viewType);
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


        private VH(ItemProgressBinding binding, int type) {
            super(binding.getRoot());
            this.binding = binding;

        }

        private void setData(ProgressModel data) {

            binding.getRoot().setOnClickListener(v -> actionItemProgress.onClick(data));
        }

    }


    public interface ActionItemProgress {

        void onClick(ProgressModel data);
    }
}
