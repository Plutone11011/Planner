package com.example.scheduler.Repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.scheduler.Model.DateStatePOJO;
import com.example.scheduler.Model.TasksDAO;
import com.example.scheduler.Model.TasksDatabase;
import com.example.scheduler.Model.TasksTable;
import com.example.scheduler.Model.datetimePOJO;
import com.example.scheduler.Model.nameClassPOJO;
import com.example.scheduler.Model.namePrioPOJO;
import com.example.scheduler.Model.nameStatePOJO;

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

    public void deleteTaskWithPrimaryKey(String date, String name){
        new DeleteTaskWithPrimaryKeyAsyncTask(tasksDAO,name,date).execute();
    }
    //deletes every row
    public void nukeTable(){
        new NukeAsyncTask(tasksDAO).execute();
    }

    //helper method called in post execute actually returns the value
    public void getTaskNamesWithSameDate(String d, TaskNamesWithSameDateListener appContext){
        new getTaskNamesWithSameDateAsyncTask(tasksDAO, appContext).execute(d);

    }

    public void getTaskWithPrimaryKey(String date, String name, TaskWithPrimaryKey listener){
        new getTaskWithPrimaryKeyAsyncTask(tasksDAO,name,date,listener).execute();
    }
    //livedata methods
    public LiveData<List<datetimePOJO>> getAllDates(){
        return tasksDAO.getAllDates() ;
    }
    public LiveData<List<namePrioPOJO>> getAllPriorities(){ return tasksDAO.getAllPriorities();}
    public LiveData<List<nameStatePOJO>> getAllStates(){ return tasksDAO.getAllStates();}
    public LiveData<List<nameClassPOJO>> getAllTypes(){ return tasksDAO.getAllTypes();}
    public LiveData<List<DateStatePOJO>> getDataForLineChart(){ return tasksDAO.getDataForLineChart();}
    /**** From here on, asynctask classes****/
    //every method except from the ones returning live data objects need to
    //be executed in the bg, so there's an asynctask for each method
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

    private static class DeleteTaskWithPrimaryKeyAsyncTask extends AsyncTask<Void, Void, Void>{

        private TasksDAO tasksDAO ;
        private String name, date ;

        public DeleteTaskWithPrimaryKeyAsyncTask(TasksDAO tasksDAO, String name, String date) {
            this.name = name;
            this.date = date;
            this.tasksDAO = tasksDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tasksDAO.deleteTaskWithPrimaryKey(date,name);
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

    //calls a callback function to return results to the UI thread
    //the calling component needs to implement the interface

    private static class getTaskWithPrimaryKeyAsyncTask extends AsyncTask<Void,Void,TasksTable>{

        private TasksDAO tasksDAO ;
        private String name, date ;
        private TaskWithPrimaryKey listener ;

        public getTaskWithPrimaryKeyAsyncTask(TasksDAO tasksDAO, String name, String date, TaskWithPrimaryKey taskWithPrimaryKey) {
            this.tasksDAO = tasksDAO;
            this.name = name;
            this.date = date;
            this.listener = taskWithPrimaryKey;
        }

        @Override
        protected TasksTable doInBackground(Void... voids) {
            return tasksDAO.getTaskWithPrimaryKey(date,name);
        }

        @Override
        protected void onPostExecute(TasksTable tasksTable) {
            Log.d("TAG",tasksTable.getDate());
            Log.d("TAG",tasksTable.getName());
            Log.d("TAG",tasksTable.getPriority());
            listener.onTaskResult(tasksTable);
        }
    }
    public interface TaskWithPrimaryKey{
        void onTaskResult(TasksTable tasksTable);
    }

    private static class getTaskNamesWithSameDateAsyncTask extends AsyncTask<String, Void, List<String>>{

        private TasksDAO tasksDAO ;
        private TaskNamesWithSameDateListener listener;//callback that allows main thread to listen to this asynctask results

        private getTaskNamesWithSameDateAsyncTask(TasksDAO tasksDAO, TaskNamesWithSameDateListener listener) {
            this.tasksDAO = tasksDAO;
            this.listener = listener ;
        }

        @Override
        protected List<String> doInBackground(String... strings) {
            return tasksDAO.getTaskNamesWithSameDate(strings[0]);
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            listener.onTaskResult(strings);
        }
    }

    //interface that main activity implements to listen to results
    public interface TaskNamesWithSameDateListener{
        void onTaskResult(List<String> tasksWithSameDate);
    }



}
