package com.bw.reference.exception;

public class EventAlreadyBookedException extends RuntimeException{
    public EventAlreadyBookedException (){
        super();
    }
    public EventAlreadyBookedException (String message){
        super(message);
    }
}
