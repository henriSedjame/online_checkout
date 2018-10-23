package fr.projects.online_checkout.paypal.controller;

import fr.projects.online_checkout.paypal.exceptions.PaypalPaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PaypalExceptionHandler {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handle(Exception e){
    return e.getMessage();
  }
}
