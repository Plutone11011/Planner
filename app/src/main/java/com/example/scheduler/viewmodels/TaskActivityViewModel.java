package com.example.scheduler.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;

public class TaskActivityViewModel extends ViewModel {



    //private MutableLiveData<String> nameTask ;
    private Date task_date ;

    public Date getTask_date() {
        return task_date;
    }

    public void setTask_date(Date task_date) {
        this.task_date = task_date;
    }

}