package com.example.to_do_list_app.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.to_do_list_app.Database.RoomDB;
import com.example.to_do_list_app.Model.SharedViewModel;
import com.example.to_do_list_app.Model.Task;
import com.example.to_do_list_app.R;
import com.example.to_do_list_app.Utils.FragmentUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Add_Node_Fragment extends Fragment {



    public Add_Node_Fragment() {
        // Required empty public constructor
    }



    private TextInputEditText title, description;
    private Button datePickerBtn,saveBtn;
    private TextView showTime;
    private String currentTime;
    private View view;
    private Calendar selectedDateTime;
    private Task taskDetails;
    private String formattedDateTime;
    private RoomDB database;
    private Task selectedTask;
    private SharedViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add__node_, container, false);
        title = view.findViewById(R.id.titleTextInput);
        description = view.findViewById(R.id.descTextInput);
        datePickerBtn = view.findViewById(R.id.date_pickerBtn);
        saveBtn = view.findViewById(R.id.savebtn);
        showTime = view.findViewById(R.id.text_dateShow);
        selectedDateTime = Calendar.getInstance();
        taskDetails = new Task();
        database = RoomDB.getInstance(getActivity());

//        try {
//            notes = (Notes) getIntent().getSerializableExtra("old_note");
//            editText_title.setText(notes.getTitle());
//            editText_notes.setText(notes.getNotes());
//            isOldNote = true;
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }


        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        selectedTask = viewModel.getSelectedTask();

        if (selectedTask != null) {
            // Now you have access to the selected Task object
            title.setText(selectedTask.getTitle());
            description.setText(selectedTask.getDescription());
            showTime.setText(selectedTask.getDueDate());
            showTime.setVisibility(View.VISIBLE);
        }


        saveBtn.setOnClickListener(v->{
            String title1 = title.getText().toString();
            String des = description.getText().toString();
            if (title1.isEmpty()){
                title.setError("Title is Empty");
                return;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            Date date = new Date();
            String currentDate = formatter.format(date);

           taskDetails.setTitle(title1);
           taskDetails.setDescription(des);
           taskDetails.setDate(currentDate);
           taskDetails.setDueDate(formattedDateTime);

           System.out.println("taskDetails"+taskDetails.getTitle());
           System.out.println("taskDetails"+taskDetails.getDescription());
           System.out.println("taskDetails"+taskDetails.getDate());
           System.out.println("taskDetails"+taskDetails.getDueDate());


           database.mainDAO().insert(taskDetails);




            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentUtils.setFragment(fragmentManager,R.id.fragment_container,new Show_Task_Fragment());




        });
        datePickerBtn.setOnClickListener(v->{
            showDateTimePicker();


        });

        return view;
    }
    private void showDateTimePicker() {
        int year = selectedDateTime.get(Calendar.YEAR);
        int month = selectedDateTime.get(Calendar.MONTH);
        int day = selectedDateTime.get(Calendar.DAY_OF_MONTH);
        int hour = selectedDateTime.get(Calendar.HOUR_OF_DAY);
        int minute = selectedDateTime.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDateTime.set(year, month, dayOfMonth);
                        showTimePicker();
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }
    private void showTimePicker() {
        int hour = selectedDateTime.get(Calendar.HOUR_OF_DAY);
        int minute = selectedDateTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedDateTime.set(Calendar.MINUTE, minute);
                        updateDateTime();
                    }
                },
                hour, minute, true);

        timePickerDialog.show();
    }
    private void updateDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
        formattedDateTime = sdf.format(selectedDateTime.getTime());
        showTime.setText(formattedDateTime);
        showTime.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.clearSelectedTask();
    }
}