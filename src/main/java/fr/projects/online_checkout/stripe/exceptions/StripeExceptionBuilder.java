package fr.projects.online_checkout.stripe.exceptions;


import fr.projects.online_checkout.core.exceptions.ExceptionBuilder;


/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 28/10/2018
 * @Class purposes : .......
 */
public class StripeExceptionBuilder extends ExceptionBuilder<StripePaymentException> {

  public StripeExceptionBuilder(Class<StripePaymentException> exceptionClass) {
    super(exceptionClass);
  }

}
