package com.afiq.myapplication.recycler_adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.afiq.myapplication.R;
import com.afiq.myapplication.databinding.ItemProjectAdminBinding;
import com.afiq.myapplication.models.ProjectModel;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdminAdapter extends RecyclerView.Adapter<ProjectAdminAdapter.VH> {

    private static final List<ProjectModel> PROJECTS = new ArrayList<>();

    private AdminProjectActionItem actionItem;


    public ProjectAdminAdapter(AdminProjectActionItem actionItem) {
        this.actionItem = actionItem;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemProjectAdminBinding binding = ItemProjectAdminBinding.inflate(inflater);
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.update(PROJECTS.get(position));
    }

    @Override
    public int getItemCount() {
        return PROJECTS.size();
    }

    public void update(List<ProjectModel> list) {
        PROJECTS.clear();
        PROJECTS.addAll(list);
        notifyDataSetChanged();
    }


    class VH extends RecyclerView.ViewHolder {

        private ItemProjectAdminBinding binding;


        private VH(ItemProjectAdminBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void update(ProjectModel data) {
            boolean status_state = data.getTotalCost().equals(data.getTotalPay());

            String label = data.getLabel();
            String revenue = "Revenue: RM" + data.getTotalCost();

            binding.label.setText(label);
            binding.revenue.setText(revenue);
            binding.status.setText(getStatusText(status_state));
            binding.status.setTextColor(getStatusColor(status_state));

            binding.getRoot().setOnClickListener(v -> actionItem.onClick(data));
        }

        private String getStatusText(boolean state) {
            return state ? "complete" : "pending";
        }

        private int getStatusColor(boolean state) {
            int status_color = state ? R.color.green_500 : R.color.orange_500;
            return ContextCompat.getColor(binding.getRoot().getContext(), status_color);
        }
    }


    public interface AdminProjectActionItem {
        void onClick(ProjectModel data);
    }
}
