package com.example.scheduler.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

public class datetimePOJO {

    @ColumnInfo(name = "name")
    @NonNull
    public String Name ;

    @ColumnInfo(name = "date")
    @NonNull
    public String Date ;

}
