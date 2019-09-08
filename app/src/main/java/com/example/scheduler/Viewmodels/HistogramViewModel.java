package com.example.scheduler.Viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.scheduler.Model.datetimePOJO;
import com.example.scheduler.Model.namePrioPOJO;
import com.example.scheduler.Repository.TasksRepo;

import java.util.List;

public class HistogramViewModel extends AndroidViewModel {

    private TasksRepo tasksRepo ;
    private LiveData<List<namePrioPOJO>> ListOfNamesandPriorities ;


    public HistogramViewModel(@NonNull Application application) {
        super(application);
        this.tasksRepo = new TasksRepo(application);
        ListOfNamesandPriorities = tasksRepo.getAllPriorities();
    }

    public LiveData<List<namePrioPOJO>> getListOfNamesandPriorities() {
        return ListOfNamesandPriorities;
    }
}
