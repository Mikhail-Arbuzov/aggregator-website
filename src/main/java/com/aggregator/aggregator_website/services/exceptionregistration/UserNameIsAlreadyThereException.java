package com.aggregator.aggregator_website.services.exceptionregistration;

public class UserNameIsAlreadyThereException extends Exception {
    public UserNameIsAlreadyThereException(String message){
        super(message);
    }
}
