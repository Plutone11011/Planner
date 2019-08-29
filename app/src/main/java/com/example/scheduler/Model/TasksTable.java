package com.example.scheduler.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tasks")
public class TasksTable {

    @PrimaryKey(autoGenerate = true)
    private @NonNull Integer id ;

    private @NonNull String name ;

    private @NonNull String date ;


    //ongoing, completed, pending
    private String state ;
    //standard values are homework, training, family
    private String type ;

    //need to map human friendly priority descriptors to numbers
    private String priority ;

    public TasksTable(@NonNull String name, @NonNull String date, String state, String type, String priority) {
        this.name = name;
        this.date = date;
        this.state = state;
        this.type = type;
        this.priority = priority;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public String getState() {
        return state;
    }

    public String getType() {
        return type;
    }

    public String getPriority() {
        return priority;
    }
}
