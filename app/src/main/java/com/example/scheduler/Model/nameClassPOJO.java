package com.example.scheduler.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

public class nameClassPOJO {

    @ColumnInfo(name = "name")
    @NonNull
    public String Name ;

    @ColumnInfo(name = "type")
    public String Type ;
}
