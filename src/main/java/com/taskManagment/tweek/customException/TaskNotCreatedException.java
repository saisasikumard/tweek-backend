package com.taskManagment.tweek.customException;

public class TaskNotCreatedException extends RuntimeException{
    public TaskNotCreatedException(String msg) {
        super(msg);
    }
}
