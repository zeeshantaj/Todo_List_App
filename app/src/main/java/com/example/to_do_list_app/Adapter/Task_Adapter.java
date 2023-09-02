package com.example.to_do_list_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_list_app.Database.MainDAO;
import com.example.to_do_list_app.Database.RoomDB;
import com.example.to_do_list_app.Model.Task;
import com.example.to_do_list_app.Model.Task_Details;
import com.example.to_do_list_app.R;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Task_Adapter extends RecyclerView.Adapter<Task_Adapter.ViewHolder> {

    List<Task> detailsList;
    private OnItemClickListener itemClickListener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(Task item);

    }
    public Task_Adapter(Context context,List<Task> detailsList,OnItemClickListener clickListener) {
        this.detailsList = detailsList;
        this.itemClickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public Task_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Task_Adapter.ViewHolder holder, int position) {
        Task details = detailsList.get(position);
        holder.title.setText(details.getTitle());
        holder.details.setText(details.getDescription());
        holder.details.setSelected(true);
        holder.date.setText("Due Date: " + details.getDate());


        holder.taskCheck.setOnCheckedChangeListener(null); // Prevent listener from being triggered initially
        holder.taskCheck.setChecked(details.isCompleted());

        holder.taskCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            details.setCompleted(isChecked);

            RoomDB database;
            database = RoomDB.getInstance(context.getApplicationContext());

            // Update the completion status in the Room database
            database.mainDAO().isCompleted(details.getID(), isChecked);

            if (isChecked) {
                holder.completionStatus.setVisibility(View.VISIBLE);
                Toast.makeText(context, "Task checked: " + details.isCompleted(), Toast.LENGTH_SHORT).show();
            } else {
                holder.completionStatus.setVisibility(View.GONE);
                Toast.makeText(context, "Task unchecked: " + details.isCompleted(), Toast.LENGTH_SHORT).show();
            }
        });

//        holder.taskCheck.setOnCheckedChangeListener(null); // Prevent listener from being triggered initially
//        holder.taskCheck.setChecked(details.isCompleted());
//
//        holder.taskCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            details.setCompleted(isChecked);
//            if (isChecked) {
//
//                RoomDB database;
//                database = RoomDB.getInstance(context.getApplicationContext());
//                database.mainDAO().isCompleted(details.getID(),true);
//                Toast.makeText(context, "Task checked: " + details.isCompleted(), Toast.LENGTH_SHORT).show();
//                if (details.isCompleted()){
//                    holder.taskCheck.setChecked(true);
//                }
//
//            }
//        });


        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(details);
            }
        });
    }

    @Override
    public int getItemCount() {
        return detailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title,details, date,completionStatus;
        private MaterialCheckBox taskCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_titleList);
            details = itemView.findViewById(R.id.txt_DetailsList);
            date = itemView.findViewById(R.id.txt_dateList);
            taskCheck = itemView.findViewById(R.id.task_checkbox);
             completionStatus = itemView.findViewById(R.id.txtCompletionStatus);



        }
    }
    public void filterList(List<Task> filterList){
        detailsList = filterList;
        notifyDataSetChanged();
    }
}
