package com.afiq.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afiq.myapplication.databinding.ItemProgressBinding;
import com.afiq.myapplication.models.ProgressModel;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.VH> {

    private final ActionItemProgress actionItemProgress;
    private final List<ProgressModel> list;

    public TimelineAdapter(ActionItemProgress action) {
        this.actionItemProgress = action;
        this.list = new ArrayList<>();

        ProgressModel data = new ProgressModel();
        data.setDescription("Start project");

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

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    class VH extends RecyclerView.ViewHolder {

        private ItemProgressBinding binding;


        private VH(ItemProgressBinding binding, int type) {
            super(binding.getRoot());
            this.binding = binding;

            binding.timeline.initLine(type);
        }

        private void setData(ProgressModel data) {
            binding.description.setText(data.getDescription());
        }
    }


    public interface ActionItemProgress {

        void onClick(ProgressModel data);
    }
}
