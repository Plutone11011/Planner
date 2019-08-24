package com.example.scheduler.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.scheduler.Model.TasksDAO;
import com.example.scheduler.Model.TasksDatabase;
import com.example.scheduler.Model.TasksTable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

//repo is a singleton class because we only want a single
//object opening connections to the database or network
public class TasksRepo {

    //private static TasksRepo instance ;
    private TasksDAO tasksDAO ;


    public TasksRepo(Application application){
        //application is a subclass of context so we can pass it to get a db instance
        TasksDatabase tasksDatabase = TasksDatabase.getInstance(application);

        //it's an abstract method but using a builder in the db allows us
        //to call it because Room deals with generating the code
        tasksDAO = tasksDatabase.tasksDAO();
    }


    /*******repo api starts here*******/
    //insert
    public void insert(TasksTable tasksTable){
        new InsertTasksAsyncTask(tasksDAO).execute(tasksTable);
    }
    //update
    public void update(TasksTable tasksTable){
        new UpdateTasksAsyncTask(tasksDAO).execute(tasksTable);
    }
    //delete
    public void delete(TasksTable tasksTable){
        new DeleteTasksAsyncTask(tasksDAO).execute(tasksTable);
    }

    public void nukeTable(){
        new NukeAsyncTask(tasksDAO).execute();
    }

    //list of all dates to update calendar ui event indicators
    public LiveData<List<String>> getAllDates(){
        return tasksDAO.getAllDates() ;
    }
    //every method except from the ones returning live data objects need to
    //be executed in the bg, asynctask, there's an asynctask for each method

    //need to be static in order to avoid having references to repo and causing memory leaks
    //then there needs to be a dao field for the db operations and a constructor to initialize it
    private static class InsertTasksAsyncTask extends AsyncTask<TasksTable, Void, Void>{

        private TasksDAO tasksDAO ;

        private InsertTasksAsyncTask(TasksDAO tasksDAO){
            this.tasksDAO = tasksDAO ;
        }

        @Override
        protected Void doInBackground(TasksTable... tasksTables) {
            tasksDAO.insert(tasksTables[0]);
            return null;
        }
    }


    private static class UpdateTasksAsyncTask extends AsyncTask<TasksTable, Void, Void>{

        private TasksDAO tasksDAO ;

        private UpdateTasksAsyncTask(TasksDAO tasksDAO){
            this.tasksDAO = tasksDAO ;
        }

        @Override
        protected Void doInBackground(TasksTable... tasksTables) {
            tasksDAO.update(tasksTables[0]);
            return null;
        }
    }


    private static class DeleteTasksAsyncTask extends AsyncTask<TasksTable, Void, Void>{

        private TasksDAO tasksDAO ;

        private DeleteTasksAsyncTask(TasksDAO tasksDAO){
            this.tasksDAO = tasksDAO ;
        }

        @Override
        protected Void doInBackground(TasksTable... tasksTables) {
            tasksDAO.delete(tasksTables[0]);
            return null;
        }
    }

    private static class NukeAsyncTask extends AsyncTask<Void, Void, Void>{

        private TasksDAO taskDAO ;

        private NukeAsyncTask(TasksDAO tasksDAO){
            this.taskDAO = tasksDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDAO.nukeTable();
            return null;
        }
    }
}
