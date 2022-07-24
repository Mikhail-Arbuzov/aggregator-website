package com.aggregator.aggregator_website.services.exceptionregistration;

public class EmailIsAlreadyThereException extends Exception {
    public EmailIsAlreadyThereException(String message){
        super(message);
    }
}
