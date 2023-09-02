package com.example.to_do_list_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.to_do_list_app.Fragment.Add_Node_Fragment;
import com.example.to_do_list_app.Fragment.Show_Task_Fragment;
import com.example.to_do_list_app.Utils.FragmentUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {


    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        FragmentUtils.setFragment(fragmentManager,R.id.fragment_container,new Show_Task_Fragment());
    }
}