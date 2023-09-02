package com.example.to_do_list_app.Model;

import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private Task selectedTask;

    public Task getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(Task task) {
        selectedTask = task;
    }
    public void clearSelectedTask() {
        selectedTask = null;
    }
}