package com.example.scheduler;

public enum PriorityEnum {

    Urgent(0),
    Important(1),
    Secondary(2);

    private final int value ;

    PriorityEnum(int value) {
        this.value = value;
    }

    public int getValue(){
        return value ;
    }
}
