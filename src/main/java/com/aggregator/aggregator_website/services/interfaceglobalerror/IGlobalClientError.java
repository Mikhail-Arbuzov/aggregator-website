package com.aggregator.aggregator_website.services.interfaceglobalerror;

import com.aggregator.aggregator_website.services.exceptionclient.ErrorResponseStatus;
import org.springframework.validation.ObjectError;

public interface IGlobalClientError {
    ObjectError validateResponseCode(String objName, ErrorResponseStatus errorResponseStatus);
}
