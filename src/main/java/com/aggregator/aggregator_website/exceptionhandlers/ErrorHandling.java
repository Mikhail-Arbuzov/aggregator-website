package com.aggregator.aggregator_website.exceptionhandlers;

import com.aggregator.aggregator_website.exceptionhandlers.notifications.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@ControllerAdvice
public class ErrorHandling {

    private static Logger logger = LoggerFactory.getLogger(ErrorHandling.class);

    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException ex, Model model){
        exceptionInfo(ex);
        model.addAttribute("errormessage",new ErrorMessage("Некорректный ввод URL-адреса или вывод его контекста !!!"));
        return "error/error";
    }


    @ExceptionHandler(NullPointerException.class)
    public String handleIOException(NullPointerException ex, Model model){
        exceptionInfo(ex);
        model.addAttribute("errormessage",new ErrorMessage("Не удалось извлечь данные из указанного URL-адреса сайта!!!Попробуйте другой URL-адрес!"));
        return "error/error";
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleIOException(ResponseStatusException ex, Model model){
        exceptionInfo(ex);
        model.addAttribute("errormessage",new ErrorMessage(" По указанному id  не удалось найти данные!"));
        return "error/error";
    }

    @ExceptionHandler(Throwable.class)
    public String handleIOException(Throwable e, Model model){
        String errorType = e.getClass().getName() != null ? e.getClass().getName() : "не известен!";
        String errorMessage = e.getMessage() != null ? e.getMessage() : "отсутствует!";
        logger.error("Ошибка во время выполнения приложения: "+ "\nтип ошибки: "+
                errorType + "\nсообщение об ошибке: " + errorMessage);
        model.addAttribute("errormessage",new ErrorMessage("Возникла ошибка на сервере!!!"));
        return "error/allerror";
    }


    private void exceptionInfo(Exception ex) {
        String exceptionType = ex.getClass().getName() != null ? ex.getClass().getName() : "не известен!";
        String exceptionMessage = ex.getMessage() != null ? ex.getMessage() : "отсутствует!";
        logger.error("Исключение во время выполнения приложения: "+ "\nтип исключения: "+
                exceptionType + "\nсообщение об исключении: " + exceptionMessage);
    }

}
