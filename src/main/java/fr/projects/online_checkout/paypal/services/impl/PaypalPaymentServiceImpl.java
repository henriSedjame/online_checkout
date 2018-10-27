package fr.projects.online_checkout.paypal.services.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import fr.projects.online_checkout.paypal.constants.PaypalConstants;
import fr.projects.online_checkout.paypal.exceptions.PaypalExceptionBuilder;
import fr.projects.online_checkout.paypal.exceptions.PaypalExceptionMessages;
import fr.projects.online_checkout.paypal.exceptions.PaypalPaymentException;
import fr.projects.online_checkout.paypal.model.PaypalClient;
import fr.projects.online_checkout.paypal.services.PaypalPaymentService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
  public Mono<Payment> approvePayment(Payment payment, PaypalClient client, String mode) throws PayPalRESTException {
    this.exceptionBuilder.clear();

    final Mono<Payment> createdPayment = Mono.fromSupplier(() -> {
      try {
        return payment.create(this.getPaypalContext(client.getClientId(), client.getSecret(), mode));
      } catch (Exception e) {
        exceptionBuilder.addException(exceptionMessages.APPROBATION_PAYMENT_IMPOSSIBLE);
      }
      return null;
    });

    this.exceptionBuilder.throwException();

    return createdPayment;
  }

  @Override
  public Mono<Payment> executePayment(String paymentId, String payerId, PaypalClient client, String mode) throws PayPalRESTException {
    this.exceptionBuilder.clear();

    if (Objects.isNull(paymentId)) this.exceptionBuilder.addException(exceptionMessages.PAYMENT_ID_NULL);
    if (Objects.isNull(payerId)) this.exceptionBuilder.addException(exceptionMessages.PAYER_ID_NULL);

    if (!exceptionBuilder.throwException()){

      Payment payment = new Payment().setId(paymentId);
      PaymentExecution execution = new PaymentExecution().setPayerId(payerId);

      final Mono<Payment> executedPayment = Mono.fromSupplier(() -> {
        try {
          return payment.execute(this.getPaypalContext(client.getClientId(), client.getSecret(), mode), execution);
        } catch (Exception e) {
          this.exceptionBuilder.addException(exceptionMessages.EXECUTION_PAYMENT_IMPOSSIBLE);
        }
        return null;
      });

      this.exceptionBuilder.throwException();

      return executedPayment;
    }
    return Mono.empty();
  }

  @Override
  public Mono<Capture> captureOrderPayment(String paymentId, String payerId, double amountToRefund, String currency, PaypalClient client, String mode) throws PayPalRESTException {

    Mono<Capture> orderCaptured = this.executePayment(paymentId, payerId, client, mode)
      .map(payment -> {
        if (Objects.equals(payment.getState(), PaypalConstants.APPROVED)) {

          String orderId = getPaymentRelatedResources(payment).getOrder().getId();

          if (Objects.isNull(orderId))
            this.exceptionBuilder.addException(exceptionMessages.PAYMENT_ORDER_ID_NULL);

          Capture responseCapture = null;

          if (!this.exceptionBuilder.hasException()) {
            Amount amount = new Amount()
              .setTotal(String.valueOf(amountToRefund))
              .setCurrency(currency);

            Order order = null;
            try {
              order = Order.get(this.getPaypalContext(client.getClientId(), client.getSecret(), mode), orderId)
                .setAmount(amount);
            } catch (PayPalRESTException e) {
              this.exceptionBuilder.addException(exceptionMessages.PAYMENT_ORDER_CREATION_IMPOSSIBLE);
            }

            if (!this.exceptionBuilder.hasException()) {
              Authorization authorization = null;
              try {
                authorization = order.authorize(this.getPaypalContext(client.getClientId(), client.getSecret(), mode));
              } catch (PayPalRESTException e) {
                this.exceptionBuilder.addException(exceptionMessages.PAYMENT_ORDER_AUTHORIZATION_IMPOSSIBLE);
              }

              if (!this.exceptionBuilder.hasException()) {
                Capture capture = new Capture()
                  .setAmount(amount)
                  .setIsFinalCapture(true);
                try {
                  responseCapture = authorization.capture(this.getPaypalContext(client.getClientId(), client.getSecret(), mode), capture);
                } catch (PayPalRESTException e) {
                  this.exceptionBuilder.addException(exceptionMessages.PAYMENT_ORDER_CAPTURE_IMPOSSIBLE);
                }
              }
            }
          }
          return responseCapture;
        } else {
          this.exceptionBuilder.addException(exceptionMessages.PAYMENT_NOT_APPROVED);
          return null;
        }
      });

    if(!this.exceptionBuilder.throwException()) return orderCaptured;

    return Mono.empty();
  }

  @Override
  public Mono<Refund> refundPayment(String saleId, double amountToRefund, String currency, PaypalClient client, String mode) throws PayPalRESTException {
    this.exceptionBuilder.clear();

    Amount amount = new Amount()
      .setTotal(String.valueOf(amountToRefund))
      .setCurrency(currency);

    RefundRequest refundRequest = new RefundRequest()
      .setAmount(amount);

    Sale sale = new Sale()
      .setId(saleId);

    final Mono<Refund> refund = Mono.fromSupplier(() -> {
      try {
        return sale.refund(this.getPaypalContext(client.getClientId(), client.getSecret(), mode), refundRequest);
      } catch (PayPalRESTException e) {
        exceptionBuilder.addException(exceptionMessages.APPROBATION_PAYMENT_IMPOSSIBLE);
      }
      return null;
    });

    if (!this.exceptionBuilder.throwException()) return refund;

    return Mono.empty();
  }

  @Override
  public Mono<Capture> captureAuthorizationPayment(String paymentId, String payerId, double amountToRefund, String currency, PaypalClient client, String mode) throws PayPalRESTException {

    this.exceptionBuilder.clear();

    final Mono<Capture> capturedAuthorization = this.executePayment(paymentId, payerId, client, mode)
      .map(payment -> {

        RelatedResources relatedResource = getPaymentRelatedResources(payment);

        if (!this.exceptionBuilder.hasException()) {

          final Authorization authorization = relatedResource.getAuthorization();

          if (Objects.isNull(authorization))
            this.exceptionBuilder.addException(exceptionMessages.PAYMENT_AUTHORIZATION_CAPTURE_NULL);

          if (!this.exceptionBuilder.hasException()) {

            final Amount amount = new Amount()
              .setTotal(String.valueOf(amountToRefund))
              .setCurrency(currency);
            Capture capture = new Capture()
              .setAmount(amount)
              .setIsFinalCapture(true);

            Capture responseCapture = null;
            try {
              responseCapture = authorization.capture(this.getPaypalContext(client.getClientId(), client.getSecret(), mode), capture);
            } catch (PayPalRESTException e) {
              this.exceptionBuilder.addException(exceptionMessages.PAYMENT_AUTHORIZATION_CAPTURE_IMPOSSIBLE);
            }
            return responseCapture;
          }
        }
        return null;
      });

    if (!this.exceptionBuilder.throwException()) return capturedAuthorization;

    return Mono.empty();
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

  private APIContext getPaypalContext(String clientId, String secret, String mode) {
    return new APIContext(clientId, secret, mode);
  }

}
