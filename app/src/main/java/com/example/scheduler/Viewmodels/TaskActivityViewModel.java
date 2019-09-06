package com.example.scheduler.Viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.scheduler.Model.TasksTable;
import com.example.scheduler.Repository.TasksRepo;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class TaskActivityViewModel extends AndroidViewModel {

    public TaskActivityViewModel(@NonNull Application application){
        super(application);
        tasksRepo = new TasksRepo(application);
        //if there are other live data objects to update, here
    }

    private TasksRepo tasksRepo ;

    //private MutableLiveData<String> nameTask ;
    private String task_date ;//contains whole date dd/MM/yyyy hh:mm


    private String priority, type, name, state ;
    //private Integer id ;


    private Boolean intentForUpdate ;

    public Boolean getIntentForUpdate() {
        return intentForUpdate;
    }

    public void setIntentForUpdate(Boolean intentForUpdate) {
        this.intentForUpdate = intentForUpdate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTask_date() {
        return task_date;
    }

    public void setTask_date(String task_date) {
        this.task_date = task_date;
    }

    public void insert(TasksTable tasksTable){
        tasksRepo.insert(tasksTable);
    }

    public void update(TasksTable tasksTable){
        tasksRepo.update(tasksTable);
    }

    public void delete(TasksTable tasksTable){
        tasksRepo.delete(tasksTable);
    }
}