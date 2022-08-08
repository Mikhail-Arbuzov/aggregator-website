package com.aggregator.aggregator_website.services.validation;

import com.aggregator.aggregator_website.exceptionhandlers.notifications.ErrorMessageResponse;
import com.aggregator.aggregator_website.exceptionhandlers.notifications.ExceptionType;
import com.aggregator.aggregator_website.services.annotations.UrlExists;
import com.aggregator.aggregator_website.services.exceptionclient.ExceptionHttpStatus;
import com.aggregator.aggregator_website.services.interfaceglobalerror.IGlobalClientError;
import com.aggregator.aggregator_website.services.interfaceglobalerror.IGlobalError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckURLValidator implements ConstraintValidator<UrlExists,String> {

    private static ErrorMessageResponse messageResponse;
    private static ExceptionHttpStatus exceptionHttpStatus;

    @Override
    public boolean isValid(String urlValue, ConstraintValidatorContext constraintValidatorContext) {
        String value = urlValue.trim();
        String pattern ="\\b(?:(?:https|http):\\/\\/|www\\.)[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|]\\S";
        Pattern patter = Pattern.compile(pattern);
        Matcher matcher = patter.matcher(value);
        boolean successfully = false;
        if(matcher.matches()){
            if(value != null && !value.isEmpty()){
                successfully = isUrlExist(value);
            }else{
                successfully = false;
            }
        }
        else{
            successfully = false;
            CheckURLValidator.exceptionHttpStatus =null;
            CheckURLValidator.messageResponse=null;
        }

        return successfully;
    }

    private boolean isUrlExist(String url){
        ExceptionType exceptionType = new ExceptionType();
        ExceptionType exceptionType1 = new ExceptionType();
        ExceptionType exceptionType2 = new ExceptionType();

        List<ExceptionType> otherExceptionTypes = new LinkedList<>();

        boolean check = false;
        boolean checkResponseCode = false;
        int statusHttpCode = 0;

        boolean flag = false;
        //String browserValue = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36 Edg/99.0.1150.55";
        String browserValue = "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-Us; rv:1.9.1.2)" +
                "Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729))";


        try {
            URL url1 = new URL(url);
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setRequestMethod("HEAD");
            httpURLConnection.setRequestProperty("User-Agent",browserValue);
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                flag = true;
            }
            if(responseCode >=400 && responseCode < 500){
                checkResponseCode = true;
                statusHttpCode = responseCode;
            }
        }
        catch (MalformedURLException e) {
            check = true;
            exceptionType.setType(e.getClass());
            otherExceptionTypes.add(exceptionType);
        }
        catch (UnknownHostException e) {
            check = true;
            exceptionType1.setType(e.getClass());
            otherExceptionTypes.add(exceptionType1);
        }
        catch (IOException e) {
            check = true;
            exceptionType2.setType(e.getClass());
            otherExceptionTypes.add(exceptionType2);
        }

        if (check !=false){
            messageResponse = errorMessageResponse(otherExceptionTypes);
        }else{
            messageResponse = null;
        }

        if(checkResponseCode != false && statusHttpCode > 0){
            exceptionHttpStatus = new ExceptionHttpStatus(statusHttpCode);
        }
        else {
            exceptionHttpStatus = null;
        }


        return flag;
    }


    public ObjectError isExceptionValidField(IGlobalError globalError, String objName){
        if(messageResponse !=null){
            return globalError.validateField(objName,messageResponse);
        }

        return new ObjectError(objName,"");
    }

    private ErrorMessageResponse errorMessageResponse(List<ExceptionType> exceptionTypes){
        return  new ErrorMessageResponse(exceptionTypes);
    }

    public ObjectError isValidateResponseCode(IGlobalClientError globalClientError, String objName){
        if(exceptionHttpStatus !=null){
            return globalClientError.validateResponseCode(objName,exceptionHttpStatus.getErrorResponseStatus());
        }
        return new ObjectError(objName,"");
    }

}
