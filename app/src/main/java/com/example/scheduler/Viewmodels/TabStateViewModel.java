package com.example.scheduler.Viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.scheduler.Model.nameStatePOJO;
import com.example.scheduler.Repository.TasksRepo;

import java.util.List;

public class TabStateViewModel extends AndroidViewModel {

    private TasksRepo tasksRepo ;
    private LiveData<List<nameStatePOJO>> ListOfNamesandStates ;


    public TabStateViewModel(@NonNull Application application) {
        super(application);
        this.tasksRepo = new TasksRepo(application);
        ListOfNamesandStates = tasksRepo.getAllStates();
    }

    public LiveData<List<nameStatePOJO>> getListOfNamesandStates() {
        return ListOfNamesandStates;
    }
}
