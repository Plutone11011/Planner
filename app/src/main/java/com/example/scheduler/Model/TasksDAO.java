package com.example.scheduler.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TasksDAO {

    @Update
    void update(TasksTable tasksTable);

    @Insert
    void insert(TasksTable tasksTable);

    @Delete
    void delete(TasksTable tasksTable);

    @Query("DELETE FROM Tasks")
    void nukeTable();

    //query that returns all tasks with a certain priority
    @Query("SELECT * FROM Tasks WHERE priority = :prio")
    List<TasksTable> getTasksWithPriority(String prio);

    //query that returns every task of a certain type
    @Query("SELECT * FROM Tasks WHERE type = :tp")
    List<TasksTable> getTasksOfType(String tp);

    //query that returns a list of all the dates and names of the tasks
    @Query("SELECT date, name FROM Tasks")
    LiveData<List<datetimePOJO>> getAllDates();

    //query that returns a list of all the priorities and names of the tasks
    @Query("SELECT name, priority FROM Tasks")
    LiveData<List<namePrioPOJO>> getAllPriorities();

    //query that returns a list of all the states and names of the tasks
    @Query("SELECT name, state FROM Tasks")
    LiveData<List<nameStatePOJO>> getAllStates();

    //query that returns a list of all the types and names of the tasks
    @Query("SELECT name, type FROM Tasks")
    LiveData<List<nameClassPOJO>> getAllTypes();

    @Query("SELECT name FROM Tasks WHERE date = :d")
    List<String> getTaskNamesWithSameDate(String d);

    //returns the only tuple with a specific name and date
    @Query("SELECT * FROM Tasks WHERE date = :d and name = :n")
    TasksTable getTaskWithPrimaryKey(String d, String n);

    @Query("DELETE FROM Tasks WHERE date = :d and name = :n")
    void deleteTaskWithPrimaryKey(String d, String n);

    @Query("SELECT date, state FROM Tasks")
    LiveData<List<DateStatePOJO>> getDataForLineChart();

    @Query("UPDATE Tasks SET state = \"Ongoing\" WHERE id = :id")
    void updateStatetoOngoing(Integer id);

}

