package com.aggregator.aggregator_website.services.globalerrors;

import com.aggregator.aggregator_website.exceptionhandlers.notifications.ErrorMessageResponse;
import com.aggregator.aggregator_website.exceptionhandlers.notifications.ExceptionType;
import com.aggregator.aggregator_website.services.interfaceglobalerror.IGlobalError;
import org.springframework.validation.ObjectError;

import java.net.UnknownHostException;

public class ErrorUnknownHost implements IGlobalError {
    @Override
    public ObjectError validateField(String objName, ErrorMessageResponse err) {

        if(!err.getErrorMessages().isEmpty()){
            for (ExceptionType e : err.getErrorMessages()){
                if(e.getType().equals(UnknownHostException.class)){
                    return new ObjectError(objName,"Не верно указан host сайта !!!");
                }
            }
        }
        return new ObjectError(objName,"");
    }
}
