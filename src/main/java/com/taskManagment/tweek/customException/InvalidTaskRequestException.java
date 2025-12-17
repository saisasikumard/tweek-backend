package com.taskManagment.tweek.customException;

public class InvalidTaskRequestException extends RuntimeException{
    public InvalidTaskRequestException(String msg){
    super(msg);
    }
}
