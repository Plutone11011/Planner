package com.example.scheduler.Viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.scheduler.Model.TasksTable;
import com.example.scheduler.Repository.TasksRepo;

import java.util.List;

public class ListTaskViewModel extends AndroidViewModel {

    private TasksRepo tasksRepo ;
    private LiveData<List<TasksTable>> rows ;


    public ListTaskViewModel(@NonNull Application application) {
        super(application);
        this.tasksRepo = new TasksRepo(application);
        this.rows = tasksRepo.getEveryTask();
    }

    public LiveData<List<TasksTable>> getRows() {
        return rows;
    }
}
