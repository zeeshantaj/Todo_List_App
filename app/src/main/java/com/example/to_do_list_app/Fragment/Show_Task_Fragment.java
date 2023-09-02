package com.example.to_do_list_app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.to_do_list_app.Adapter.Task_Adapter;
import com.example.to_do_list_app.Database.RoomDB;
import com.example.to_do_list_app.Model.SharedViewModel;
import com.example.to_do_list_app.Model.Task;
import com.example.to_do_list_app.Model.Task_Details;
import com.example.to_do_list_app.R;
import com.example.to_do_list_app.Utils.FragmentUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Show_Task_Fragment extends Fragment {



    public Show_Task_Fragment() {
        // Required empty public constructor
    }

    private FloatingActionButton addTaskBtn;
    private FrameLayout fragmentContainer;
    private FragmentManager fragmentManager;
    private View view;

    private RecyclerView recyclerView;
    private SearchView searchBtn;
    private Task_Adapter adapter;
    private List<Task> list;
    private RoomDB database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_show__task_, container, false);

        addTaskBtn = view.findViewById(R.id.fab_add);
        recyclerView = view.findViewById(R.id.recycler_home);
        searchBtn = view.findViewById(R.id.searchView_home);
        fragmentContainer = getActivity().findViewById(R.id.fragment_container);
        fragmentManager = getActivity().getSupportFragmentManager();
        database = RoomDB.getInstance(getActivity());

        addTaskBtn.setOnClickListener((View)->{
            FragmentUtils.setFragment(fragmentManager,R.id.fragment_container,new Add_Node_Fragment());
        });

        list = new ArrayList<>();
        list = database.mainDAO().getALl();

        

        adapter = new Task_Adapter(getActivity(),list, new Task_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task item) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("ACTION REQUIRE")
                        .setMessage("Do you want to Delete Or Edit This Task Data")
                        .setPositiveButton("Edit Task", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
                                viewModel.setSelectedTask(item);
                                Add_Node_Fragment fragment = new Add_Node_Fragment();
                                FragmentUtils.setFragment(fragmentManager, R.id.fragment_container, fragment);
                             }
                        })
                        .setNegativeButton("Delete Task", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                database.mainDAO().delete(item);
                                Toast.makeText(getActivity(), "Task Deleted " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                                list.remove(item);
                                adapter.notifyDataSetChanged();

                            }
                        })
                        .show();
            }

        });
        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        searchBtn.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
        return view;

    }

    private void filter(String filet){
        List<Task> filterList = new ArrayList<>();
        for (Task task:list){
            if (task.getTitle().toLowerCase().contains(filet.toLowerCase())
                ||task.getDescription().toLowerCase().contains(filet.toLowerCase())){
                filterList.add(task);
            }
        }
        adapter.filterList(filterList);
    }
}