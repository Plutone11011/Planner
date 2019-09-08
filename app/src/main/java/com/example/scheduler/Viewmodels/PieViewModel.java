package com.example.scheduler.Viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.scheduler.Model.datetimePOJO;
import com.example.scheduler.Model.nameClassPOJO;
import com.example.scheduler.Repository.TasksRepo;

import java.util.List;

public class PieViewModel extends AndroidViewModel {

    private TasksRepo tasksRepo ;
    private LiveData<List<nameClassPOJO>> ListOfNamesandTypes ;

    public PieViewModel(@NonNull Application application) {
        super(application);
        this.tasksRepo = new TasksRepo(application);
        ListOfNamesandTypes = tasksRepo.getAllTypes();
    }

    public LiveData<List<nameClassPOJO>> getListOfNamesandTypes() {
        return ListOfNamesandTypes;
    }
}
