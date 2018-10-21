package fr.projects.online_checkout.paypal.exceptions;

import com.paypal.base.rest.PayPalRESTException;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 21/10/2018
 * @Class purposes : .......
 */
public class PaypalPaymentException extends PayPalRESTException {

  public PaypalPaymentException(String message) {
    super(message);
  }

  public PaypalPaymentException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public PaypalPaymentException(Throwable throwable) {
    super(throwable);
  }
}
