package com.aggregator.aggregator_website.services.globalerrors;

import com.aggregator.aggregator_website.exceptionhandlers.notifications.ErrorMessageResponse;
import com.aggregator.aggregator_website.exceptionhandlers.notifications.ExceptionType;
import com.aggregator.aggregator_website.services.interfaceglobalerror.IGlobalError;
import org.springframework.validation.ObjectError;

import java.io.IOException;

public class ErrorIO implements IGlobalError {
    @Override
    public ObjectError validateField(String objName, ErrorMessageResponse err) {
        if(!err.getErrorMessages().isEmpty()){
            for (ExceptionType e : err.getErrorMessages()){
                if(e.getType().equals(IOException.class)){
                    return new ObjectError(objName,"Некорректный ввод URL-адреса или вывод его контекста !!!");
                }
            }
        }
        return new ObjectError(objName,"");
    }
}

