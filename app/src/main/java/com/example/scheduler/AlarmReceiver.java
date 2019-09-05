package com.example.scheduler;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.scheduler.UI.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {

    private final String channelName = "TasksChannel";
    private final String channelID = "TID";
    private final String channelDescription = "Channel for task notifications";
    private final int notificationID = 1;
    private SharedPreferences sharedPrefPendingIntentID;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Log.d("BROADCAST","ohi");
        //throw new UnsupportedOperationException("Not yet implemented");
        initChannels(context);

        Intent intentForService = new Intent(context, OngoingService.class);
        intentForService.putExtra("id",intent.getIntExtra("id",0));
        Intent intentforMainTap = new Intent(context, MainActivity.class);


        Notification notification =  new NotificationCompat.Builder(context,channelID)
                .setSmallIcon(R.drawable.ic_event_note_white_24dp)
                .setContentTitle("Time to get to work!")
                .setContentText("It's " + intent.getStringExtra("name")+" time. Choose whether you want to postpone it or do it")
                .setContentIntent(PendingIntent.getActivity(context,intent.getIntExtra("Pending id",0)
                        ,intentforMainTap,PendingIntent.FLAG_CANCEL_CURRENT)) //with this flag the pending intent that caused this broadcast is canceled
                .addAction(new NotificationCompat.Action.Builder(R.drawable.ic_done_white_24dp,"Ongoing",
                        PendingIntent.getService(context, intent.getIntExtra("Pending id",0),intentForService,PendingIntent.FLAG_CANCEL_CURRENT))
                        .build()).build();

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(context,intent.getIntExtra("id_for_alarmmanager",0)
        //        ,intent,PendingIntent.FLAG_NO_CREATE);
        //if (pendingIntent!=null)
        //    pendingIntent.cancel();
        nm.notify(notificationID,notification);
    }

    //channel setup for android oreo and beyond
    public void initChannels(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(channelID,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(channelDescription);
        notificationManager.createNotificationChannel(channel);
    }
}
