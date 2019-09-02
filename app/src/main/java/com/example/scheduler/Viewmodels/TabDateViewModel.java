package com.example.scheduler.Viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.scheduler.Model.datetimePOJO;
import com.example.scheduler.Repository.TasksRepo;

import java.util.List;

public class TabDateViewModel extends AndroidViewModel {

    private TasksRepo tasksRepo ;
    private LiveData<List<datetimePOJO>> ListOfNamesandDates ;

    public TabDateViewModel(@NonNull Application application) {
        super(application);
        this.tasksRepo = new TasksRepo(application);
        ListOfNamesandDates = tasksRepo.getAllDates();
    }

    public LiveData<List<datetimePOJO>> getAllDates(){
        return ListOfNamesandDates;
    }


}
