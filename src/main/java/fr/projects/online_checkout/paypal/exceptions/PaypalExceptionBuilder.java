package fr.projects.online_checkout.paypal.exceptions;

import fr.projects.online_checkout.core.exceptions.ExceptionBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 21/10/2018
 * @Class purposes : .......
 */

@Slf4j
public class PaypalExceptionBuilder extends ExceptionBuilder<PaypalPaymentException> {

  public PaypalExceptionBuilder(Class<PaypalPaymentException> exceptionClass) {
    super(exceptionClass);
  }

}
