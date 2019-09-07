package com.example.scheduler.Viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.scheduler.Model.DateStatePOJO;
import com.example.scheduler.Repository.TasksRepo;

import java.util.List;

public class TabLineChartViewModel extends AndroidViewModel {

    private TasksRepo tasksRepo ;
    private LiveData<List<DateStatePOJO>> mDateStatePojoList ;

    public TabLineChartViewModel(@NonNull Application application) {
        super(application);
        this.tasksRepo = new TasksRepo(application);
        mDateStatePojoList = tasksRepo.getDataForLineChart();
    }

    public LiveData<List<DateStatePOJO>> getmDateStatePojoList() {
        return mDateStatePojoList;
    }
}
