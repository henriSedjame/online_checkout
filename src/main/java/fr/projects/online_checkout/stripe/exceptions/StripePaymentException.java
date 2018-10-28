package fr.projects.online_checkout.stripe.exceptions;

import com.stripe.exception.StripeException;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 28/10/2018
 * @Class purposes : .......
 */
public class StripePaymentException extends StripeException {

  public StripePaymentException(String message) {
    super(message, null, null, null);
  }

  public StripePaymentException(String message, Throwable e) {
    super(message, null, null, null, e);
  }

  public StripePaymentException(String message, String requestId, String code, Integer statusCode) {
    super(message, requestId, code, statusCode);
  }

  public StripePaymentException(String message, String requestId, String code, Integer statusCode, Throwable e) {
    super(message, requestId, code, statusCode, e);
  }
}
