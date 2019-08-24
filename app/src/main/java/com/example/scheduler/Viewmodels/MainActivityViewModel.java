package com.example.scheduler.Viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.scheduler.Model.TasksTable;
import com.example.scheduler.Repository.TasksRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {




    private LiveData<List<String>> dates ;
    private TasksRepo tasksRepo ;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        tasksRepo = new TasksRepo(application);
        dates = tasksRepo.getAllDates();
    }

    public LiveData<List<String>> getDates() {
        return dates;
    }

    public void nukeTable(){
        tasksRepo.nukeTable();
    }



}
