package com.example.to_do_list_app.Database;



import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.to_do_list_app.Model.Task;
import com.example.to_do_list_app.Model.Task_Details;

import java.util.List;


@Dao
public interface MainDAO {

    @Insert(onConflict = REPLACE)
    void insert(Task Tasks_Table);


    @Query("SELECT * FROM Tasks_Table ORDER BY id DESC")
    List<Task> getALl();

    @Query("UPDATE Tasks_Table SET title = :title, description = :description, date = :date, dueDate = :dueDate WHERE ID = :id")
    void update(int id,String title,String description,String date,String dueDate);

    @Delete
    void delete(Task Tasks_Table);


    @Query("UPDATE Tasks_Table SET completed = :completed WHERE ID = :id")
    void isCompleted(int id,boolean completed);

}
