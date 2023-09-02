package com.example.to_do_list_app.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

    @Entity(tableName = "Tasks_Table")
    public class Task implements Serializable {


        @PrimaryKey(autoGenerate = true)
        int ID = 0;

        @ColumnInfo(name = "title")
        String title = "";


        @ColumnInfo(name = "description")
        String description = "";


        @ColumnInfo(name = "date")
        String date = "";

        @ColumnInfo(name = "dueDate")
        String dueDate = "";


        @ColumnInfo(name = "completed")
        boolean completed = false;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
        public String getDueDate() {
            return dueDate;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

    }
