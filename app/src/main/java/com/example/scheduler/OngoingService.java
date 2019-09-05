package com.example.scheduler;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.scheduler.Repository.TasksRepo;

public class OngoingService extends Service {

    private TasksRepo tasksRepo ;

    @Override
    public void onCreate() {
        super.onCreate();
        tasksRepo = new TasksRepo(getApplication());
    }

    public OngoingService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SERVICE", "command");
        Log.d("SERVICE",((Integer)intent.getIntExtra("id",0)).toString());
        tasksRepo.updateStatetoOngoing("Ongoing",intent.getIntExtra("id",0));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("SERVICE", "bind");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
