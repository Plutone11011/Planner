package com.example.scheduler.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = TasksTable.class, version = 2)
public abstract class TasksDatabase extends RoomDatabase {

    private static TasksDatabase instance;//singleton

    //room already takes care of this code
    public abstract TasksDAO tasksDAO();

    //synchronized ensures only one thread executes this method
    //avoiding the creation of 2 instances for race condition problems
    public static synchronized TasksDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),TasksDatabase.class,
                    "tasks_database")
                    .fallbackToDestructiveMigration() //deletes database and recreates it when there's a version update
                    .build();
        }
        return instance ;
    }
}
