package com.example.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateFormat extends SimpleDateFormat {

    public MyDateFormat(String pattern) {
        super(pattern);
    }

    public Date StringtoDate(String text) throws ParseException {

        return parse(text);
    }

    public String DatetoString(Date d){

        return format(d);
    }
}
