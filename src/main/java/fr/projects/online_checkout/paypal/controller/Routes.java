package fr.projects.online_checkout.paypal.controller;

import fr.projects.online_checkout.paypal.exceptions.UnstantiableClassException;

public class Routes {
  private Routes() throws UnstantiableClassException {
    throw new UnstantiableClassException("Cette classe n'est pas instantibale");
  }

  public static final String HOST = "http://localhost:8082";
  public static final String EXECUTE ="/paypal/payment/execute/success";
  public static final String AUTHORIZE ="/paypal/payment/authorize/sucess";
  public static final String ORDER = "/paypal/payment/order/success";
  public static final String CANCEL ="/paypal/payment/cancel";
}
