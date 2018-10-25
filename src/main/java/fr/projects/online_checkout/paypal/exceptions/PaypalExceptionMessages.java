package fr.projects.online_checkout.paypal.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 21/10/2018
 * @Class purposes : .......
 */
@Configuration
@PropertySource("classpath:error_messages.properties")
public class PaypalExceptionMessages {

  @Value("${payment.approbation.impossible}")
  public String APPROBATION_PAYMENT_IMPOSSIBLE;
  @Value("${execution.payment.impossible}")
  public String EXECUTION_PAYMENT_IMPOSSIBLE;
  @Value("${payment.id.null}")
  public String PAYMENT_ID_NULL;
  @Value("${payer.id.null}")
  public String PAYER_ID_NULL;
  @Value("${payment.transactions.empty}")
  public String PAYMENT_TRANSACTIONS_EMPTY;
  @Value("${payment.transaction.null}")
  public String PAYMENT_TRANSACTION_NULL;
  @Value("${payment.transaction.related.resources.empty}")
  public String PAYMENT_TRANSACTION_RELATED_SOURCES_EMPTY;
  @Value("${payment.transaction.related.resource.null}")
  public String PAYMENT_TRANSACTION_RELATED_SOURCE_NULL;
  @Value("${payment.authorization.capture.impossible}")
  public String PAYMENT_AUTHORIZATION_CAPTURE_IMPOSSIBLE;
  @Value("${payment.order.id.null}")
  public String PAYMENT_ORDER_ID_NULL;
  @Value("${payment.order.creation.impossible}")
  public String PAYMENT_ORDER_CREATION_IMPOSSIBLE;
  @Value("${payment.order.authorization.impossible}")
  public String PAYMENT_ORDER_AUTHORIZATION_IMPOSSIBLE;
  @Value("${payment.order.capture.impossible}")
  public String PAYMENT_ORDER_CAPTURE_IMPOSSIBLE;
  @Value("${payment.not.approved}")
  public String PAYMENT_NOT_APPROVED;
  @Value("${payment.authorization.capture.null}")
  public String PAYMENT_AUTHORIZATION_CAPTURE_NULL;

}
