package com.example.scheduler.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class TasksDAO {

    @Update
    public abstract void update(TasksTable tasksTable);

    @Insert
    public abstract void insert(TasksTable tasksTable);

    @Delete
    public abstract void delete(TasksTable tasksTable);

    @Transaction
    public void insertdeleteWithTransaction(TasksTable oldtable, TasksTable newtable){
        delete(oldtable);
        insert(newtable);
    }

    @Query("DELETE FROM Tasks")
    public abstract void nukeTable();

    //query that returns a list of all the dates and names of the tasks
    @Query("SELECT date, name FROM Tasks")
    public abstract LiveData<List<datetimePOJO>> getAllDates();

    //query that returns a list of all the priorities and names of the tasks
    @Query("SELECT name, priority FROM Tasks")
    public abstract LiveData<List<namePrioPOJO>> getAllPriorities();

    //query that returns a list of all the types and names of the tasks
    @Query("SELECT name, type FROM Tasks")
    public abstract LiveData<List<nameClassPOJO>> getAllTypes();

    @Query("SELECT name FROM Tasks WHERE date = :d")
    public abstract List<String> getTaskNamesWithSameDate(String d);

    //returns the only tuple with a specific name and date
    @Query("SELECT * FROM Tasks WHERE date = :d and name = :n")
    public abstract TasksTable getTaskWithPrimaryKey(String d, String n);

    @Query("DELETE FROM Tasks WHERE date = :d and name = :n")
    public abstract void deleteTaskWithPrimaryKey(String d, String n);

    @Query("SELECT date, state FROM Tasks")
    public abstract LiveData<List<DateStatePOJO>> getDataForLineChart();

    @Query("UPDATE Tasks SET state = \"Ongoing\" WHERE name = :name and date = :date")
    public abstract void updateStatetoOngoing(String name, String date);

    @Query("UPDATE Tasks SET state = \"Completed\" WHERE name = :name and date = :date")
    public abstract void updateStatetoCompleted(String name, String date);

    @Query("SELECT * FROM Tasks")
    public abstract LiveData<List<TasksTable>> getEveryTask();

}

