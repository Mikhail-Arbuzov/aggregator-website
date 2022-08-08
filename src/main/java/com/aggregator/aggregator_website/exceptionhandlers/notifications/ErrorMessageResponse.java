package com.aggregator.aggregator_website.exceptionhandlers.notifications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageResponse {
    private List<ExceptionType> errorMessages= new LinkedList<>();
}
