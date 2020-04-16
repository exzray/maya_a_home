package com.afiq.myapplication.recycler_adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afiq.myapplication.databinding.ItemAdminProjectBinding;
import com.afiq.myapplication.models.ProjectModel;

import java.util.ArrayList;
import java.util.List;

public class AdminProjectAdapter extends RecyclerView.Adapter<AdminProjectAdapter.VH> {

    private static final List<ProjectModel> PROJECTS = new ArrayList<>();

    private AdminProjectActionItem actionItem;


    public AdminProjectAdapter(AdminProjectActionItem actionItem) {
        this.actionItem = actionItem;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemAdminProjectBinding binding = ItemAdminProjectBinding.inflate(inflater);
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
//        holder.update(PROJECTS.get(position));
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public void update(List<ProjectModel> list) {
        PROJECTS.clear();
        PROJECTS.addAll(list);
        notifyDataSetChanged();
    }


    class VH extends RecyclerView.ViewHolder {

        private ItemAdminProjectBinding binding;


        private VH(ItemAdminProjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void update(ProjectModel data) {
            binding.getRoot().setOnClickListener(v -> actionItem.onClick(data));
        }
    }


    public interface AdminProjectActionItem {
        void onClick(ProjectModel data);
    }
}
