package com.example.scheduler.Viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.scheduler.Model.TasksTable;
import com.example.scheduler.Repository.TasksRepo;

public class SelectTaskFragmentViewModel extends AndroidViewModel {

    private TasksRepo tasksRepo ;
    private Integer action ;

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    private String t_name, t_date ;

    public SelectTaskFragmentViewModel(@NonNull Application application) {
        super(application);
        this.tasksRepo = new TasksRepo(application);
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getT_date() {
        return t_date;
    }

    public void setT_date(String t_date) {
        this.t_date = t_date;
    }




    public void delete(TasksTable tasksTable){
        tasksRepo.delete(tasksTable);
    }

    public void getTaskWithPrimaryKey(String date, String name, TasksRepo.TaskWithPrimaryKey listener){
        tasksRepo.getTaskWithPrimaryKey(date,name,listener);
    }
    public void deleteTaskWithPrimaryKey(String date, String name){
        tasksRepo.deleteTaskWithPrimaryKey(date, name);
    }


    public void updateStatetoCompleted(String name, String date){
        tasksRepo.updateStatetoCompleted(name, date);
    }
}
