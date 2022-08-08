package com.aggregator.aggregator_website.services.interfaceglobalerror;

import com.aggregator.aggregator_website.exceptionhandlers.notifications.ErrorMessageResponse;
import org.springframework.validation.ObjectError;

public interface IGlobalError {
    ObjectError validateField(String objName, ErrorMessageResponse err);
}
