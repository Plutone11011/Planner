package com.example.scheduler.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

public class DateStatePOJO {

    @ColumnInfo(name = "date")
    @NonNull
    public String Date ;

    @ColumnInfo(name = "state")
    public String State ;
}
