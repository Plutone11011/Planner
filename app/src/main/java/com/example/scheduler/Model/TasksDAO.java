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

    //query that returns a list of all the dates of the tasks
    @Query("SELECT date, name FROM Tasks")
    LiveData<List<datetimePOJO>> getAllDates();

    @Query("SELECT name FROM Tasks WHERE date = :d")
    List<String> getTaskNamesWithSameDate(String d);
}
