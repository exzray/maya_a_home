package com.afiq.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.afiq.myapplication.MainActivity;
import com.afiq.myapplication.databinding.ItemProjectBinding;
import com.afiq.myapplication.models.ProjectModel;
import com.afiq.myapplication.utilities.FirebaseHelper;
import com.afiq.myapplication.viewmodels.ProjectViewModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.VH> implements Observer<List<ProjectModel>> {

    private final List<ProjectModel> list = new ArrayList<>();
    private final ProjectActionItem actionItem;

    public ProjectAdapter(ProjectActionItem actionItem, MainActivity activity) {
        this.actionItem = actionItem;

        ProjectViewModel viewModel = new ViewModelProvider(activity).get(ProjectViewModel.class);
        viewModel.getProjects(FirebaseHelper.getUserProjectsQuery()).observe(activity, this);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(ItemProjectBinding.inflate(LayoutInflater.from(parent.getContext())));
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
    public void onChanged(List<ProjectModel> payments) {
        list.clear();
        list.addAll(payments);
        notifyDataSetChanged();
    }


    class VH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemProjectBinding binding;
        private ProjectModel data;

        private VH(ItemProjectBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        private void setData(ProjectModel data) {
            this.data = data;

            itemView.setOnClickListener(this);

            binding.progress.setMaxValue(data.getTotalCost());
            binding.progress.setValueAnimated(data.getTotalPay());
            binding.label.setText(data.getLabel().toUpperCase());
            binding.created.setText(stringDate(data.getCreated()));
        }

        private String stringDate(Date date) {
            return DateFormat.getDateInstance(DateFormat.SHORT).format(date);
        }

        @Override
        public void onClick(View v) {
            actionItem.onClick(data);
        }
    }

    public interface ProjectActionItem {

        void onClick(ProjectModel data);
    }
}
