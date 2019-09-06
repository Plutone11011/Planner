package com.example.scheduler.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

public class namePrioPOJO {

    @ColumnInfo(name = "name")
    @NonNull
    public String Name ;

    @ColumnInfo(name = "priority")
    public String Priority ;
}
