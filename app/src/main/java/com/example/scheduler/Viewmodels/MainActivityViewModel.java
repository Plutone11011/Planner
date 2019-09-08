package com.example.scheduler.Viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.scheduler.Model.DateStatePOJO;
import com.example.scheduler.Model.TasksTable;
import com.example.scheduler.Model.datetimePOJO;
import com.example.scheduler.Repository.TasksRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {




    private LiveData<List<datetimePOJO>> dates_names ;
    private TasksRepo tasksRepo ;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        tasksRepo = new TasksRepo(application);
        dates_names = tasksRepo.getAllDates();
    }

    public LiveData<List<datetimePOJO>> getDates() {
        return dates_names;
    }

    public void nukeTable(){
        tasksRepo.nukeTable();
    }
}
