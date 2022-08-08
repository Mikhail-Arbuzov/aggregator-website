package com.aggregator.aggregator_website.services.globalerrors;

import com.aggregator.aggregator_website.exceptionhandlers.notifications.ErrorMessageResponse;
import com.aggregator.aggregator_website.exceptionhandlers.notifications.ExceptionType;
import com.aggregator.aggregator_website.services.interfaceglobalerror.IGlobalError;
import org.springframework.validation.ObjectError;

import java.net.MalformedURLException;

public class ErrorMalformedURL implements IGlobalError {

    @Override
    public ObjectError validateField(String objName, ErrorMessageResponse err) {

        if(!err.getErrorMessages().isEmpty()){
            for (ExceptionType e : err.getErrorMessages()){
                if(e.getType().equals(MalformedURLException.class)){
                    return new ObjectError(objName,"Не верный синтаксис в URL-адресе, возможно нет протокола HTTP !!!");
                }
            }
        }
        return new ObjectError(objName,"");
    }
}
