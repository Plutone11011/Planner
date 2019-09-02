package com.example.scheduler.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

public class nameStatePOJO {

    @ColumnInfo(name = "name")
    @NonNull
    public String Name ;

    @ColumnInfo(name = "state")
    public String State ;

}
