package com.aggregator.aggregator_website.services.exceptionclient;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class ExceptionHttpStatus {

    private int statusCode;

    public ErrorResponseStatus getErrorResponseStatus(){

        ErrorResponseStatus errorResponseStatus = null;

        String reasonPhrase = "";

        switch (statusCode){
            case 400:
                reasonPhrase = "BAD_REQUEST";
                errorResponseStatus = new ErrorResponseStatus(HttpStatus.BAD_REQUEST,reasonPhrase);
                break;
            case 401:
                reasonPhrase = "UNAUTHORIZED";
                errorResponseStatus = new ErrorResponseStatus(HttpStatus.UNAUTHORIZED,reasonPhrase);
                break;
            case 402:
                reasonPhrase = "PAYMENT_REQUIRED";
                errorResponseStatus = new ErrorResponseStatus(HttpStatus.PAYMENT_REQUIRED,reasonPhrase);
                break;
            case 403:
                reasonPhrase = "FORBIDDEN";
                errorResponseStatus = new ErrorResponseStatus(HttpStatus.FORBIDDEN,reasonPhrase);
                break;
            case 404:
                reasonPhrase = "NOT_FOUND";
                errorResponseStatus = new ErrorResponseStatus(HttpStatus.NOT_FOUND,reasonPhrase);
                break;
            case 405:
                reasonPhrase = "METHOD_NOT_ALLOWED";
                errorResponseStatus = new ErrorResponseStatus(HttpStatus.METHOD_NOT_ALLOWED,reasonPhrase);
                break;
            case 406:
                reasonPhrase = "NOT_ACCEPTABLE";
                errorResponseStatus = new ErrorResponseStatus(HttpStatus.NOT_ACCEPTABLE,reasonPhrase);
                break;
            case 407:
                reasonPhrase = "PROXY_AUTHENTICATION_REQUIRED ";
                errorResponseStatus = new ErrorResponseStatus(HttpStatus.PROXY_AUTHENTICATION_REQUIRED,reasonPhrase);
                break;
            case 408:
                reasonPhrase = "REQUEST_TIMEOUT";
                errorResponseStatus = new ErrorResponseStatus(HttpStatus.REQUEST_TIMEOUT,reasonPhrase);
                break;
            case 409:
                reasonPhrase = "CONFLICT";
                errorResponseStatus = new ErrorResponseStatus(HttpStatus.CONFLICT,reasonPhrase);
                break;
            case 410:
                reasonPhrase = "GONE";
                errorResponseStatus = new ErrorResponseStatus(HttpStatus.GONE,reasonPhrase);
                break;
            case 411:
                reasonPhrase = "LENGTH_REQUIRED";
                errorResponseStatus = new ErrorResponseStatus(HttpStatus.LENGTH_REQUIRED,reasonPhrase);
                break;
            case 412:
                reasonPhrase = "PRECONDITION_FAILED";
                errorResponseStatus = new ErrorResponseStatus(HttpStatus.PRECONDITION_FAILED,reasonPhrase);
                break;
            case 415:
                reasonPhrase = "UNSUPPORTED_MEDIA_TYPE";
                errorResponseStatus = new ErrorResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE,reasonPhrase);
                break;
            default:
                reasonPhrase = "BAD REQUEST";
                errorResponseStatus = new ErrorResponseStatus(HttpStatus.BAD_REQUEST,reasonPhrase);
        }
        return errorResponseStatus;
    }
}
