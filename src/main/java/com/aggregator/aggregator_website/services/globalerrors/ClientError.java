package com.aggregator.aggregator_website.services.globalerrors;

import com.aggregator.aggregator_website.services.exceptionclient.ErrorResponseStatus;
import com.aggregator.aggregator_website.services.interfaceglobalerror.IGlobalClientError;
import org.springframework.validation.ObjectError;

public class ClientError implements IGlobalClientError {

    @Override
    public ObjectError validateResponseCode(String objName, ErrorResponseStatus errorResponseStatus) {
        if(errorResponseStatus !=null){
            int httpStatus = errorResponseStatus.getRawStatusCode();
            String reasonPhrase = errorResponseStatus.getReason();
            String defaultMessage ="При запросе на указанный URL возникла ошибка на стороне клиента!!!" +
                    "\nHTTP status code: "+ httpStatus +" - " + reasonPhrase;
            return new ObjectError(objName,defaultMessage);
        }
        return new ObjectError(objName,"");
    }
}
