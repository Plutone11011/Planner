package com.example.scheduler.repo;

import androidx.lifecycle.LifecycleObserver;

//repo is a singleton class because we only want a single
//object opening connections to the database or network
public class TasksRepo implements LifecycleObserver {

    private static TasksRepo instance ;

    public static TasksRepo getInstance(){
        if (instance == null){
            instance = new TasksRepo();
        }
        return instance;
    }
}
