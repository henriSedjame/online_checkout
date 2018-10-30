package fr.projects.online_checkout.paypal.services.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import fr.projects.online_checkout.core.utils.RequireObjects;
import fr.projects.online_checkout.paypal.configuration.PaypalApi;
import fr.projects.online_checkout.paypal.constants.PaypalConstants;
import fr.projects.online_checkout.paypal.exceptions.PaypalExceptionBuilder;
import fr.projects.online_checkout.paypal.exceptions.PaypalExceptionMessages;
import fr.projects.online_checkout.paypal.exceptions.PaypalPaymentException;
import fr.projects.online_checkout.paypal.services.PaypalPaymentService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
@Getter
@Slf4j
public class PaypalPaymentServiceImpl implements PaypalPaymentService {

  //********************************************************************************************************************
  // ATTRIBUTS
  //********************************************************************************************************************

  private PaypalExceptionBuilder exceptionBuilder;
  private PaypalExceptionMessages exceptionMessages;

  //********************************************************************************************************************
  // CONSTRUCTEUR
  //********************************************************************************************************************

  public PaypalPaymentServiceImpl(PaypalExceptionMessages exceptionMessages) {
    this.exceptionBuilder = new PaypalExceptionBuilder(PaypalPaymentException.class);
    this.exceptionMessages = exceptionMessages;
  }

  //********************************************************************************************************************
  // METHODES
  //********************************************************************************************************************

  @Override
  public Mono<Payment> approvePayment(Payment payment) {

    return Mono.defer(() -> {
      try {
        return Mono.just(payment.create(PaypalApi.context));
      } catch (Exception e) {
        return Mono.error(this.exceptionBuilder.buildException(exceptionMessages.APPROBATION_PAYMENT_IMPOSSIBLE, e));
      }
    });

  }

  @Override
  public Mono<Payment> executePayment(String paymentId, String payerId) {

    this.exceptionBuilder.clear();

    RequireObjects.requireNotNull(Arrays.asList(paymentId, payerId), this.exceptionBuilder, exceptionMessages.EXECUTE_PAYMENT_PARAMETERS_MISSING);

    if (exceptionBuilder.isEmpty()) {

      Payment payment = new Payment().setId(paymentId);
      PaymentExecution execution = new PaymentExecution().setPayerId(payerId);

      return Mono.defer(() -> {
        try {
          return Mono.just(payment.execute(PaypalApi.context, execution));
        } catch (Exception e) {
          return Mono.error(this.exceptionBuilder.buildException(exceptionMessages.EXECUTION_PAYMENT_IMPOSSIBLE, e));
        }

      });
    }

    return Mono.error(this.exceptionBuilder.buildException());
  }

  @Override
  public Mono<Capture> captureOrderPayment(String paymentId, String payerId, double amountToRefund, String currency) {

    this.exceptionBuilder.clear();

    RequireObjects.requireNotNull(Arrays.asList(paymentId, payerId, amountToRefund, currency), this.exceptionBuilder, exceptionMessages.CAPTURE_ORDER_PAYMENT_PARAMETERS_MISSING);

    if (this.exceptionBuilder.isEmpty()) {

      return Mono.defer(() -> this.executePayment(paymentId, payerId)
              .flatMap(payment -> {

                if (Objects.equals(payment.getState(), PaypalConstants.APPROVED)) {

                  String orderId = getPaymentRelatedResources(payment).getOrder().getId();

                  if (Objects.isNull(orderId))
                    return Mono.error(this.exceptionBuilder.buildException(exceptionMessages.PAYMENT_ORDER_ID_NULL, null));

                  Capture responseCapture = null;

            Amount amount = new Amount()
                    .setTotal(String.valueOf(amountToRefund))
                    .setCurrency(currency);

            Order order = null;
            try {
              order = Order.get(PaypalApi.context, orderId)
                      .setAmount(amount);
            } catch (PayPalRESTException e) {
              return Mono.error(this.exceptionBuilder.buildException(exceptionMessages.PAYMENT_ORDER_CREATION_IMPOSSIBLE, e));
            }

                  Authorization authorization = null;
                  try {
                    authorization = order.authorize(PaypalApi.context);
                  } catch (PayPalRESTException e) {
                    return Mono.error(this.exceptionBuilder.buildException(exceptionMessages.PAYMENT_ORDER_AUTHORIZATION_IMPOSSIBLE, e));
                  }

                  Capture capture = new Capture()
                          .setAmount(amount)
                          .setIsFinalCapture(true);
                  try {
                    responseCapture = authorization.capture(PaypalApi.context, capture);
                  } catch (PayPalRESTException e) {
                    return Mono.error(this.exceptionBuilder.buildException(exceptionMessages.PAYMENT_ORDER_CAPTURE_IMPOSSIBLE, e));
                  }

                  return Mono.just(responseCapture);
                } else {
                  return Mono.error(this.exceptionBuilder.buildException(exceptionMessages.PAYMENT_NOT_APPROVED, null));
                }
              }));
    }
    return Mono.error(this.exceptionBuilder.buildException());
  }

  @Override
  public Mono<Refund> refundPayment(String saleId, double amountToRefund, String currency) {
    this.exceptionBuilder.clear();

    RequireObjects.requireNotNull(Arrays.asList(saleId, amountToRefund, currency), this.exceptionBuilder, exceptionMessages.REFUND_PAYMENT_PARAMETERS_MISSING);

    if (this.exceptionBuilder.isEmpty()) {
      Amount amount = new Amount()
              .setTotal(String.valueOf(amountToRefund))
              .setCurrency(currency);

      RefundRequest refundRequest = new RefundRequest()
              .setAmount(amount);

      Sale sale = new Sale()
              .setId(saleId);

      return Mono.defer(() -> {
        try {
          return Mono.just(sale.refund(PaypalApi.context, refundRequest));
        } catch (PayPalRESTException e) {
          return Mono.error(this.exceptionBuilder.buildException(exceptionMessages.REFUND_PAYMENT_IMPOSSIBLE, e));
        }
      });
    }
    return Mono.error(this.exceptionBuilder.buildException());
  }

  @Override
  public Mono<Capture> captureAuthorizationPayment(String paymentId, String payerId, double amountToRefund, String currency) {

    this.exceptionBuilder.clear();

    RequireObjects.requireNotNull(Arrays.asList(paymentId, payerId, amountToRefund, currency), this.exceptionBuilder, exceptionMessages.CAPTURE_AUTHORIZATION_PAYMENT_PARAMETERS_MISSING);

    if (this.exceptionBuilder.isEmpty()) {

      return Mono.defer(() -> this.executePayment(paymentId, payerId)
              .flatMap(payment -> {

                RelatedResources relatedResource = getPaymentRelatedResources(payment);

                if (this.exceptionBuilder.isEmpty()) {

                  final Authorization authorization = relatedResource.getAuthorization();

                  if (Objects.isNull(authorization))
                    return Mono.error(this.exceptionBuilder.buildException(exceptionMessages.PAYMENT_AUTHORIZATION_CAPTURE_NULL, null));

            final Amount amount = new Amount()
              .setTotal(String.valueOf(amountToRefund))
              .setCurrency(currency);

                  final Capture capture = new Capture()
              .setAmount(amount)
              .setIsFinalCapture(true);

            Capture responseCapture = null;
            try {
              responseCapture = authorization.capture(PaypalApi.context, capture);
            } catch (PayPalRESTException e) {
              return Mono.error(this.exceptionBuilder.buildException(exceptionMessages.PAYMENT_AUTHORIZATION_CAPTURE_IMPOSSIBLE, e));
            }

                  return Mono.just(responseCapture);

                } else return Mono.error(this.exceptionBuilder.buildException());
              }));
    }
    return Mono.error(this.exceptionBuilder.buildException());
  }


  //********************************************************************************************************************
  // PRIVATE METHODES
  //********************************************************************************************************************

  private RelatedResources getPaymentRelatedResources(Payment payment) {
    final List<Transaction> transactions = payment.getTransactions();
    Transaction transaction = null;
    List<RelatedResources> relatedResources = null;
    RelatedResources relatedResource = null;
    if (transactions.isEmpty())
      this.exceptionBuilder.addException(exceptionMessages.PAYMENT_TRANSACTIONS_EMPTY);
    else {
      transaction = transactions.get(0);
      if (Objects.isNull(transaction))
        this.exceptionBuilder.addException(exceptionMessages.PAYMENT_TRANSACTION_NULL);
      else {
        relatedResources = transaction.getRelatedResources();
        if (relatedResources.isEmpty())
          this.exceptionBuilder.addException(exceptionMessages.PAYMENT_TRANSACTION_RELATED_SOURCES_EMPTY);
        else {
          relatedResource = relatedResources.get(0);
          if (Objects.isNull(relatedResource))
            this.exceptionBuilder.addException(exceptionMessages.PAYMENT_TRANSACTION_RELATED_SOURCE_NULL);
        }
      }
    }
    return relatedResource;
  }


}
